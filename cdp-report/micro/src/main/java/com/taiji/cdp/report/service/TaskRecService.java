package com.taiji.cdp.report.service;

import com.taiji.cdp.report.entity.Task;
import com.taiji.cdp.report.entity.TaskReceive;
import com.taiji.cdp.report.repository.TaskRecRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskRecService extends BaseService<TaskReceive,String>{

    TaskRecRepository repository;

    //批量创建任务接收者
    public List<TaskReceive> createReceivers(List<TaskReceive> entities){
        List<TaskReceive> list = repository.save(entities);
        return list;
    }

    /**
     * 根据 orgId和 taskId获取接收者
     * @param orgId 负责单位orgId
     * @param readFlag 是否已读:0否1是
     * @param taskId 任务id
     */
    public List<TaskReceive> findRecsByOrgIdAndTaskId(String orgId,String taskId,String readFlag){
        return repository.findRecsByOrgIdAndTaskId(orgId,taskId,readFlag);
    }

    /**
     * 更新接收人对象 已读状态
     * @param receiveId 接收人id
     * @param taskIds 任务id
     */
    public void updateTasksByRead(List<String> taskIds,String receiveId){
        repository.updateTasksByRead(taskIds,receiveId);
    }

    //分页查询-- 接收方
    public Page<TaskReceive> findPage(MultiValueMap<String, Object> params, Pageable pageable){
        return repository.findPage(params,pageable);
    }

    //分页查询-- 接收方（孙毅添加）
    public List<TaskReceive> searchAllStat(MultiValueMap<String, Object> params){
        return repository.searchAllStat(params);
    }

}
