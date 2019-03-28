package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.common.enums.ApprovalNodeEnum;
import com.taiji.cdp.report.entity.Approval;
import com.taiji.cdp.report.entity.ConsInfo;
import com.taiji.cdp.report.feign.IApprovalRestService;
import com.taiji.cdp.report.mapper.ApprovalMapper;
import com.taiji.cdp.report.mapper.ConsInfoMapper;
import com.taiji.cdp.report.mapper.EvidenceMapper;
import com.taiji.cdp.report.service.ApprovalService;
import com.taiji.cdp.report.service.EvidenceService;
import com.taiji.cdp.report.vo.ApprovalVo;
import com.taiji.cdp.report.vo.ConsInfoSearchVo;
import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.cdp.report.vo.PreCmdsVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-19 18:13
 **/
@Slf4j
@RestController
@RequestMapping("/api/approval")
@AllArgsConstructor
public class ApprovalController  implements IApprovalRestService {
    ApprovalService approvalService;
    ConsInfoMapper consInfoMapper;
    ApprovalMapper approvalMapper;
    EvidenceMapper evidenceMapper;
    EvidenceService evidenceService;

    /**
     * 新增舆情审批信息
     * @param approvalVO
     * @return
     */
    @Override
    public ResponseEntity<Void> addApproval(
            @RequestBody
            @NotNull(message = "approvalVO 不能为null") ApprovalVo approvalVO) {
        Approval approval = approvalMapper.voToEntity(approvalVO);
        approvalService.addApproval(approval);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据舆情ID查询舆情审批记录
     * @param infoId 舆情ID
     * @return
     */
    @Override
    public ResponseEntity<List<ApprovalVo>> findApprovalByInfoId(
            @PathVariable("infoId")
            @NotEmpty(message = "infoId不能为空字符串")String infoId) {
        List<Approval> approvals = approvalService.findApprovalByInfoId(infoId);
        List<ApprovalVo> approvalVos = approvalMapper.entityListToVoList(approvals);
        return ResponseEntity.ok(approvalVos);
    }

    /**
     * 根据舆情审批信息ID 更新舆情信息
     * @param approvalVO
     * @return
     */
    @Override
    public ResponseEntity<Void> updateApprovalClient(
            @RequestBody
            @NotNull(message = "approvalVO 不能为null") ApprovalVo approvalVO) {
        Approval approval = approvalMapper.voToEntity(approvalVO);
        approvalService.updateApprovalClient(approval);
        return ResponseEntity.ok().build();
    }
    /**
     * 新增常用审批意见
     * @param approvalVO
     */
    @Override
    public ResponseEntity<Void> addCommonAdvice(
            @RequestBody
            @NotNull(message = "approvalVO 不能为null")ApprovalVo approvalVO) {
        approvalService.addCommonAdvice(approvalVO);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取单条舆情信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ApprovalVo> findApprovalById(
            @PathVariable("id")
            @NotEmpty(message = "Id不能为空字符串")String id) {
        Approval approval = approvalService.findApprovalById(id);
        ApprovalVo approvalVo = approvalMapper.entityToVo(approval);
        List<String> commonAdviceByApprovalId = approvalService.findCommonAdviceByApprovalId(approval.getId());
        approvalVo.setApprovalAdviceIds(commonAdviceByApprovalId);
        return ResponseEntity.ok(approvalVo);
    }
    /**
     * 通过舆情ID获取调控单的各种初始化数据信息
     * @param infoId
     * @return
     */
    @Override
    public ResponseEntity<PreCmdsVo> findPreCmdByInfoId(
            @RequestParam("infoId")
            @NotEmpty(message = "infoId不能为空字符串")String infoId) {
        return ResponseEntity.ok(approvalService.findPreCmdByInfoId(infoId));
    }

    /**
     * 通过条件查询审批建议上日报的舆情信息列表信息
     * @param params
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<ConsInfoVo>> findDailies(
            @Validated
            @NotNull(message = "params不能为null")
            @RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);

        Page<ConsInfo> consInfos = approvalService.findDailies(params,pageable);
        RestPageImpl<ConsInfoVo> consInfoVos = consInfoMapper.entityPageToVoPage(consInfos, pageable);
        return ResponseEntity.ok(consInfoVos);
    }

    /**
     * 查询个人待审批和已审批列表
     * @param userName
     * @param tagFlag
     * @return
     */
    @Override
    public ResponseEntity<List<ApprovalVo>> findByApprovalerAndResult(
            @NotEmpty(message = "userName 不能为空字符串")
            @PathVariable("userName") String userName,
            @NotEmpty(message = "tagFlag 不能为空字符串")
            @PathVariable("tagFlag")String tagFlag) {
        List<Approval> approvals = approvalService.findByApprovalerAndResult(userName, tagFlag);
        List<ApprovalVo> approvalVos = approvalMapper.entityListToVoList(approvals);
        return ResponseEntity.ok(approvalVos);
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
    @Override
    public ResponseEntity<List<ApprovalVo>> findConsApprovals(
            @RequestParam Map<String, Object> map) {
        List<Approval> approvals = approvalService.findConsApprovals(map);
        List<ApprovalVo> approvalVos = approvalMapper.entityListToVoList(approvals);
        if(!CollectionUtils.isEmpty(approvalVos)&&null!=approvalVos){
            approvalVos.forEach(approvalVO->{
                List<String> commonAdviceByApprovalId = approvalService.findCommonAdviceByApprovalId(approvalVO.getId());
                approvalVO.setApprovalAdviceIds(commonAdviceByApprovalId);
            });
        }
        return  ResponseEntity.ok(approvalVos);
    }

    /**
     * 根据条件查询舆情信息列表-分页（与审批人相关的）
     *
     * @param params page 页码
     *               size 每页的长度
     *               title 舆情名称
     *               tagFlag 00个人待审批 列表，01个人已审批列表
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<ConsInfoSearchVo>> search(
            @RequestParam MultiValueMap<String, Object> params) {
        String tagFlag = params.getFirst("tagFlag").toString();
        String userId = params.getFirst("id").toString();
        List<Approval> approvals = approvalService.findByApprovalerAndResult(userId,tagFlag);
        List<ApprovalVo> approvalVos = approvalMapper.entityListToVoList(approvals);
        Set<String> infoIds = approvalVos.stream().map(ApprovalVo::getInfoId).collect(Collectors.toSet());
        List<ConsInfoSearchVo> vos = new ArrayList<>();
        Pageable pageable = PageUtils.getPageable(params);
        Page<ConsInfo> consInfos = approvalService.findByInfoIdAndNamePage(params, pageable, infoIds);
        RestPageImpl<ConsInfoVo> consInfoVos = consInfoMapper.entityPageToVoPage(consInfos, pageable);
        if(null!=consInfoVos){
            consInfoVos.getContent().forEach(consInfoVo->{
                consInfoVo.setConsEvidences(evidenceMapper.entityListToVoList(evidenceService.findByJudgeId(consInfoVo.getId())));
                ConsInfoSearchVo consInfoSearchVo = new ConsInfoSearchVo();
                consInfoSearchVo.setConsInfoVo(consInfoVo);
                List<Approval> approvalEntities = approvalService.findApprovalByInfoId(consInfoVo.getId());
                if(approvalEntities.size()==2){
                    consInfoSearchVo.setApprovalNode(ApprovalNodeEnum.LEADER_AUDIT.getStatus());
                }else{
                    consInfoSearchVo.setApprovalNode(ApprovalNodeEnum.CENTER_AUDIT.getStatus());
                }
                vos.add(consInfoSearchVo);
            });
        }else{
            List<ConsInfoSearchVo> list = new ArrayList<>();
            RestPageImpl<ConsInfoSearchVo> consInfoSearchVoes = new RestPageImpl(list,pageable,0);
            return ResponseEntity.ok(consInfoSearchVoes);
        }
        RestPageImpl<ConsInfoSearchVo> consInfoSearchVos = new RestPageImpl<>(vos,pageable,consInfoVos.getTotalElements());
        return ResponseEntity.ok(consInfoSearchVos);
    }

    /**
     * 查询舆情信息 所有（孙毅添加）
     */
    @Override
    public ResponseEntity<List<ConsInfoSearchVo>> searchAllStat(
            @RequestParam MultiValueMap<String, Object> params) {
        String userId = params.getFirst("id").toString();
        String tagFlag = params.getFirst("tagFlag").toString();
        List<Approval> approvals = approvalService.findByApprovalerAndResult(userId,tagFlag);
        List<ApprovalVo> approvalVos = approvalMapper.entityListToVoList(approvals);
        Set<String> infoIds = approvalVos.stream().map(ApprovalVo::getInfoId).collect(Collectors.toSet());
        List<ConsInfo> consInfos = approvalService.findByInfoIdAndName(params, infoIds);
        List<ConsInfoVo> consInfoVos = consInfoMapper.entityListToVoList(consInfos);
        List<ConsInfoSearchVo> vos = new ArrayList<>();

        if(!CollectionUtils.isEmpty(consInfoVos)&&null!=consInfoVos){
            consInfoVos.forEach(consInfoVo->{
                consInfoVo.setConsEvidences(evidenceMapper.entityListToVoList(evidenceService.findByJudgeId(consInfoVo.getId())));
                ConsInfoSearchVo consInfoSearchVo = new ConsInfoSearchVo();
                consInfoSearchVo.setConsInfoVo(consInfoVo);
                List<Approval> approvalEntities = approvalService.findApprovalByInfoId(consInfoVo.getId());
                if(approvalEntities.size()==2){
                    consInfoSearchVo.setApprovalNode(ApprovalNodeEnum.LEADER_AUDIT.getStatus());
                }else{
                    consInfoSearchVo.setApprovalNode(ApprovalNodeEnum.CENTER_AUDIT.getStatus());
                }
                vos.add(consInfoSearchVo);
            });
        }
        return ResponseEntity.ok(vos);
    }
}