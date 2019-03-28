package com.taiji.cdp.report.feign;

import com.taiji.cdp.report.vo.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 统计信息接口服务类
 * @author sunyi
 * @date 2019年1月22日
 */
@FeignClient(value = "micro-report-stat-cons")
public interface IStatRestService {

    /**
     * 统计报舆情数量（统计使用）
     * @param statVQueryVo 统计周期
     * @return ResponseEntity<Integer>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/statConsInfo")
    @ResponseBody
    ResponseEntity<List<ConsInfoVo>> findStatCons(
            @RequestBody StatVQueryVo statVQueryVo);


    /**
     * 统计下发给本单位的任务数量（本年度）
     * @param statVQueryVo
     * cj_task_exeorg表中的org_id=登录人所属部门
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/findStatTasks")
    @ResponseBody
    ResponseEntity<List<TaskVo>> findStatTasks(
            @RequestBody StatVQueryVo statVQueryVo);




    /**
     * 舆情信息按新增舆情和重点舆情分类进行统计（近七日）
     * @param orgId   不为空查 本单位 为空查全部
     * cr_cons_info表的status=【1-7】，cj_cons_judge表的tag_type舆情标签类型：0新增舆情 1重点舆情
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/findStatTypes")
    @ResponseBody
    ResponseEntity<StatTimeTypeVo> findStatTypes(
            @RequestParam("orgId") String orgId);



}
