package com.taiji.cdp.base.sms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class SmsVo extends BaseVo<String> {
    public SmsVo(){}
    @Getter
    @Setter
    @Length(max = 1000,message = "短信内容content字段最大长度1000")
    private String content;

    @Getter
    @Setter
    @Length(max = 36,message = "创建单位ID buildOrgId字段最大长度36")
    private String buildOrgId;

    @Getter
    @Setter
    @Length(max = 100,message = "创建单位buildOrgName字段最大长度100")
    private String buildOrgName;

    @Getter
    @Setter
    @Length(max = 1,message = "发送状态sendStatus字段最大长度1")
    private String sendStatus;

    @Getter
    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime sendStartTime;

    @Getter
    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime sendEndTime;

    @Getter
    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime sendTime;

    @Getter
    @Setter
    private List<SmsRecieveVo> receivers;
}
