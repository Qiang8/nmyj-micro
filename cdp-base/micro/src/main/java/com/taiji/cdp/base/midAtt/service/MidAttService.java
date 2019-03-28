package com.taiji.cdp.base.midAtt.service;

import com.taiji.cdp.base.midAtt.entity.Attachment;
import com.taiji.cdp.base.midAtt.entity.MidAtt;
import com.taiji.cdp.base.midAtt.repository.MidAttRepository;
import com.taiji.cdp.base.midAtt.vo.MidAttSaveVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MidAttService extends BaseService<MidAtt,String>{

    MidAttRepository repository;

    /**
     * 创建单个中间表对象
     */
    public MidAtt createOne(MidAtt entity){
        Assert.notNull(entity,"entity 不能为null");
        return repository.save(entity);
    }

    /**
     * 创建多个中间表对象
     */
    public List<MidAtt> createList(List<MidAtt> list){
        List<MidAtt> resultList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){

            for(MidAtt midAtt:list){
                MidAtt result =createOne(midAtt);
                if(null!=result){
                    resultList.add(result);
                }
            }
        }
        return resultList;
    }

    /**
     * 通过附件id删除单个中间表对象
     */
    public void deleteByAttId(String attId){
        repository.deleteByAttId(attId);
    }

    /**
     * 通过附件ids删除多个中间表对象
     */
    public void deleteByAttIds(List<String> attIds){
        repository.deleteByAttIds(attIds);
    }

    /**
     * 根据业务实体id获取所有附件对象
     */
    public List<Attachment> findAttsByEntityId(String entityId){
        return repository.findAttsByEntityId(entityId);
    }

    /**
     * 业务提交保存时调用，保存附件信息
     */
    public void saveMidAtts(MidAttSaveVo midAttSaveVo){
        repository.saveMidAtts(midAttSaveVo);
    }

}
