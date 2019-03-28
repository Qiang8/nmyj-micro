package com.taiji.cdp.report.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TaskSaveVo {

    public TaskSaveVo(){}

    public TaskSaveVo(TaskVo task,List<String> orgIds){
        this.task = task;
        this.orgIds = orgIds;
    }

    /**
     * 任务对象
     */
    @Getter@Setter
    private TaskVo task;

    /**
     * 负责部门orgID数组
     */
    @Getter@Setter
    private List<String> orgIds;

}
