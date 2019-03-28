package com.taiji.cdp.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 任务接收Vo
 */
public class TaskReceiveVo extends IdVo<String>{

    public TaskReceiveVo(){}

    /**
     * 任务信息ID
     */
    @Getter@Setter
    private TaskVo task;

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
     * 接收时间(标为已读)
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
    private LocalDateTime sendTime;

}
