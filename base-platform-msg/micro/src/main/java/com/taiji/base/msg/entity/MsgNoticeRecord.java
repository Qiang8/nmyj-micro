package com.taiji.base.msg.entity;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * <p>Title:MsgNoticeRecord.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/29 15:44</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "MSG_NOTICE_RECORD")
public class MsgNoticeRecord extends IdEntity<String> {

    /**
     * 消息Id
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = MsgNotice.class)
    @JoinColumn(name = "NOTICE_ID", referencedColumnName = "id")
    private MsgNotice msgNotice;

    /**
     * 接收者
     */
    @Length(max = 36, message = "接收者receiverId字段最大长度36")
    @Getter
    @Setter
    private String receiverId;

    /**
     * 接收者名称
     */
    @Length(max = 200, message = "接收者名称 receiveName字段最大长度200")
    @Getter
    @Setter
    private String receiverName;

    /**
     * 接收人单位编码
     */
    @Length(max = 36, message = "接收人单位编码deptId字段最大长度36")
    @Getter
    @Setter
    private String deptId;

    /**
     * 接收人单位名称
     */
    @Length(max = 200, message = "接收人单位名称 deptName字段最大长度200")
    @Getter
    @Setter
    private String deptName;

    /**
     * 已读标志 0 未读 1 已读
     */
    @Length(max = 1, message = "已读标志 readFlag字段最大长度1")
    @Getter
    @Setter
    private String readFlag;

    /**
     * 读取时间
     */
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @Getter
    @Setter
    private LocalDateTime readTime;

    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @CreatedDate
    @Getter
    @Setter
    private LocalDateTime createTime;
}
