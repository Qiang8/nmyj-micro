package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.service.TaskService;
import com.taiji.cdp.report.vo.TaskExeOrgVo;
import com.taiji.cdp.report.vo.TaskReceiveVo;
import com.taiji.cdp.report.vo.TaskSaveVo;
import com.taiji.cdp.report.vo.TaskVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class TaskController {

    TaskService service;

    /**
     * 新增任务信息
     * @param vo
     * @param principal 用户信息
     */
    @PostMapping(path = "/tasks")
    public ResultEntity addTaskVo(
            @Validated
            @NotNull(message = "TaskSaveVo 不能为null")
            @RequestBody TaskSaveVo vo, OAuth2Authentication principal){
        TaskSaveVo result = service.addTaskVo(vo,principal);
        return ResultUtils.success(result);
    }

    /**
     * 修改任务信息
     * @param vo
     * @param id
     * @param principal 用户信息
     */
    @PutMapping(path = "/tasks/{id}")
    public ResultEntity updateTaskVo(
            @Validated
            @NotNull(message = "TaskSaveVo 不能为null")
            @RequestBody TaskSaveVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id
            ,OAuth2Authentication principal){
        TaskSaveVo result = service.updateTaskVo(vo,id,principal);
        return ResultUtils.success(result);
    }

    /**
     * 获取单条任务信息
     * @param id
     */
    @GetMapping(path = "/tasks/{id}")
    public ResultEntity findTaskVoById(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        TaskSaveVo result = service.findTaskVoById(id);
        return ResultUtils.success(result);
    }

    /**
     * 删除任务信息
     * @param id
     */
    @DeleteMapping(path = "/tasks/{id}")
    public ResultEntity deleteTask(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        service.deleteTask(id);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询任务信息列表-分页  --发送方
     * @param map
     * {
        "infoTitle": "string",
        "title": "string",
        "createTimeStart": "2019-01-17T01:52:22.883Z",
        "createTimeEnd": "2019-01-17T01:52:22.883Z",
        "status": "string",
        "orgId": "string",
        "page": 0,
        "size": 0
        }
     * @param principal 用户信息
     */
    @PostMapping(path = "/tasks/search")
    public ResultEntity findTasks(
            @RequestBody Map<String,Object> map, OAuth2Authentication principal){
        if(map.containsKey("page")&&map.containsKey("size")){
            RestPageImpl<TaskVo> result = service.findTasks(map,principal);
            return ResultUtils.success(result);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 任务信息下发，后台更新Task表的status和sendTime字段，并且将接收部门的人员的相关初始数据入库TaskReceive表
     * @param taskId 任务id
     * @param principal 用户信息
     */
    @PostMapping(path = "/tasks/send")
    public ResultEntity sendTask(
            @NotEmpty(message = "taskId 不能为空字符串")
            @RequestParam("taskId") String taskId, OAuth2Authentication principal){
        service.sendTask(taskId,principal);
        service.sendInfoMsg(taskId);
        return ResultUtils.success();
    }

    /**
     * 根据任务ID查询接收状态信息列表(获取所有接收部门的接收状态及部门下仅已接收人员的列表)
     * @param taskId
     */
    @GetMapping(path = "/taskRecs/viewStatus")
    public ResultEntity findTaskRecStatus(
            @NotEmpty(message = "taskId 不能为空字符串")
            @RequestParam("taskId") String taskId){
        List<TaskExeOrgVo> result = service.findTaskRecStatus(taskId);
        return ResultUtils.success(result);
    }

    /**
     * 根据条件查询接收任务信息列表-分页 -- 接收方
     * @param map
     * {
        "infoTitle": "string",
        "title": "string",
        "sendTimeStart": "2019-01-17T02:02:49.253Z",
        "sendTimeEnd": "2019-01-17T02:02:49.253Z",
        "readFlag": "string",
        "page": 0,
        "size": 0
        }
     * @param principal 用户信息
     */
    @PostMapping(path = "/taskRecs/search")
    public ResultEntity findRecTasks(
            @RequestBody Map<String,Object> map, OAuth2Authentication principal){
        if(map.containsKey("page")&&map.containsKey("size")){
            RestPageImpl<TaskVo> result = service.findRecTasks(map,principal);
            return ResultUtils.success(result);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 查看个人任务信息，标记为已读
     * @param taskIds
     * @param principal 用户信息
     */
    @PostMapping(path = "/taskRecs/read")
    public ResultEntity readTasks(
            @RequestBody List<String> taskIds, OAuth2Authentication principal){
            service.readTasks(taskIds,principal);
            return ResultUtils.success();
    }

}
