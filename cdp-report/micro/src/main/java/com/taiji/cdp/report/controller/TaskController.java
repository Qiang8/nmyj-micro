package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.common.enums.TaskReadFlagEnum;
import com.taiji.cdp.report.entity.Task;
import com.taiji.cdp.report.entity.TaskExeOrg;
import com.taiji.cdp.report.entity.TaskReceive;
import com.taiji.cdp.report.feign.ITaskRestService;
import com.taiji.cdp.report.mapper.TaskExeOrgMapper;
import com.taiji.cdp.report.mapper.TaskMapper;
import com.taiji.cdp.report.mapper.TaskRecMapper;
import com.taiji.cdp.report.service.TaskExeOrgService;
import com.taiji.cdp.report.service.TaskRecService;
import com.taiji.cdp.report.service.TaskService;
import com.taiji.cdp.report.vo.TaskExeOrgVo;
import com.taiji.cdp.report.vo.TaskReceiveVo;
import com.taiji.cdp.report.vo.TaskVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 任务管理接口实现类controller
 * @author qizhijie-pc
 * @date 2019年1月16日11:52:55
 */
@Slf4j
@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController implements ITaskRestService{

    TaskService taskService;
    TaskExeOrgService taskExeOrgService;
    TaskRecService taskRecService;
    TaskMapper taskMapper;
    TaskExeOrgMapper taskExeOrgMapper;
    TaskRecMapper taskRecMapper;

    /**
     * 新增任务vo，级联保存exeOrg
     *
     * @param vo
     * @return ResponseEntity<TaskVo>
     */
    @Override
    public ResponseEntity<TaskVo> create(
            @Validated
            @NotNull(message = "TaskVo 不能为null")
            @RequestBody TaskVo vo) {
        Map<String,Object> orgMap = vo.getOrgMap(); //获取负责单位 map: {orgId:orgName}
        Task entity = taskMapper.voToEntity(vo);
        Task result = taskService.create(entity);
        TaskVo taskVo = taskMapper.entityToVo(result);
        Map<String,Object> resultMap = taskExeOrgService.createList(orgMap,taskVo.getId());
        taskVo.setOrgMap(resultMap);
        return ResponseEntity.ok(taskVo);
    }

    /**
     * 更新任务vo，级联保存exeOrg
     *
     * @param vo
     * @param id
     * @return ResponseEntity<TaskVo>
     */
    @Override
    public ResponseEntity<TaskVo> update(
            @Validated
            @NotNull(message = "TaskVo 不能为null")
            @RequestBody TaskVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        Map<String,Object> orgMap = vo.getOrgMap(); //获取负责单位 map: {orgId:orgName}
        Task entity = taskMapper.voToEntity(vo);
        Task result = taskService.update(entity,id);
        TaskVo taskVo = taskMapper.entityToVo(result);
        Map<String,Object> resultMap = taskExeOrgService.updateList(orgMap,id);
        taskVo.setOrgMap(resultMap);
        return ResponseEntity.ok(taskVo);
    }

    /**
     * 根据id获取单条任务信息vo
     *
     * @param id
     * @return ResponseEntity<TaskVo>
     */
    @Override
    public ResponseEntity<TaskVo> findOneTask(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        Task result = taskService.findOne(id);
        TaskVo taskVo = taskMapper.entityToVo(result);
        Map<String,Object> resultMap = taskExeOrgService.findMapByTaskId(id);
        taskVo.setOrgMap(resultMap);
        return ResponseEntity.ok(taskVo);
    }

    /**
     * 根据id删除单条任务信息vo，级联删除exeOrg
     *
     * @param id
     * @return ResponseEntity<TaskVo>
     */
    @Override
    public ResponseEntity<Void> deleteTask(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        taskService.deleteLogic(id, DelFlagEnum.DELETE);
        taskExeOrgService.deleteList(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据条件查询任务信息列表-分页(发送方)
     *
     * @param params page,size
     *               参数：infoTitle(舆情标题),title(任务标题),createTimeStart(创建开始时间),createTimeEnd(创建结束时间),
     *               status(任务状态：0未下发 1已下发),orgId(创建部门ID)
     * @return RestPageImpl<TaskVo>
     */
    @Override
    public ResponseEntity<RestPageImpl<TaskVo>> search(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<Task> taskPage = taskService.findPage(params,pageable);
        RestPageImpl<TaskVo> voPage = taskMapper.entityPageToVoPage(taskPage,pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 任务下发时，批量保存 receiver
     *
     * @param list
     * @return ResponseEntity<List < TaskReceiveVo>>
     */
    @Override
    public ResponseEntity<List<TaskReceiveVo>> createReceivers(@RequestBody List<TaskReceiveVo> list) {
        if(!CollectionUtils.isEmpty(list)){
            List<TaskReceive> entities = taskRecMapper.voListToEntityList(list);
            List<TaskReceive> result = taskRecService.createReceivers(entities);
            List<TaskReceiveVo> resultVo = taskRecMapper.entityListToVoList(result);
            return ResponseEntity.ok(resultVo);
        }else{
            return null;
        }
    }

    /**
     * 根据任务id获取所有 接收部门状态及已接收人员状态
     *
     * @param taskId
     * @return ResponseEntity<List < TaskExeOrgVo>>
     */
    @Override
    public ResponseEntity<List<TaskExeOrgVo>> viewTaskStatus(
            @NotEmpty(message = "taskId 不能为空字符串")
            @RequestParam("taskId") String taskId) {
        List<TaskExeOrg> orgList = taskExeOrgService.findListByTaskId(taskId);
        List<TaskExeOrgVo> saveResult = taskExeOrgMapper.entityListToVoList(orgList);
        List<TaskExeOrgVo> result = new ArrayList<>();
        if(!CollectionUtils.isEmpty(saveResult)){
            for(TaskExeOrgVo taskExeOrgVo : saveResult){
                List<TaskReceive> list = taskRecService.findRecsByOrgIdAndTaskId(taskExeOrgVo.getOrgId(),taskId, TaskReadFlagEnum.HAS_READ.getCode());
                List<TaskReceiveVo> voList = taskRecMapper.entityListToVoList(list);
                taskExeOrgVo.setTaskReceive(voList);
                result.add(taskExeOrgVo);
            }
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 根据条件查询接收任务信息列表-分页(接收方)
     *
     * @param params page,size
     *               参数：infoTitle(舆情标题),title(任务标题),sendTimeStart(发送开始时间),sendTimeEnd(发送结束时间),
     *               readFlag(已读标识：0否1是),receiveId(接收用户id)
     */
    @Override
    public ResponseEntity<RestPageImpl<TaskVo>> findRecTasks(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<TaskReceive> taskPage = taskRecService.findPage(params,pageable);
        //重写mapper
        RestPageImpl<TaskVo> voPage = taskMapper.taskRecPageToTaskVoPage(taskPage,pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 统计使用（孙毅添加）
     */
    @Override
    public ResponseEntity<List<TaskVo>> searchAllStat(@RequestParam MultiValueMap<String, Object> params) {
        List<TaskReceive> taskPage = taskRecService.searchAllStat(params);
        //重写mapper
        List<TaskVo> voPage = taskMapper.taskRecToTaskVo(taskPage);
        return ResponseEntity.ok(voPage);
    }


    /**
     * 标为已读，批量更改 receiver 状态 和 exeOrg对象状态
     *
     * @param params 参数：List<taskId> taskIds(任务ids)
     */
    @Override
    public ResponseEntity<Void> updateTasksByRead(@RequestParam MultiValueMap<String, Object> params) {
        if(params.containsKey("taskIds")&&params.containsKey("receiveId")&&params.containsKey("orgId")){
            Object obj = params.get("taskIds");
            List<String> taskIds = (List<String>)obj;
            String receiveId = params.getFirst("receiveId").toString();
            String orgId = params.getFirst("orgId").toString();
            taskRecService.updateTasksByRead(taskIds,receiveId);
            taskExeOrgService.updateListByByRead(taskIds,orgId);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
