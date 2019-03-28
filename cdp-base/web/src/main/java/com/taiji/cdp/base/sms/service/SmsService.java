package com.taiji.cdp.base.sms.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.cdp.base.common.enums.SmsSendStatusEnum;
import com.taiji.cdp.base.midAtt.service.BaseService;
import com.taiji.cdp.base.sms.feign.SmsClient;
import com.taiji.cdp.base.sms.feign.SmsRecieveClient;
import com.taiji.cdp.base.sms.searchVo.SmsListVo;
import com.taiji.cdp.base.sms.searchVo.SmsPageVo;
import com.taiji.cdp.base.sms.vo.SendMsgVo;
import com.taiji.cdp.base.sms.vo.SmsRecieveVo;
import com.taiji.cdp.base.sms.vo.SmsVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.feign.UtilsFeignClient;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SmsService extends BaseService {
    @Autowired
    private SmsClient smsClient;
    @Autowired
    private SmsRecieveClient smsRecieveClient;
    @Autowired
    private UtilsFeignClient utilsFeignClient;

    //新增
    public String create(SmsVo smsVo, OAuth2Authentication principal){

        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String account = userMap.get("username").toString();

        smsVo.setCreateBy(account);
        smsVo.setUpdateBy(account);
        smsVo.setBuildOrgId(userMap.get("orgId").toString());
        smsVo.setBuildOrgName(userMap.get("orgName").toString());
        smsVo.setSendStatus(SmsSendStatusEnum.NOT_SEND.getCode());

        ResponseEntity<SmsVo> resultVo = smsClient.create(smsVo);
        SmsVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);

        //存入短信接收表信息
        SmsRecieveVo SmsRecieveVo = new SmsRecieveVo();
        SmsRecieveVo.setSmsId(vo.getId());
        SmsRecieveVo.setSendBy(account);
        SmsRecieveVo.setSendStatus(SmsSendStatusEnum.NOT_SEND.getCode());
        SmsRecieveVo.setReceivers(smsVo.getReceivers());
        smsRecieveClient.create(SmsRecieveVo);

        return vo.getId();
    }

    //修改
    public void update(SmsVo smsVo,String id, OAuth2Authentication principal){
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String account = userMap.get("username").toString();
        Assert.hasText(id,"id 不能为空");

        smsVo.setUpdateBy(account);
        smsClient.update(smsVo,id);
        //修改短信接收表信息
        SmsRecieveVo SmsRecieveVo = new SmsRecieveVo();
        SmsRecieveVo.setSendBy(account);
        SmsRecieveVo.setSendStatus(SmsSendStatusEnum.NOT_SEND.getCode());
        SmsRecieveVo.setReceivers(smsVo.getReceivers());
        smsRecieveClient.update(SmsRecieveVo,id);

    }

    //获取单条
    public SmsVo findOne(String id){
        Assert.notNull(id,"id不能为空字符串或null");
        ResponseEntity<SmsVo> resultVo = smsClient.findOne(id);
        SmsVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //获取短信接收表信息
        SmsRecieveVo smsRecieveVo = new SmsRecieveVo();
        smsRecieveVo.setSmsId(id);
        ResponseEntity<List<SmsRecieveVo>> vos = smsRecieveClient.findList(smsRecieveVo);
        List<SmsRecieveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(vos);
        vo.setReceivers(voList);
        return vo;
    }

    //删除
    public void deleteLogic(String id){
        Assert.hasText(id,"id不能为空字符串或null");
        ResponseEntity<Void> resultVo = smsClient.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        //删除短信接收表信息
        SmsRecieveVo smsRecieveVo = new SmsRecieveVo();
        smsRecieveVo.setSmsId(id);
        smsRecieveClient.deleteLogic(id);
    }

    //分页
    public RestPageImpl<SmsVo> findPage(SmsPageVo smsPageVo){
        Assert.notNull(smsPageVo,"smsPageVo 不能为空");
        ResponseEntity<RestPageImpl<SmsVo>> resultVo = smsClient.findPage(smsPageVo);
        RestPageImpl<SmsVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        for (SmsVo sv:vo) {
            String id = sv.getId();
            //查询短信接收表信息
            SmsRecieveVo smsRecieveVo = new SmsRecieveVo();
            smsRecieveVo.setSmsId(id);
            ResponseEntity<List<SmsRecieveVo>> vos = smsRecieveClient.findList(smsRecieveVo);
            List<SmsRecieveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(vos);
            sv.setReceivers(voList);
        }
        return vo;
    }

    //不分页
    public List<SmsVo> findList(SmsListVo smsListVo){
        Assert.notNull(smsListVo,"smsListVo 不能为空");
        ResponseEntity<List<SmsVo>>resultVo = smsClient.findList(smsListVo);
        List<SmsVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }


    //查看短信发送状态
    public List<SmsRecieveVo> findSmsRecieveBySmsId(String id){
        Assert.notNull(id,"id不能为空字符串或null");
        //获取短信接收表信息
        SmsRecieveVo smsRecieveVo = new SmsRecieveVo();
        smsRecieveVo.setSmsId(id);
        ResponseEntity<List<SmsRecieveVo>> vos = smsRecieveClient.findList(smsRecieveVo);
        List<SmsRecieveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(vos);
        return voList;
    }

    //短信发送接口
    public void sendMsg(SendMsgVo sendMsgVo, OAuth2Authentication principal){

        //TODO 短信发送接口
        String content = sendMsgVo.getContent();
        String smsId = sendMsgVo.getSmsId();
        List<String> receiveTels = sendMsgVo.getReceiveTels();

        //...

        //更新主表状态
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String account = userMap.get("username").toString();

        ResponseEntity<SmsVo> resultVo = smsClient.findOne(smsId);
        SmsVo vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        Assert.notNull(vo,"SmsVo 不能为null");
        //获取短信接收表信息
        SmsRecieveVo smsRecieveVo = new SmsRecieveVo();
        smsRecieveVo.setSmsId(smsId);
        ResponseEntity<List<SmsRecieveVo>> vos = smsRecieveClient.findList(smsRecieveVo); //从表对象
        List<SmsRecieveVo> voList = ResponseEntityUtils.achieveResponseEntityBody(vos);

        LocalDateTime now = utilsFeignClient.now().getBody();
        vo.setUpdateBy(account);
        vo.setSendStatus(SmsSendStatusEnum.HAS_SEND.getCode());
        vo.setSendEndTime(now);
        smsClient.update(vo,smsId);

    }

}
