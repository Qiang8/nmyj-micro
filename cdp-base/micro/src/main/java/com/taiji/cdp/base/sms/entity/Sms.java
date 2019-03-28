package com.taiji.cdp.base.sms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "BM_SMS")
public class Sms extends BaseEntity<String> implements DelFlag {
    public Sms(){}
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
    private LocalDateTime sendTime;

    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
