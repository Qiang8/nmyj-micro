package com.taiji.cdp.base.sms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "BM_SMS_RECEIVE")
public class SmsRecieve extends IdEntity<String> {

    public SmsRecieve(){}

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
//    @CreatedDate
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
    @NotEmpty(message = "接收人不能为空")
    @Length(max = 50,message = "接收人receiverName字段最大长度50")
    private String receiverName;

    @Getter
    @Setter
    @Length(max = 1,message = "接收状态receiveStatus字段最大长度1")
    private String receiveStatus;

    @Getter
    @Setter
//    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime receiveTime;
}
