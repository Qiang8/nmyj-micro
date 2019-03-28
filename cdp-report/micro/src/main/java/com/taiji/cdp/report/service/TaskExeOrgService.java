package com.taiji.cdp.report.service;

import com.taiji.cdp.report.common.enums.TaskReadFlagEnum;
import com.taiji.cdp.report.entity.TaskExeOrg;
import com.taiji.cdp.report.repository.TaskExeOrgRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskExeOrgService extends BaseService<TaskExeOrg,String> {

    TaskExeOrgRepository repository;

    //创建 --批量处理exeOrg对象
    public Map<String,Object> createList(Map<String,Object> orgMap,String taskId){
        if(orgMap.isEmpty()){
            return null;
        }
        List<TaskExeOrg> entities = new ArrayList<>();
        for(Map.Entry<String,Object> entity : orgMap.entrySet()){
            TaskExeOrg taskExeOrg = new TaskExeOrg();
            String orgId = entity.getKey();
            String orgName = entity.getValue().toString();
            taskExeOrg.setOrgId(orgId);
            taskExeOrg.setOrgName(orgName);
            taskExeOrg.setTaskId(taskId);
            taskExeOrg.setReadFlag(TaskReadFlagEnum.NOT_READ.getCode());
            entities.add(taskExeOrg);
        }
        List<TaskExeOrg> list = createList(entities);
        Map<String,Object> map = list.stream().collect(Collectors.toMap(TaskExeOrg::getOrgId,TaskExeOrg::getOrgName));
        return map;
    }

    //创建 exeOrg对象
    public List<TaskExeOrg> createList(List<TaskExeOrg> entities){
        if(!CollectionUtils.isEmpty(entities)){
            List<TaskExeOrg> list = new ArrayList<>();
            for(TaskExeOrg entity : entities){
                list.add(repository.create(entity));
            }
            return list;
        }else{
            return null;
        }
    }

    //更新 --批量处理exeOrg对象
    public Map<String,Object> updateList(Map<String,Object> orgMap,String taskId){

        List<TaskExeOrg> oldList = repository.findByTaskId(taskId);
        Map<String,TaskExeOrg> oldMap = oldList.stream().collect(Collectors.toMap(temp -> temp.getOrgId(),temp -> temp));

        List<TaskExeOrg> entities = new ArrayList<>();
        for(Map.Entry<String,Object> entity : orgMap.entrySet()){
            String orgId = entity.getKey();
            if(oldMap.containsKey(orgId)){ //库中仍然存在
                TaskExeOrg old = oldMap.get(orgId);
                oldList.remove(oldMap.get(orgId));
                entities.add(old);
            }else{ //新增入库
                String orgName = entity.getValue().toString();
                TaskExeOrg taskExeOrg = new TaskExeOrg();
                taskExeOrg.setOrgId(orgId);
                taskExeOrg.setOrgName(orgName);
                taskExeOrg.setTaskId(taskId);
                taskExeOrg.setReadFlag(TaskReadFlagEnum.NOT_READ.getCode());
                TaskExeOrg result = repository.save(taskExeOrg);
                entities.add(result);
            }
        }

        if(!CollectionUtils.isEmpty(oldList)){ //剩下的是需要删除的
            repository.delete(oldList); //批量删除
        }

        Map<String,Object> map = entities.stream().collect(Collectors.toMap(TaskExeOrg::getOrgId,TaskExeOrg::getOrgName));
        return map;
    }

    //根据任务id 获取exeOrg对象
    public Map<String,Object> findMapByTaskId(String taskId){
        Assert.hasText(taskId,"taskId 不能为空字符串");
        List<TaskExeOrg> list = repository.findByTaskId(taskId);
        Map<String,Object> map = list.stream().collect(Collectors.toMap(TaskExeOrg::getOrgId,TaskExeOrg::getOrgName));
        return map;
    }

    //根据任务id 获取exeOrg对象
    public List<TaskExeOrg> findListByTaskId(String taskId){
        Assert.hasText(taskId,"taskId 不能为空字符串");
        List<TaskExeOrg> list = repository.findByTaskId(taskId);
        return list;
    }

    //根据id 批量删除exeOrg对象
    public void deleteList(String taskId){
        repository.deleteListByTaskId(taskId);
    }

    //更新exeOrg已读状态
    public void updateListByByRead(List<String> taskIds,String orgId){
        repository.updateListByByRead(taskIds,orgId);
    }


    /**
     * 统计下发给本单位的任务数量
     * cj_task_exeorg表中的org_id=登录人所属部门
     */
    public List<TaskExeOrg> findStatTasks(String orgId){
        List<TaskExeOrg> statSources = repository.findStatTasks(orgId);
        return statSources;
    }

}
