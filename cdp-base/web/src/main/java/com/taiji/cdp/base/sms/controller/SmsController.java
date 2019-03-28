package com.taiji.cdp.base.sms.controller;

import com.taiji.cdp.base.sms.searchVo.SmsListVo;
import com.taiji.cdp.base.sms.searchVo.SmsPageVo;
import com.taiji.cdp.base.sms.service.SmsService;
import com.taiji.cdp.base.sms.vo.SendMsgVo;
import com.taiji.cdp.base.sms.vo.SmsRecieveVo;
import com.taiji.cdp.base.sms.vo.SmsVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/smses")
public class SmsController{
    @Autowired
    SmsService smsService;

    // 新增
    @PostMapping
    public ResultEntity addSms(
            @Validated
            @NotNull(message = "SmsVo不能为null")
            @RequestBody SmsVo smsVo,OAuth2Authentication principal){
        String smsId = smsService.create(smsVo,principal);
        return ResultUtils.success(smsId);
    }

    //修改
    @PutMapping(path = "/{id}")
    public ResultEntity updateSms(
            @NotNull(message = "SmsVo不能为null")
            @RequestBody SmsVo smsVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            OAuth2Authentication principal){
        smsService.update(smsVo,id,principal);
        return ResultUtils.success();
    }

    //获取单条
    @GetMapping(path = "/{id}")
    public ResultEntity findSmsById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        SmsVo vo = smsService.findOne(id);
        return ResultUtils.success(vo);
    }

    //删除
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteSms(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        smsService.deleteLogic(id);
        return ResultUtils.success();
    }

    //分页
    @PostMapping(path = "/search")
    public ResultEntity findSmss(@RequestBody SmsPageVo smsPageVo){
        RestPageImpl<SmsVo> pageVo = smsService.findPage(smsPageVo);
        return ResultUtils.success(pageVo);
    }

    //不分页
    @PostMapping(path = "/searchAll")
    public ResultEntity findSmsAll(@RequestBody SmsListVo smsListVo){
        List<SmsVo> listVo = smsService.findList(smsListVo);
        return ResultUtils.success(listVo);
    }

    //查看短信发送状态
    @GetMapping(path = "/sendStatus/{smsId}")
    public ResultEntity findSmsRecieveBySmsId(
            @NotEmpty(message = "smsId不能为空")
            @PathVariable(name = "smsId")String smsId){
        List<SmsRecieveVo> vo = smsService.findSmsRecieveBySmsId(smsId);
        return ResultUtils.success(vo);
    }


    //短信发送，后台需要根据各个发送人的发送接收情况依次写短信接收表
    @PostMapping(path = "/send")
    public ResultEntity sendMsg(@RequestBody SendMsgVo sendMsgVo,OAuth2Authentication principal){
        //List<SmsVo> listVo = smsService.findList(smsListVo);
        //TODO 短信发送接口
        smsService.sendMsg(sendMsgVo,principal);
        return ResultUtils.success();
    }

}