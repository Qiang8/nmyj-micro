package com.taiji.cdp.base.sms.controller;

import com.taiji.cdp.base.sms.entity.SmsRecieve;
import com.taiji.cdp.base.sms.feign.ISmsRecieveService;
import com.taiji.cdp.base.sms.mapper.SmsRecieveMapper;
import com.taiji.cdp.base.sms.service.SmsRecieveService;
import com.taiji.cdp.base.sms.vo.SmsRecieveVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/smsRecieves")
public class SmsRecieveController implements ISmsRecieveService {
    SmsRecieveService smsRecieveService;
    SmsRecieveMapper smsRecieveMapper;

    /**
     * 不分页
     */
    @Override
    public ResponseEntity<List<SmsRecieveVo>> findList(@RequestBody SmsRecieveVo smsRecieveVo){
        List<SmsRecieve> list = smsRecieveService.findList(smsRecieveVo);
        List<SmsRecieveVo> voList = smsRecieveMapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }


    /**
     * 新增
     */
    @Override
    public ResponseEntity<SmsRecieveVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody SmsRecieveVo vo){
        List<SmsRecieveVo> receivers = vo.getReceivers();
        SmsRecieveVo resultVo = new SmsRecieveVo();
        for (SmsRecieveVo receiver:receivers) {
            vo.setReceiverName(receiver.getReceiverName());
            vo.setReceiverTel(receiver.getReceiverTel());
            SmsRecieve entity = smsRecieveMapper.voToEntity(vo);
            SmsRecieve result = smsRecieveService.create(entity);
             resultVo = smsRecieveMapper.entityToVo(result);

        }
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新
     */
    @Override
    public ResponseEntity<SmsRecieveVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody SmsRecieveVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        //先删除再添加
        smsRecieveService.deleteLogic(id);
        List<SmsRecieveVo> receivers = vo.getReceivers();
        SmsRecieveVo resultVo = new SmsRecieveVo();
        if(!CollectionUtils.isEmpty(receivers)){
            for (SmsRecieveVo receiver :receivers) {
                SmsRecieveVo smsRecieveVo = new SmsRecieveVo();
                smsRecieveVo.setSmsId(id);
                smsRecieveVo.setReceiverName(receiver.getReceiverName());
                smsRecieveVo.setReceiverTel(receiver.getReceiverTel());
                smsRecieveVo.setSendBy(vo.getSendBy());
                smsRecieveVo.setSendStatus(vo.getSendStatus());
//            ResponseEntity<List<SmsRecieveVo>> list = findList(smsRecieveVo);
//            List<SmsRecieveVo> SmsRecieveVo  = list.getBody();
//            SmsRecieveVo sr = new SmsRecieveVo();
//            if (!CollectionUtils.isEmpty(SmsRecieveVo)){
//                sr = SmsRecieveVo.get(0);
//            }
//            smsRecieveVo.setId(sr.getId());
                SmsRecieve entity = smsRecieveMapper.voToEntity(smsRecieveVo);
                SmsRecieve result = smsRecieveService.update(entity);
                resultVo = smsRecieveMapper.entityToVo(result);
            }
        }
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     *获取单条
     */
    @Override
    public ResponseEntity<SmsRecieveVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id){
        SmsRecieve result = smsRecieveService.findOne(id);
        SmsRecieveVo resultVo = smsRecieveMapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 删除
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id){
        smsRecieveService.deleteLogic(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
