package com.taiji.cdp.base.caseMa.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 案例Vo
 */
public class CaseVo extends BaseVo<String>{

    public CaseVo(){}

    /**
     * 舆情信息ID
     */
    @Getter@Setter
    @Length(max=36,message = "舆情信息ID infoId 最大长度不能超过36")
    private String infoId;

    /**
     * 调控单ID
     */
    @Getter@Setter
    @Length(max=36,message = "调控单ID cdId 最大长度不能超过36")
    private String cdId;

    /**
     * 任务标题
     */
    @Getter@Setter
    @NotEmpty(message = "任务标题 title 不能为空")
    @Length(max=200,message = "任务标题 title 最大长度不能超过200")
    private String title;


    /**
     * 首发时间
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
    private LocalDateTime startTime;

    /**
     * 首发网站
     */
    @Getter@Setter
    @NotEmpty(message = "首发网站 startWebsite 不能为空")
    @Length(max=50,message = "任务标题 title 最大长度不能超过50")
    private String startWebsite;

    /**
     * 案例类型ID字符串，以逗号隔开
     * */
    @Getter@Setter
    @Length(max=200,message = "案例类型ID caseTypeIds 最大长度不能超过200")
    private String caseTypeIds;

    /**
     * 案例类型名称字符串，以逗号隔开
     */
    @Getter@Setter
    @Length(max=200,message = "案例类型名称 caseTypeNames 最大长度不能超过200")
    private String caseTypeNames;

    /**
     * 备注
     */
    @Getter@Setter
    @Length(max=1000,message = "备注 notes 最大长度不能超过1000")
    private String notes;

    /**
     * 删除标志（0：已删除；1：未删除）
     */
    @Getter@Setter
    @Length(max=1,message = "任务状态 taskStatus 最大长度不能超过1")
    private String delFlag;

}
