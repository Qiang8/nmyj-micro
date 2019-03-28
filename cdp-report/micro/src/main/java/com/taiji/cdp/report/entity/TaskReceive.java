package com.taiji.cdp.report.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.cdp.report.vo.TaskVo;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 任务管理 --接收用户实体对象
 * @author qizhijie-pc
 * @date 2019年1月16日15:13:47
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CJ_TASK_RECEIVE")
public class TaskReceive extends IdEntity<String> {

    public TaskReceive(){}

    /**
     * 任务信息ID
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = Task.class)
    @JoinColumn(name = "TASK_ID")
    private Task task;

    /**
     * 接收部门ID
     */
    @Getter@Setter
    @NotEmpty(message = "接收部门ID 不能为空")
    @Length(max=36,message = "接收部门ID orgId 最大长度不能超过36")
    private String orgId;

    /**
     * 接收部门名称
     */
    @Getter@Setter
    @Length(max=100,message = "接收部门名称  orgName 最大长度不能超过100")
    private String orgName;

    /**
     * 接收人ID
     */
    @Getter@Setter
    @Length(max=36,message = "接收人ID receiverId 最大长度不能超过36")
    private String receiverId;

    /**
     * 接收人姓名
     */
    @Getter@Setter
    @Length(max=50,message = "接收人姓名 receiverName 最大长度不能超过50")
    private String receiverName;

    /**
     * 已读标识：0否1是
     */
    @Getter
    @Setter
    @Length(max=1,message = "已读标识  readFlag 最大长度不能超过1")
    private String readFlag;

    /**
     * 接收时间(标为已读的时间)
     */
    @Getter@Setter
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
    private LocalDateTime readTime;

    /**
     * 发送人
     */
    @Getter@Setter
    @Length(max=20,message = "发送人 sendBy 最大长度不能超过20")
    private String sendBy;

    /**
     * 发送时间
     */
    @Getter@Setter
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
    private LocalDateTime sendTime;

}
