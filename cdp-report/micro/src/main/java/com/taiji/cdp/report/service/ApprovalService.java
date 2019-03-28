package com.taiji.cdp.report.service;

import com.taiji.cdp.report.common.enums.ApprovalNodeEnum;
import com.taiji.cdp.report.entity.Approval;
import com.taiji.cdp.report.entity.CommonAdvice;
import com.taiji.cdp.report.entity.ConsInfo;
import com.taiji.cdp.report.entity.ConsJudgeInfo;
import com.taiji.cdp.report.mapper.ApprovalMapper;
import com.taiji.cdp.report.mapper.ConsInfoMapper;
import com.taiji.cdp.report.mapper.EvidenceMapper;
import com.taiji.cdp.report.repository.*;
import com.taiji.cdp.report.vo.ApprovalVo;
import com.taiji.cdp.report.vo.ConsInfoSearchVo;
import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.cdp.report.vo.PreCmdsVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-19 19:07
 **/
@Service
@AllArgsConstructor
public class ApprovalService extends BaseService<Approval, String> {

    ApprovalRepository approvalRepository;
    CommonAdviceRepository commonAdviceRepository;
    ConsJudgeRepository consJudgeRepository;
    ConsInfoRepository consInfoRepository;
    EvdienceRepository evdienceRepository;

    /**
     * 新增舆情审批信息
     * @param approval
     * @return
     */
    public void addApproval(Approval approval) {
        Assert.notNull(approval,"approval不能为null");
        approvalRepository.save(approval);
    }

    /**
     * 根据舆情ID查询舆情审批记录
     * @param infoId
     * @return
     */
    public List<Approval> findApprovalByInfoId(String infoId) {
        Assert.hasText(infoId,"infoId不能为空字符串");
        return approvalRepository.findApprovalByInfoId(infoId);
    }

    /**
     * 根据舆情审批信息ID 更新舆情信息
     * @param approval
     * @return
     */
    public void updateApprovalClient(Approval approval) {
        Assert.notNull(approval,"approval不能为null");
        approvalRepository.save(approval);
    }

    /**
     * 新增常用审批意见
     * @param approvalVO
     */
    public void addCommonAdvice(ApprovalVo approvalVO) {
        Assert.notNull(approvalVO,"approval不能为null");
        String id = approvalVO.getId();
        String approvalNode = approvalVO.getApprovalNode();
        approvalVO.getApprovalAdviceIds().forEach(approvalAdviceId->{
            CommonAdvice commonAdvice = new CommonAdvice();
            commonAdvice.setApprovalId(id);
            commonAdvice.setCommonAdviceId(approvalAdviceId);
            commonAdvice.setApprovalNode(approvalNode);
            commonAdviceRepository.save(commonAdvice);
        });
    }

    /**
     * 获取单条舆情审批信息
     * @param id
     * @return
     */
    public Approval findApprovalById(String id) {
        Assert.hasText(id,"infoId不能为空字符串");
        return approvalRepository.findOne(id);
    }

    /**
     * 通过舆情ID获取调控单的各种初始化数据信息
     * @param infoId
     * @return
     */
    public PreCmdsVo findPreCmdByInfoId(String infoId) {
        Assert.hasText(infoId,"infoId不能为空字符串");
        PreCmdsVo preCmdsVO = new PreCmdsVo();
        List<Approval> approvalEntities = approvalRepository.findApprovalByInfoId(infoId);
        approvalEntities.forEach(approvalEntity->{
            //处领导审批意见
            if(ApprovalNodeEnum.CENTER_AUDIT.getStatus().equals(approvalEntity.getApprovalNode())){
                preCmdsVO.setCenterOpinion(approvalEntity.getApprovalOpinion());
                //办领导审批意见
            }else{
                preCmdsVO.setLeaderOpinion(approvalEntity.getApprovalOpinion());
            }
        });
        List<ConsJudgeInfo> consJudgeInfos = consJudgeRepository.findByJudgeId(infoId);
        consJudgeInfos.forEach(consJudgeInfo->{
            if(infoId.equals(consJudgeInfo.getInfoId())){
                preCmdsVO.setJudgeOpinion(consJudgeInfo.getOpinion());
            }
        });
        return preCmdsVO;
    }
    /**
     * 通过条件查询审批建议上日报的舆情信息列表信息
     * @param params
     * @return
     */
    public Page<ConsInfo> findDailies(MultiValueMap<String, Object> params, Pageable pageable) {
        List<CommonAdvice> commonAdviceEntities = commonAdviceRepository.findDaily();
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        commonAdviceEntities.forEach(commonAdvice ->{
            set1.add(commonAdvice.getApprovalId());
        });
        set1.forEach(approvalId->{
            Approval approval = approvalRepository.findOne(approvalId);
            set2.add(approval.getInfoId());
        });
        return consInfoRepository.findByInfoIdAndTime(set2,params,pageable);
    }

    /**
     * 查询个人待审批和已审批列表
     * @param userId
     * @param tagFlag
     * @return
     */

    public List<Approval> findByApprovalerAndResult(String userId, String tagFlag) {
        Assert.hasText(userId,"userName不能为空字符串");
        Assert.hasText(tagFlag,"tagFlag不能为空字符串");
        List<Approval> approvalEntities = approvalRepository.findByApprovalerAndResult(userId,tagFlag);
        return approvalEntities;
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
    public Page<ConsInfo> findByInfoIdAndNamePage(MultiValueMap<String, Object> params, Pageable pageable, Set<String> infoIds) {
        Page<ConsInfo> consInfos = consInfoRepository.findByInfoIdAndName(infoIds, params, pageable);
        return consInfos;
    }
    /**
     * 查询舆情信息 所有（孙毅添加）
     */
    public List<ConsInfo> findByInfoIdAndName(MultiValueMap<String, Object> params, Set<String> infoIds) {
        List<ConsInfo> consInfos = consInfoRepository.findByInfoIdAndName(infoIds, params);
        return consInfos;
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
    public List<Approval> findConsApprovals(Map<String, Object> map) {
        return approvalRepository.findConsApprovals(map);
    }

    /**
     * 根据舆情审批id获取舆情审批意见
     * @param approvalId
     * @return
     */
    public List<String> findCommonAdviceByApprovalId(String approvalId){
        Assert.hasText(approvalId,"approvalId不能为空字符串");
        List<CommonAdvice> commonAdviceEntities = commonAdviceRepository.findByApprovalId(approvalId);
        List<String> commonAdviceIds = new ArrayList<>();
        commonAdviceEntities.forEach(commonAdvice ->{
            commonAdviceIds.add(commonAdvice.getCommonAdviceId());
        });
        return commonAdviceIds;
    }

}