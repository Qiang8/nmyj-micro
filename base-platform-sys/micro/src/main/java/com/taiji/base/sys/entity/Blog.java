package com.taiji.base.sys.entity;

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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * <p>Title:Blog.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 19:30</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "SYS_BUSINESS_LOG")
public class Blog extends IdEntity<String> {

    public Blog()
    {}

    /**
     * 日志类型
     */
    @Length(max = 2, message = "日志类型type字段最大长度2")
    @Getter
    @Setter
    private String type;

    /**
     * 日志内容
     */
    @Length(max = 4000, message = "日志内容content字段最大长度4000")
    @Getter
    @Setter
    private String content;

    /**
     * 操作人
     */
    @Length(max = 36, message = "操作人operateBy字段最大长度36")
    @Getter
    @Setter
    private String operateBy;

    /**
     * 操作人名称
     */
    @Length(max = 50, message = "操作人名称operator字段最大长度36")
    @Getter
    @Setter
    private String operator;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter
    @Setter
    private LocalDateTime createTime;
}
