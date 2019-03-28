package com.taiji.base.redis.service;

import com.taiji.base.redis.utils.RedisUtil;
import com.taiji.base.sys.entity.DicGroupItem;
import com.taiji.base.sys.repository.DicGroupItemRepository;
import com.taiji.base.sys.repository.DicGroupRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class DicItemRedisService {


    @Autowired
    DicGroupItemRepository repository;
    @Autowired
    DicGroupRepository dicGroupRepository;
    @Autowired
    RedisUtil redisUtil;

    private final static String itemKey = "dicItemKey";

    /**
     * 存入缓存：
     * (1) item:  String,Map<String,String>
     *     "dicItemKey",Map<itemId,itemName>
     * (2) group: String,Map<String,Object>
     *     dicCode,Map<itemId,DicGroupItem>
     *
     * 系统启动初始化字典表
     */
    public void createAllDicItems(){

        //dicGroup查出所有的 dicCode
        List<String> dicCodes = dicGroupRepository.findAllDicCode();

        Map<String,String> itemMap = new HashMap<>();
        if(null!=dicCodes&&dicCodes.size()>0){
            for(String dicCode : dicCodes){
                Map<String,DicGroupItem> groupMap = new HashMap<>();
                //根据dicCode获取所有的字典项List
                List<DicGroupItem> itemList = repository.findItemsByDicCode(dicCode);

                if(null!=itemList&&itemList.size()>0){
                    for(DicGroupItem item : itemList){
                        itemMap.put(item.getId(),item.getItemName());
                        groupMap.put(item.getId(),item);
                    }
                }
                redisUtil.set(dicCode,groupMap);
            }
            redisUtil.set(itemKey,itemMap); //itemId,ItemName键值对放置在map中
        }
    }

    /**
     * 清理缓存字典表记录
     */
    public void clearAllDicItems(){
        //1.清理item部分
        System.out.println(itemKey+"部分清理开始>>>>>>>>>>>>>>>>>>");
        redisUtil.remove(itemKey);
        System.out.println(itemKey+"部分清理结束>>>>>>>>>>>>>>>>>>");
        //2.清理group部分
        //dicGroup查出所有的 dicCode
        List<String> dicCodes = dicGroupRepository.findAllDicCode();
        if(null!=dicCodes&&dicCodes.size()>0){
            for(String dicCode : dicCodes){
                System.out.println("字典表dicCode:"+dicCode+"部分清理开始>>>>>>>>>>>>>>>>>>");
                redisUtil.remove(dicCode);
                System.out.println("字典表dicCode:"+dicCode+"部分清理结束>>>>>>>>>>>>>>>>>>");
            }
        }
    }

    /**
     * 新增和启用单个字典表
     */
    public void createDicGroupByDicCode(String dicCode){
        Map<String,DicGroupItem> groupMap = new HashMap<>();

        Map<String,String> itemMap = (Map<String,String>)redisUtil.get(itemKey);

        //根据dicCode获取所有的字典项List
        List<DicGroupItem> itemList = repository.findItemsByDicCode(dicCode);

        if(null!=itemList&&itemList.size()>0){
            for(DicGroupItem item : itemList){
                itemMap.put(item.getId(),item.getItemName());
                groupMap.put(item.getId(),item);
            }
        }
        redisUtil.set(itemKey,itemMap); //itemId,ItemName键值对放置在map中
        redisUtil.set(dicCode,groupMap);
    }

    /**
     * 删除和禁用单个字典表，级联删除字典项
     */
    public List<DicGroupItem> deleteDicGroupByDicCode(String dicCode){

        if(redisUtil.exists(dicCode)){
            //清理groupMap
            redisUtil.remove(dicCode);

            //清理itemMap
            Map<String,String> itemsMap = (Map<String,String>)redisUtil.get(itemKey);
            //根据dicCode获取所有的字典项id串
            List<String> dicItemIds = repository.findItemIdsByDicCode(dicCode);

            if(null!=dicItemIds&&dicItemIds.size()>0){
                for(String itemId : dicItemIds){
                    if(itemsMap.containsKey(itemId)){
                        itemsMap.remove(itemId);
                    }
                }
            }
            redisUtil.set(itemKey,itemsMap); //将删除后的map替换进去

        }
        return getItemsByDicCode(dicCode);
    }

    /**
     * 新增或更新单个字典项
     */
    public DicGroupItem createOrUpdateItem(DicGroupItem item){
        String itemId = item.getId();

        Map<String,String> itemMap = (Map<String,String>)redisUtil.get(itemKey);
        itemMap.put(itemId,item.getItemName());
        redisUtil.set(itemKey,itemMap);

        String dicCode = item.getDicCode(); //字典表code
        if(redisUtil.exists(dicCode)){
            Map<String,DicGroupItem> itemsMap = (Map<String,DicGroupItem>)redisUtil.get(dicCode);
            itemsMap.put(itemId,item);
            redisUtil.set(dicCode,itemsMap);
        }

        return item;
    }

    /**
     * 删除单个字典项
     */
    public DicGroupItem deleteItem(DicGroupItem item){
        String itemId = item.getId();

        Map<String,String> itemMap = (Map<String,String>)redisUtil.get(itemKey);
        if(itemMap.containsKey(itemId)){
            itemMap.remove(itemId);
            redisUtil.set(itemKey,itemMap);
        }

        String dicCode = item.getDicCode(); //字典表code
        if(redisUtil.exists(dicCode)){
            Map<String,DicGroupItem> itemsMap = (Map<String,DicGroupItem>)redisUtil.get(dicCode);
            if(itemsMap.containsKey(itemId)){
                itemsMap.remove(itemId);
                redisUtil.set(dicCode,itemsMap);
            }
        }

        return item;
    }

    /**
     * 根据dicCode返回缓存中的 List<DicGroupItem>
     */
    public List<DicGroupItem> getItemsByDicCode(String dicCode){
        List<DicGroupItem> result = null;
        if(redisUtil.exists(dicCode)){
            Map<String,DicGroupItem> itemsMap = (Map<String,DicGroupItem>)redisUtil.get(dicCode);
            result = new ArrayList<>(itemsMap.values());
        }
        return result;
    }

    /**
     * 根据dicCode和 itemId 返回缓存中的 DicGroupItem
     */
    public DicGroupItem getItemByDicCodeAndId(String dicCode,String itemId){
        DicGroupItem result = null;
        if(redisUtil.exists(dicCode)){
            Map<String,DicGroupItem> itemsMap = (Map<String,DicGroupItem>)redisUtil.get(dicCode);
            if(itemsMap.containsKey(itemId)){
                result =itemsMap.get(itemId);
            }
        }
        return result;
    }

    /**
     * 根据itemId 获取缓存中的 itemName
     */
    public String getItemNameById(String itemId){
        Map<String,String> itemMap = (Map<String,String>)redisUtil.get(itemKey);
        if(itemMap.containsKey(itemId)){
            String itemName = itemMap.get(itemId);
            return itemName;
        }else{
            return null;
        }
    }
    /**
     * 根据itemId串查找itemNames字符串(英文逗号拼接)
     */
    public String getNamesByItemIds(List<String> itemIds){
        String result = null;
        if(null!=itemIds&&itemIds.size()>0){
            List<String> names = new ArrayList<>();
            for(String itemId : itemIds){
                names.add(getItemNameById(itemId));
            }
            result =StringUtils.join(names,",");
        }
        return result;
    }

}
