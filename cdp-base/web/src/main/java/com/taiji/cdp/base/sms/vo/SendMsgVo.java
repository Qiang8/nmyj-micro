package com.taiji.cdp.base.sms.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//短信发送对象
public class SendMsgVo {

    public SendMsgVo(){}

    public SendMsgVo(String content,String smsId,List<String> receiveTels){
        this.content = content;
        this.smsId = smsId;
        this.receiveTels = receiveTels;
    }

    @Getter@Setter
    private String content;

    @Getter@Setter
    private String smsId;

    @Getter@Setter
    private List<String> receiveTels;

}
