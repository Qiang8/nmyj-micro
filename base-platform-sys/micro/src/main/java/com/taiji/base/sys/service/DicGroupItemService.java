package com.taiji.base.sys.service;

import com.taiji.base.redis.service.DicItemRedisService;
import com.taiji.base.sys.entity.DicGroupItem;
import com.taiji.base.sys.repository.DicGroupItemRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统数据字典Service类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Slf4j
@Service
@AllArgsConstructor
public class DicGroupItemService extends BaseService<DicGroupItem,String> {

    DicGroupItemRepository repository;
    @Autowired
    DicItemRedisService dicItemRedisService;

    /**
     * 根据id获取一条记录。
     *
     * @param id    字典项id
     *
     * @return DicGroupItem 字典项
     */
    public DicGroupItem findOne(String id) {
        Assert.hasText(id, "id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 根据参数获取DicGroupItem多条记录。
     * <p>
     *
     * @param dicCode   字典项类别（可选）
     *
     * @return List<DicGroupItem>   字典项列表
     */
    public List<DicGroupItem> findAll(String dicCode){
        List<DicGroupItem> result = null;

        //redis操作 -- 根据dicCode返回缓存中的 List<DicGroupItem>
        if(StringUtils.hasText(dicCode)){
            result = dicItemRedisService.getItemsByDicCode(dicCode);
        }
        if(null == result){
            result = repository.findAll(dicCode);
        }

        return result;
    }

    /**
     * 新增DicGroupItem，DicGroupItem不能为空。
     *
     * @param entity   字典项entity
     *
     * @return DicGroupItem 字典项
     */
    public DicGroupItem create(DicGroupItem entity) {
        Assert.notNull(entity,"entity不能为null!");

        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DicGroupItem result = repository.save(entity);

        //redis操作 --新增或更新单个字典项
        dicItemRedisService.createOrUpdateItem(result);

        return result;
    }

    /**
     * 更新DicGroupItem，DicGroupItem不能为空。
     *
     * @param entity    字典项entity
     * @param id 更新DicGroupItem Id
     *
     * @return DicGroupItem 字典项
     */
    public DicGroupItem update(DicGroupItem entity, String id) {
        Assert.notNull(entity,"entity不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        DicGroupItem result = repository.save(entity);

        //redis操作 --新增或更新单个字典项
        dicItemRedisService.createOrUpdateItem(result);

        return result;
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id    字典项id
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为null或空字符串!");
        DicGroupItem entity = repository.findOne(id);
        repository.deleteLogic(entity, delFlagEnum);

        //redis操作 --删除单个字典项
        dicItemRedisService.deleteItem(entity);
    }

    /**
     * 根据itemId 获取缓存中的 itemName
     */
    public String getItemNameById(String itemId){
        String result = dicItemRedisService.getItemNameById(itemId);
        if(StringUtils.isEmpty(result)){ //如果缓存中存在问题 -- 查询数据库
            result = findOne(itemId).getItemName();
        }
        return result;
    }

    /**
     * 根据itemId串查找itemNames字符串(英文逗号拼接)
     */
    public String getNamesByItemIds(List<String> itemIds){
        String result = dicItemRedisService.getNamesByItemIds(itemIds);
        if(StringUtils.isEmpty(result)){
            if(itemIds.size()>0){
                for(String itemId:itemIds){
                    result+=(findOne(itemId).getItemName())+",";
                }
                result = result.substring(0,result.length()-1); //去除最后一个逗号
            }
        }
        return result;
    }
}
