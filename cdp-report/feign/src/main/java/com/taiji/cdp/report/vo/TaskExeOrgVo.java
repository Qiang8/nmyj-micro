package com.taiji.cdp.report.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 任务执行单位
 */
public class TaskExeOrgVo extends IdVo<String>{

    public TaskExeOrgVo(){}

    /**
     * 任务信息ID
     */
    @Getter@Setter
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

    /**
     * 获取 跟该负责单位相关的 接收人对象list
     */
    @Getter@Setter
    private List<TaskReceiveVo> taskReceive;

}
