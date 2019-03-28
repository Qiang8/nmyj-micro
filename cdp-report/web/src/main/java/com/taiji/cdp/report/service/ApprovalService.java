package com.taiji.cdp.report.service;

import com.taiji.cdp.report.common.enums.ApprovalNodeEnum;
import com.taiji.cdp.report.common.enums.ApprovalResultEnum;
import com.taiji.cdp.report.feign.ApprovalClient;
import com.taiji.cdp.report.feign.FlowLogClient;
import com.taiji.cdp.report.feign.UtilsFeignClient;
import com.taiji.cdp.report.vo.*;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-18 16:45
 **/
@Service
@AllArgsConstructor
public class ApprovalService extends BaseService {

    ApprovalClient approvalClient;
    UtilsFeignClient utilsFeignClient;
    FlowLogClient flowLogClient;
    /**
     * 申请舆情信息
     * approvalVO.getApprovalNode()无论为CENTER_AUDIT或者LEADER_AUDIT,都需要去新增一条记录
     * 如果为CENTER_AUDIT，则为值班人员研判提交，本条舆情无舆情审批信息
     * 如果为LEADER_AUDIT，则为处领导审批提交，本条舆情有一条完整的舆情审批信息
     * @param approvalVO
     * @param principal
     */
    public void addApproval(ApprovalVo approvalVO, OAuth2Authentication principal) {
        LinkedHashMap<String, Object> userMap = getUserMap(principal);
        approvalVO.setSubmitter(userMap.get("username").toString());
        approvalVO.setSubmitTime(getCurrrentTime());
        approvalClient.addApproval(approvalVO);

        //保存流程节点日志信息
        FlowLogVo flowLogVo = new FlowLogVo();
        flowLogVo.setEntityId(approvalVO.getInfoId());
        flowLogVo.setFlowNode(approvalVO.getApprovalNode());
        flowLogVo.setDealTime(getCurrrentTime());
        flowLogVo.setDealUserName(userMap.get("username").toString());
        flowLogVo.setDealUserId(userMap.get("id").toString());
        flowLogVo.setDealOrgId(userMap.get("orgId").toString());
        flowLogVo.setDealOrgName(userMap.get("orgName").toString());
        if(null!=approvalVO.getApprovalResult()){
            flowLogVo.setDealContent("审批结果："+ ApprovalResultEnum.statusOf(approvalVO.getApprovalResult()).getDesc());
        }
        flowLogClient.addConsFlows(flowLogVo);
    }

    /**
     * 提交舆情信息
     *
     * @param approvalVO
     * @param principal
     */
    public void submitApplyApproval(ApprovalVo approvalVO, OAuth2Authentication principal, String id) {
        LinkedHashMap<String, Object> userMap = getUserMap(principal);

        ResponseEntity<ApprovalVo> result = approvalClient.findApprovalById(id);
        ApprovalVo approval = ResponseEntityUtils.achieveResponseEntityBody(result);
        //approval.setApprovaler(userMap.get("username").toString());
        approval.setApprovalTime(getCurrrentTime());
        approval.setApprovalOpinion(approvalVO.getApprovalOpinion());
        approval.setApprovalerId(userMap.get("id").toString());
        approval.setApprovalResult(approvalVO.getApprovalResult());

        //更新原来的记录
        approvalClient.updateApprovalClient(approval);
        //常用审批意见ID数组
        approvalVO.setId(id);
        approvalClient.addCommonAdvice(approvalVO);
        //保存流程节点日志信息
        FlowLogVo flowLogVo = new FlowLogVo();
        flowLogVo.setEntityId(approvalVO.getInfoId());
        flowLogVo.setDealTime(getCurrrentTime());
        flowLogVo.setDealOrgName(userMap.get("orgName").toString());
        flowLogVo.setDealUserId(userMap.get("id").toString());
        flowLogVo.setDealOrgId(userMap.get("orgId").toString());
        flowLogVo.setDealUserName(userMap.get("username").toString());
        flowLogVo.setFlowNode(approvalVO.getApprovalNode());
        if(null!=approvalVO.getApprovalResult()){
            flowLogVo.setDealContent("审批结果："+ApprovalResultEnum.statusOf(approvalVO.getApprovalResult()).getDesc());
        }
        flowLogClient.addConsFlows(flowLogVo);
    }

    /**
     * 获取单条舆情信息
     *
     * @param id
     * @return
     */
    public ApprovalVo findApprovalById(String id) {
        return ResponseEntityUtils.achieveResponseEntityBody(approvalClient.findApprovalById(id));
    }

    /**
     * 通过舆情ID获取调控单的各种初始化数据信息
     *
     * @param infoId
     * @return
     */
    public PreCmdsVo findPreCmdByInfoId(String infoId) {
        return ResponseEntityUtils.achieveResponseEntityBody(approvalClient.findPreCmdByInfoId(infoId));
    }

    /**
     * 通过条件查询审批建议上日报的舆情信息列表信息
     *
     * @param params
     * @return
     */
    public RestPageImpl<ConsInfoVo> findDailies(MultiValueMap<String, Object> params) {
        Assert.notNull(params, "params不能为null值");
        ResponseEntity<RestPageImpl<ConsInfoVo>> consDailys = approvalClient.findDailies(params);
        return ResponseEntityUtils.achieveResponseEntityBody(consDailys);
    }

    /**
     * 根据条件查询舆情信息列表-分页（与审批人相关的）
     *
     * @param params page 页码
     *               size 每页的长度
     *               title 舆情名称
     *               tagFlag 00个人待审批 列表，01个人已审批列表
     * @param principal
     * @return
     */
    public RestPageImpl<ConsInfoSearchVo> findByConditionPage(MultiValueMap<String, Object> params, OAuth2Authentication principal) {
        params.add("id",getUserMap(principal).get("id"));
        ResponseEntity<RestPageImpl<ConsInfoSearchVo>> vos = approvalClient.search(params);
        return ResponseEntityUtils.achieveResponseEntityBody(vos);
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
    public List<ApprovalVo> findConsApprovals(Map<String, Object> map) {
        return ResponseEntityUtils.achieveResponseEntityBody(approvalClient.findConsApprovals(map));
    }
}