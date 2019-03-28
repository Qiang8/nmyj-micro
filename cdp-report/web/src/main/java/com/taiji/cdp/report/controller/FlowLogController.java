package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.service.FlowLogService;
import com.taiji.cdp.report.vo.FlowLogVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: nmyj-micro
 * @description: 舆情操作日志记录
 * @author: TaiJi.WangJian
 * @create: 2019-01-31 16:30
 **/
@Slf4j
@AllArgsConstructor
@RestController
public class FlowLogController {

    FlowLogService flowLogService;

    /**
     * 新增流程节点日志信息
     * @param flowLogVo
     * @return
     */
    @PostMapping(path = "/consFlows")
    public ResultEntity addConsFlows(
            @NotNull(message =  "flowLogVo 不能为null")
            @RequestBody FlowLogVo flowLogVo) {
        flowLogService.addConsFlows(flowLogVo);
        return ResultUtils.success();
    }

    /**
     * 根据舆情ID查询流程节点信息（自治区用户流程查看节点包括上报、研判、审批、处置、结束；
     * 盟市用户流程查看节点包括研判、审批、处置、结束）
     * @param consId 舆情Id
     * @param userType 用户类型：自治区0、盟市1
     * @return
     */
    @GetMapping(path = "/consFlows/view")
    public ResultEntity findConsFlowsView(
            @NotEmpty(message = "consId 不能为空字符串")
            @RequestParam("consId") String consId,
            @NotEmpty(message = "userType 不能为空字符串")
            @RequestParam("userType") String userType) {
        List<FlowLogVo> flowLogVos = flowLogService.findConsFlowsView(consId, userType);
        return ResultUtils.success(flowLogVos);
    }
}