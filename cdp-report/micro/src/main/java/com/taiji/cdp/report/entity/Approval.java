package com.taiji.cdp.report.entity;

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
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-18 17:05
 **/
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CJ_CONS_APPROVAL")
public class Approval extends IdEntity<String> {
    /**
     * 舆情信息id
     */
    @Getter
    @Setter
    @Length(max = 36, message = "舆情信息ID 最大长度不能超过36")
    @NotEmpty(message = "舆情信息id 不能为空字符串")
    private String infoId;


    @Getter
    @Setter
    @Length(max = 20, message = "提交人 最大长度不能超过20")
    private String submitter;

    /**
     * 提交时间
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
    private LocalDateTime submitTime;
    /**
     * 审批节点：监管中心/带班处长center_audit 、办领导leader_audit
     */
    @Getter
    @Setter
    @Length(max = 36, message = "审批节点 最大长度不能超过36")
    private String approvalNode;

    @Getter
    @Setter
    @Length(max = 36, message = "审批人ID  最大长度不能超过36")
    private String approvalerId;

    @Getter
    @Setter
    @Length(max = 20, message = "审批人名称  最大长度不能超过20")
    private String approvaler;

    /**
     * 审批时间
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
    private LocalDateTime approvalTime;

    /**
     * 审批结果：0同意 1退回
     */
    @Getter
    @Setter
    @Length(max = 1, message = "审批结果  最大长度不能超过1")
    private String approvalResult;


    @Getter
    @Setter
    @Length(max = 1000, message = "审批意见  最大长度不能超过1000")
    private String approvalOpinion;


}

