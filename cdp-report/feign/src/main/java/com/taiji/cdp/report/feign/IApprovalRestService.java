package com.taiji.cdp.report.feign;

import com.taiji.cdp.report.vo.ApprovalVo;
import com.taiji.cdp.report.vo.ConsInfoSearchVo;
import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.cdp.report.vo.PreCmdsVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-19 17:40
 **/
@FeignClient(value = "micro-report-approval")
public interface IApprovalRestService {
    /**
     * 新增舆情审批信息
     * @param approvalVO
     */
    @RequestMapping(method = RequestMethod.POST, path = "/addApproval")
    @ResponseBody
    ResponseEntity<Void> addApproval(@RequestBody ApprovalVo approvalVO);

    /**
     * 根据舆情ID查询舆情审批记录
     * @param infoId 舆情ID
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findApprovalByInfoId/{infoId}")
    @ResponseBody
    ResponseEntity<List<ApprovalVo>> findApprovalByInfoId(@PathVariable("infoId") String infoId);

    /**
     * 根据舆情审批信息ID 更新舆情信息
     * @param approvalVO
     */
    @RequestMapping(method = RequestMethod.POST, path = "/updateApprovalClient")
    @ResponseBody
    ResponseEntity<Void> updateApprovalClient(@RequestBody ApprovalVo approvalVO);

    /**
     * 新增常用审批意见
     * @param approvalVO
     */
    @RequestMapping(method = RequestMethod.POST, path = "/addCommonAdvice")
    @ResponseBody
    ResponseEntity<Void> addCommonAdvice(@RequestBody ApprovalVo approvalVO);

    /**
     * 获取单条舆情信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findApprovalById/{id}")
    @ResponseBody
    ResponseEntity<ApprovalVo> findApprovalById(@PathVariable("id")String id);

    /**
     * 通过舆情ID获取调控单的各种初始化数据信息
     * @param infoId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/preCmds")
    @ResponseBody
    ResponseEntity<PreCmdsVo> findPreCmdByInfoId(@RequestParam("infoId")String infoId);

    /**
     * 通过条件查询审批建议上日报的舆情信息列表信息
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/consDailys")
    @ResponseBody
    ResponseEntity<RestPageImpl<ConsInfoVo>> findDailies(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 查询个人待审批和已审批列表
     * @param userName
     * @param tagFlag
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findByApprovalerAndResult")
    @ResponseBody
    ResponseEntity<List<ApprovalVo>> findByApprovalerAndResult(@RequestParam("userName")String userName, @RequestParam("tagFlag")String tagFlag);

    /**
     * 通过舆情ID获取该条舆情审批列表信息
     * @param map 查询条件
     *            参数：
     *            infoId:舆情ID
     *            approvalNode:审批节点：监管中心/带班处长center_audit 、办领导leader_audit
     *            approvalResult:审批结果：0同意 1退回（查看审批意见时，前端不需传值；查看退回原因时，前端传入1
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/consApprovals")
    @ResponseBody
    ResponseEntity<List<ApprovalVo>> findConsApprovals(@RequestParam Map<String, Object> map);

    /**
     * 根据条件查询舆情信息列表-分页（与审批人相关的）
     *
     * @param params page 页码
     *               size 每页的长度
     *               title 舆情名称
     *               tagFlag 00个人待审批 列表，01个人已审批列表
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/search")
    @ResponseBody
    ResponseEntity<RestPageImpl<ConsInfoSearchVo>> search(@RequestParam MultiValueMap<String, Object> params);


    /**
     * 查询舆情信息 所有（孙毅添加）
     */
    @RequestMapping(method = RequestMethod.POST, path = "/searchAll")
    @ResponseBody
    ResponseEntity<List<ConsInfoSearchVo>> searchAllStat(@RequestParam MultiValueMap<String, Object> params);
}
