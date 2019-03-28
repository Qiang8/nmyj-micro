package com.taiji.cdp.report.entity;

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
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 任务管理 --任务实体对象
 * @author qizhijie-pc
 * @date 2019年1月16日15:13:47
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CJ_TASK")
public class Task extends BaseEntity<String> implements DelFlag{

    public Task(){}

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
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
