package com.taiji.cdp.report.vo;

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
import java.util.Map;

/**
 * 任务Vo
 */
public class TaskVo extends BaseVo<String>{

    public TaskVo(){}

    /**
     * 舆情信息ID
     */
    @Getter@Setter
    @Length(max=36,message = "舆情信息ID infoId 最大长度不能超过36")
    private String infoId;

    /**
     * 舆情标题
     */
    @Getter@Setter
    @Length(max=200,message = "舆情标题 infoTitle 最大长度不能超过200")
    private String infoTitle;

    /**
     * 任务标题
     */
    @Getter@Setter
    @NotEmpty(message = "任务标题 title 不能为空")
    @Length(max=200,message = "任务标题 title 最大长度不能超过200")
    private String title;

    /**
     * 任务内容
     */
    @Getter@Setter
    @Length(max=2000,message = "任务内容 content 最大长度不能超过2000")
    private String content;

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

    /**
     * 任务状态：0未下发 1已下发
     */
    @Getter@Setter
    @Length(max=1,message = "任务状态 taskStatus 最大长度不能超过1")
    private String taskStatus;

    /**
     * 创建部门ID
     */
    @Getter@Setter
    @Length(max=36,message = "创建部门ID createOrgId 最大长度不能超过36")
    private String createOrgId;

    /**
     * 创建部门名称
     */
    @Getter@Setter
    @Length(max=100,message = "创建部门名称 createOrgName 最大长度不能超过100")
    private String createOrgName;

    /**
     * 舆情链接URL
     */
    @Getter
    @Setter
    @Length(max = 500,message = "舆情链接 infoUrl字段最大长度500")
    private String infoUrl;

    /**
     * 负责部门map,
     * {orgId:orgName}
     */
    @Getter@Setter
    private Map<String,Object> orgMap;

}
