package com.taiji.cdp.base.sms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public class SmsRecieveVo extends IdVo<String> {
    public SmsRecieveVo(){}

    @Getter
    @Setter
    @Length(max = 36,message = "短信ID smsId字段最大长度36")
    private String smsId;

    @Getter
    @Setter
    @Length(max = 50,message = "发送人sendBy字段最大长度50")
    private String sendBy;

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
    private LocalDateTime sendTime;

    @Getter
    @Setter
    @Length(max = 16,message = "接收人手机receiverTel字段最大长度16")
    private String receiverTel;

    @Getter
    @Setter
    @Length(max = 50,message = "接收人receiverName字段最大长度50")
    private String receiverName;

    @Getter
    @Setter
    @Length(max = 1,message = "接收状态sendStatus字段最大长度1")
    private String recieveStatus;

    @Getter
    @Setter
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)

    private LocalDateTime receiveTime;

    @Getter
    @Setter
    private List<SmsRecieveVo> receivers;
}
