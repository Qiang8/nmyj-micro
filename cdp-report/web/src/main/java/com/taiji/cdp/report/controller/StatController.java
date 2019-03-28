package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.service.StatService;
import com.taiji.cdp.report.vo.StatTimeTypeVo;
import com.taiji.cdp.report.vo.StatVQueryVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/stats")
public class StatController {

    StatService statService;

    /**
     * 统计本单位上报舆情数量
     * cr_cons_info表的create_org_id=登录人所属部门&status=【1-8】的记录
     * @param vo  vo.getTimeCycle 统计周期
     *   1：今日，2：本周，3：本月，4：本年
     */
    @PostMapping(path = "/statOwnReports")
    public ResultEntity findOwnReports(
            @RequestBody StatVQueryVo vo, OAuth2Authentication principal){
        return ResultUtils.success(statService.findOwnReports(vo,principal));
    }

    /**
     * 统计本所以接收的舆情数量
     * 已接收表示cr_cons_info表的status=【1-8】；已处理表示cr_cons_info表的status=【4-8】
     * @param vo  vo.getStatFlag
     *   1：今日已接收，2：今日已处理，3：今日未处理，4：至今累计接收
     */
    @PostMapping(path = "/statAllReports")
    public ResultEntity findAllReports(
            @NotEmpty(message = "statFlag 不能为空字符串")
            @RequestBody StatVQueryVo vo){
        return ResultUtils.success(statService.findAllReports(vo));
    }


    /**
     * 统计下发给本单位的任务数量（本年度）
     * cj_task_exeorg表中的org_id=登录人所属部门
     */
    @PostMapping(path = "/statTasks")
    public ResultEntity findStatTasks(
            OAuth2Authentication principal){
        return ResultUtils.success(statService.findStatTasks(principal));
    }

    /**
     * 舆情信息上报来源统计(本年度)
     * @param vo vo.getIsOwnOrg
     * 是否统计本单位：0本单位 1全部
     * cr_cons_info表的source_type_id为来源类型ID(字典表),&status=【1-8】的记录
     */
    @PostMapping(path = "/statSources")
    public ResultEntity findStatSources(
            @NotEmpty(message = "vo 不能为空字符串")
            @RequestBody StatVQueryVo vo,OAuth2Authentication principal){
        return ResultUtils.success(statService.findStatSources(vo,principal));
    }


    /**
     * 舆情信息按新增舆情和重点舆情分类进行统计（近七日）
     * @param isOwnOrg
     * 是否统计本单位：0本单位 1全部
     * cr_cons_info表的status=【1-7】，cj_cons_judge表的tag_type舆情标签类型：0新增舆情 1重点舆情
     */
    @GetMapping(path = "/statTypes")
    public ResultEntity findStatTypes(
            @NotEmpty(message = "isOwnOrg 不能为空字符串")
            @RequestParam("isOwnOrg")String isOwnOrg,OAuth2Authentication principal){
        StatTimeTypeVo types = statService.findStatTypes(isOwnOrg, principal);
        return ResultUtils.success(types);
    }

    /**
     * 统计各渠道上报舆情数量（本年度）,--目前是根据部门相同的进行统计，待修改
     * （cr_cons_info表的status=【1-8】，createOrgId对应的组织CODE分类）
     */
    @PostMapping(path = "/statWays")
    public ResultEntity findStatWays(){
        return ResultUtils.success(statService.findStatWays());
    }

    /**
     * 统计舆情报送有效率（本年度）
     * （cr_cons_info表的status,总数【1-8】，有效率【1/2/3/4/6/7】）
     * 返回上报总数 和 上报有效数量总数
     */
    @PostMapping(path = "/statValids")
    public ResultEntity findStatValid(){
        return ResultUtils.success(statService.findStatValids());
    }



    /**
     * 统计本单位上报舆情总数量（盟市）
     * 0待上报 1已上报
     */
    @GetMapping(path = "/reports")
    public ResultEntity findStatReports(OAuth2Authentication principal){
        return ResultUtils.success(statService.findStatReports(principal));
    }


    /**
     * 统计接收处理舆情总数(自治区)
     * 0待处理 1已处理 2已退回
     */
    @GetMapping(path = "/reportRecs")
    public ResultEntity findStatReportRecs(){
        return ResultUtils.success(statService.findStatReportRecs());
    }


    /**
     * 统计审批舆情总数(自治区)
     * 00个人待审批 列表，01个人已审批列表
     */
    @GetMapping(path = "/approvals")
    public ResultEntity findStatApprovals(
            OAuth2Authentication principal){
        return ResultUtils.success(statService.findStatApprovals(principal));
    }
    /**
     * 统计本单位接收任务总数（盟市）
     * 0未接收 1已接收
     */
    @GetMapping(path = "/taskRecs")
    public ResultEntity findStatTaskRecs(
            OAuth2Authentication principal){
        return ResultUtils.success(statService.findStatTaskRecs(principal));
    }
}
