package com.taiji.cdp.report.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 任务管理 --负责单位实体对象
 * @author qizhijie-pc
 * @date 2019年1月16日15:13:47
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CJ_TASK_EXEORG")
public class TaskExeOrg extends IdEntity<String>{

    public TaskExeOrg(){}

    /**
     * 任务信息ID
     */
    @Getter
    @Setter
    @NotEmpty(message = "任务信息ID 不能为空")
    @Length(max=36,message = "任务信息ID taskId 最大长度不能超过36")
    private String taskId;

    /**
     * 负责部门ID
     */
    @Getter@Setter
    @NotEmpty(message = "负责部门ID 不能为空")
    @Length(max=36,message = "负责部门ID orgId 最大长度不能超过36")
    private String orgId;

    /**
     * 负责部门名称
     */
    @Getter@Setter
    @Length(max=100,message = "负责部门名称  orgName 最大长度不能超过100")
    private String orgName;

    /**
     * 已读标识：0否1是
     */
    @Getter@Setter
    @Length(max=1,message = "已读标识  readFlag 最大长度不能超过1")
    private String readFlag;


}
