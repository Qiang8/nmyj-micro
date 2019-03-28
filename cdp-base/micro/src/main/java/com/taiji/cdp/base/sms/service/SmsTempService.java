package com.taiji.cdp.base.sms.service;

import com.taiji.cdp.base.sms.entity.SmsTemp;
import com.taiji.cdp.base.sms.repository.SmsTempRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SmsTempService extends BaseService<SmsTemp,String> {

    SmsTempRepository repository;

    public SmsTemp create(SmsTemp entity){
        Assert.notNull(entity,"SmsTemp 对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        SmsTemp result = repository.save(entity);
        return result;
    }

    public SmsTemp findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串");
        SmsTemp result = repository.findOne(id);
        return result;
    }

    public SmsTemp update(SmsTemp entity,String id){
        Assert.notNull(entity,"SmsTemp 对象不能为null");
        Assert.hasText(id,"id不能为null或空字符串");
        entity.setId(id);
        SmsTemp result = repository.save(entity);
        return result;
    }

    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id不能为Null或空字符串");
        SmsTemp entity = repository.findOne(id);
        repository.deleteLogic(entity,delFlagEnum);
    }

    public Page<SmsTemp> findPage(MultiValueMap<String,Object> params, Pageable pageable){
        Page<SmsTemp> result = repository.findPage(params,pageable);
        return result;
    }
    public List<SmsTemp> findList(MultiValueMap<String,Object> params){
        List<SmsTemp> result = repository.findList(params);
        return result;
    }

}
