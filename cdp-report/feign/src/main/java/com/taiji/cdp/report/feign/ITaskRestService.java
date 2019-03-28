package com.taiji.cdp.report.feign;

import com.taiji.cdp.report.vo.TaskExeOrgVo;
import com.taiji.cdp.report.vo.TaskReceiveVo;
import com.taiji.cdp.report.vo.TaskVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-report-task")
public interface ITaskRestService {

    /**
     * 新增任务vo，级联保存exeOrg
     * @param vo
     * @return ResponseEntity<TaskVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/task")
    @ResponseBody
    ResponseEntity<TaskVo> create(@RequestBody TaskVo vo);

    /**
     * 更新任务vo，级联保存exeOrg
     * @param vo
     * @return ResponseEntity<TaskVo>
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/task/{id}")
    @ResponseBody
    ResponseEntity<TaskVo> update(@RequestBody TaskVo vo, @PathVariable("id") String id);

    /**
     * 根据id获取单条任务信息vo
     * @param id
     * @return ResponseEntity<TaskVo>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/task/{id}")
    @ResponseBody
    ResponseEntity<TaskVo> findOneTask(@PathVariable("id") String id);

    /**
     * 根据id删除单条任务信息vo，级联删除exeOrg
     * @param id
     * @return ResponseEntity<TaskVo>
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/task/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteTask(@PathVariable("id") String id);

    /**
     * 根据条件查询任务信息列表-分页(发送方)
     * @param params
     * page,size
     * 参数：infoTitle(舆情标题),title(任务标题),createTimeStart(创建开始时间),createTimeEnd(创建结束时间),
     * status(任务状态：0未下发 1已下发),orgId(创建部门ID)
     * @return RestPageImpl<TaskVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/task/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<TaskVo>> search(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 任务下发时，批量保存 receiver
     * @param list
     * @return ResponseEntity<List<TaskReceiveVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/receivers")
    @ResponseBody
    ResponseEntity<List<TaskReceiveVo>> createReceivers(@RequestBody List<TaskReceiveVo> list);

    /**
     * 根据任务id获取所有 接收部门状态及已接收人员状态
     * @param taskId
     * @return ResponseEntity<List<TaskExeOrgVo>>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/viewStatus")
    @ResponseBody
    ResponseEntity<List<TaskExeOrgVo>> viewTaskStatus(@RequestParam("taskId") String taskId);

    /**
     *根据条件查询接收任务信息列表-分页(接收方)
     * @param params
     * page,size
     * 参数：infoTitle(舆情标题),title(任务标题),sendTimeStart(发送开始时间),sendTimeEnd(发送结束时间),
     * readFlag(已读标识：0否1是),receiveId(接收用户id)
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/recTasks")
    @ResponseBody
    ResponseEntity<RestPageImpl<TaskVo>> findRecTasks(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 统计使用（孙毅添加）
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/searchAllStat")
    @ResponseBody
    ResponseEntity<List<TaskVo>> searchAllStat(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 标为已读，批量更改 receiver 状态 和 exeOrg对象状态
     * @param params
     * 参数：String[] taskIds(任务ids),receiveId(userId),orgId(user所在单位)
     */
    @RequestMapping(method = RequestMethod.POST,path = "/update/readTasks")
    @ResponseBody
    ResponseEntity<Void> updateTasksByRead(@RequestParam MultiValueMap<String,Object> params);

}
