package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.entity.Task;
import com.taiji.cdp.report.entity.TaskExeOrg;
import com.taiji.cdp.report.feign.IStatRestService;
import com.taiji.cdp.report.mapper.ConsInfoMapper;
import com.taiji.cdp.report.mapper.TaskMapper;
import com.taiji.cdp.report.service.StatConsInfoService;
import com.taiji.cdp.report.service.TaskExeOrgService;
import com.taiji.cdp.report.service.TaskService;
import com.taiji.cdp.report.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 舆情信息接口实现类controller
 * @author qizhijie-pc
 * @date 2019年1月8日17:25:02
 */
@Slf4j
@RestController
@RequestMapping("/api/statConsInfo")
@AllArgsConstructor
public class StatController implements IStatRestService {

    StatConsInfoService service;
    TaskService taskService;
    TaskExeOrgService taskExeOrgService;

    ConsInfoMapper mapper;
    TaskMapper taskMapper;
    /**
     * 根据不同条件 统计舆情数量 （统计使用）
     * @return ResponseEntity<List<ConsInfoVo>>
     */
    @Override
    public ResponseEntity<List<ConsInfoVo>> findStatCons(
            @NotEmpty(message = "statVQueryVo 不能为空")
            @RequestBody StatVQueryVo statVQueryVo){
        return ResponseEntity.ok(mapper.entityListToVoList(service.findStatCons(statVQueryVo)));
    }


    /**
     * 统计下发给本单位的任务数量（本年度）
     */
    @Override
    public ResponseEntity<List<TaskVo>> findStatTasks(
            @NotEmpty(message = "statVQueryVo 不能为空")
            @RequestBody StatVQueryVo statVQueryVo){
        String orgId = statVQueryVo.getOrgId();
        List<TaskExeOrg> taskExeOrgList = taskExeOrgService.findStatTasks(orgId);
        List<String> taskIds = taskExeOrgList.stream().map(TaskExeOrg::getId).collect(Collectors.toList());
        List<Task> taskList = taskService.findStatList(taskIds, statVQueryVo);
        List<TaskVo> taskVos = taskMapper.entityListToVoList(taskList);
        return ResponseEntity.ok(taskVos);
    }


    /**
     * 舆情信息按新增舆情和重点舆情分类进行统计（近七日）
     */
    @Override
    public ResponseEntity<StatTimeTypeVo> findStatTypes(
            @RequestParam("orgId") String orgId){
        StatTimeTypeVo statTypes = service.findStatTypes(orgId);
        return ResponseEntity.ok(statTypes);
    }

}
