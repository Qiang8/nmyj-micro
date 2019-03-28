package com.taiji.cdp.cmd.vo;

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
 * 处置反馈实体对象Vo
 * @author sunyi
 * @date 2019年2月20日
 */
public class FeedbackVo extends IdVo<String> {

    public FeedbackVo() {
    }

    /**
     * 调控单ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "调控单ID cdId 最大长度不能超过36")
    @NotEmpty(message = "调控单ID cdId 不能为空字符串")
    private String cdId;

    /**
     * 反馈部门ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "反馈部门ID  orgId 最大长度不能超过36")
    private String orgId;

    /**
     * 反馈内容
     */
    @Getter
    @Setter
    @Length(max = 500,message = "反馈内容  content 最大长度不能超过500")
    private String content;

    /**
     * 反馈人
     */
    @Getter
    @Setter
    @Length(max = 20,message = "反馈人  feedbackBy 最大长度不能超过20")
    private String feedbackBy;

    /**
     * 反馈时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Getter@Setter
    private LocalDateTime feedbackTime;

    /**
     * 是否完成：0否 1是
     */
    @Getter
    @Setter
    @Length(max = 1,message = "反馈人  isComplet 最大长度不能超过1")
    private String isComplet;

    /**
     * 备注
     */
    @Getter
    @Setter
    @Length(max = 2000,message = "备注  notes 最大长度不能超过2000")
    private String notes;

}
