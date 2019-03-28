package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.service.ApprovalService;
import com.taiji.cdp.report.vo.*;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @program: nmyj-micro
 * @description: 舆情审批controller
 * @author: TaiJi.WangJian
 * @create: 2019-01-18 16:43
 **/
@Slf4j
@AllArgsConstructor
@RestController
public class ApprovalController {

    ApprovalService approvalService;
    /**
     * 申请舆情审批信息
     *
     * @param approvalVO
     * @return
     */
    @PostMapping(path = "/approvals")
    public ResultEntity addApproval(
            @NotNull(message =  "ApprovalVo 不能为null")
            @RequestBody ApprovalVo approvalVO, OAuth2Authentication principal) {
            approvalService.addApproval(approvalVO, principal);
            return ResultUtils.success();
    }

    /**
     * 提交舆情审批意见
     * @param approvalVO
     * @param principal
     * @return
     */
    @PutMapping(path = "/approvals/{id}")
    public ResultEntity submitApplyApproval(
            @NotNull(message = "TaskSaveVo 不能为null")
            @RequestBody ApprovalVo approvalVO,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id,
            OAuth2Authentication principal){
            approvalService.submitApplyApproval(approvalVO, principal,id);
            return ResultUtils.success();
    }

    /**
     * 获取单条舆情信息
     * @param id
     * @return
     */
    @GetMapping(path = "/approvals/{id}")
    public ResultEntity findApprovalById(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        ApprovalVo approvalVO = approvalService.findApprovalById(id);
        return ResultUtils.success(approvalVO);
    }

    /**
     * 通过条件查询审批建议上日报的舆情信息列表信息
     * @return
     */
    @PostMapping(path = "/consDailys")
    public ResultEntity findDailies(
            @NotNull(message = "ApprovalVo 不能为null")
            @RequestBody ConsDailysVo consDailysVO) {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("page", consDailysVO.getPage());
            params.add("size", consDailysVO.getSize());
            params.add("startTime", DateUtil.getDateTimeStr(consDailysVO.getStartTime()));
            params.add("endTime", DateUtil.getDateTimeStr(consDailysVO.getEndTime()));
            RestPageImpl<ConsInfoVo> pageList =  approvalService.findDailies(params);
            return ResultUtils.success(pageList);
    }
    /**
     * 通过舆情ID获取调控单的各种初始化数据信息
     * @param infoId
     * @return
     */
    @GetMapping(path = "/preCmds")
    public ResultEntity findPreCmdByInfoId(
            @NotEmpty(message = "id 不能为空字符串")
            @RequestParam String infoId) {
        PreCmdsVo preCmds = approvalService.findPreCmdByInfoId(infoId);
        return ResultUtils.success(preCmds);
    }

    /**
     * 根据条件查询舆情信息列表-分页（与审批人相关的）
     * @param ApprovalSearchVO
     * @param principal
     * @return
     */
    @PostMapping(path = "/approvals/search")
    public ResultEntity findByConditionPage(
            @NotNull(message = "ApprovalVo 不能为null")
            @RequestBody ApprovalSearchVo ApprovalSearchVO, OAuth2Authentication principal) {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("page", ApprovalSearchVO.getPage());
            params.add("size", ApprovalSearchVO.getSize());
            params.add("title", ApprovalSearchVO.getTitle());
            //00个人待审批 列表，01个人已审批列表
            params.add("tagFlag", ApprovalSearchVO.getTagFlag());

            RestPageImpl<ConsInfoSearchVo> pageList =  approvalService.findByConditionPage(params,principal);
            return ResultUtils.success(pageList);
    }

    /**
     * 通过舆情ID获取该条舆情审批列表信息
     * @param map 查询条件
     *            参数：
     *            infoId:舆情ID
     *            approvalNode:审批节点：监管中心/带班处长center_audit 、办领导leader_audit
     *            approvalResult:审批结果：0同意 1退回（查看审批意见时，前端不需传值；查看退回原因时，前端传入1
     * @return
     */
    @PostMapping(path = "/consApprovals")
    public ResultEntity findConsApprovals(
            @RequestParam
            @NotNull(message = "map不能为null") Map<String,Object> map) {
            List<ApprovalVo> approvalVos =  approvalService.findConsApprovals(map);
            return ResultUtils.success(approvalVos);
    }

}