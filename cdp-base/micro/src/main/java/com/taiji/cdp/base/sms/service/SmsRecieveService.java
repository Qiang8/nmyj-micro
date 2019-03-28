package com.taiji.cdp.base.sms.service;

import com.taiji.cdp.base.sms.entity.SmsRecieve;
import com.taiji.cdp.base.sms.repository.SmsRecieveRepository;
import com.taiji.cdp.base.sms.vo.SmsRecieveVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SmsRecieveService extends BaseService<SmsRecieve,String> {
    @Autowired
    private SmsRecieveRepository smsRecieveRepository;

    public SmsRecieve create(SmsRecieve entity){
        Assert.notNull(entity,"Sms 对象不能为null");
        SmsRecieve result = smsRecieveRepository.save(entity);
        return result;
    }

    public SmsRecieve findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串");
        SmsRecieve result = smsRecieveRepository.findOne(id);
        return result;
    }

    public SmsRecieve update(SmsRecieve entity){
        Assert.notNull(entity,"Sms对象不能为Null");
        SmsRecieve result = smsRecieveRepository.save(entity);
        return result;
    }

    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为Null或空字符串");
        SmsRecieveVo vo = new SmsRecieveVo();
        vo.setSmsId(id);
        List<SmsRecieve> result = smsRecieveRepository.findList(vo);
        smsRecieveRepository.delete(result);
    }

    public List<SmsRecieve> findList(SmsRecieveVo smsRecieveVo){
        List<SmsRecieve> result = smsRecieveRepository.findList(smsRecieveVo);
        return result;
    }
}
