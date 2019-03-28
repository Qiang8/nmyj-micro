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
import java.util.List;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-18 18:08
 **/
public class ApprovalVo extends IdVo<String> {
    @Getter
    @Setter
    @Length(max = 36, message = "舆情信息ID infoId最大长度不能超过36")
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

    @Getter
    @Setter
    private List<String> approvalAdviceIds;
}