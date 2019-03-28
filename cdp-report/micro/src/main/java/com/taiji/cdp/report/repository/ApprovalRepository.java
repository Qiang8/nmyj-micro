package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.entity.Approval;
import com.taiji.cdp.report.entity.QApproval;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-19 19:11
 **/
@Repository
public class ApprovalRepository extends BaseJpaRepository<Approval, String> {
    /**
     * 根据舆情Id查询舆情审批信息
     * @param infoId
     * @return
     */
    public List<Approval> findApprovalByInfoId(String infoId) {
        QApproval qApprovalEntity = QApproval.approval;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<Approval> query = from(qApprovalEntity);
        builder.and(qApprovalEntity.infoId.eq(infoId));
        query.select(Projections.bean(Approval.class,
                qApprovalEntity.id,
                qApprovalEntity.infoId,
                qApprovalEntity.submitter,
                qApprovalEntity.submitTime,
                qApprovalEntity.approvalerId,
                qApprovalEntity.approvaler,
                qApprovalEntity.approvalTime,
                qApprovalEntity.approvalResult,
                qApprovalEntity.approvalNode,
                qApprovalEntity.approvalOpinion))
                .where(builder).orderBy(qApprovalEntity.submitTime.desc());
        return findAll(query);
    }

    /**
     * 查询个人待审批和已审批列表
     * @param userId
     * @param tagFlag 00待审批  01已审批
     * @return
     */
    public List<Approval> findByApprovalerAndResult(String userId, String tagFlag) {
        QApproval qApproval = QApproval.approval;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<Approval> query = from(qApproval);
        builder.and(qApproval.approvalerId.eq(userId));
        if("00".equals(tagFlag)){
            builder.and(qApproval.approvalResult.isNull());
        }else{
            builder.and(qApproval.approvalResult.isNotNull());
        }
        query.select(Projections.bean(Approval.class,qApproval.infoId)).where(builder);
        return findAll(query);
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
        QApproval qApprovalEntity = QApproval.approval;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<Approval> query = from(qApprovalEntity);
        if(map.containsKey("approvalNode")) {
            if (!map.get("approvalNode").toString().isEmpty() && map.get("approvalNode").toString() != null) {
                builder.and(qApprovalEntity.approvalNode.eq(map.get("approvalNode").toString()));
            }
        }
        if(map.containsKey("infoId")) {
            if (!map.get("infoId").toString().isEmpty() && map.get("infoId").toString() != null) {
                builder.and(qApprovalEntity.infoId.eq(map.get("infoId").toString()));
            }
        }
        if(map.containsKey("approvalResult")){
            if("1".equals(map.get("approvalResult").toString())){
                builder.and(qApprovalEntity.approvalResult.eq("1"));
            }else{
                builder.and(qApprovalEntity.approvalResult.eq("0"));
            }
        }
        query.select(Projections.bean(Approval.class,
                qApprovalEntity.infoId,
                qApprovalEntity.id,
                qApprovalEntity.submitter,
                qApprovalEntity.submitTime,
                qApprovalEntity.approvalNode,
                qApprovalEntity.approvalerId,
                qApprovalEntity.approvaler,
                qApprovalEntity.approvalTime,
                qApprovalEntity.approvalResult,
                qApprovalEntity.approvalOpinion
                )).where(builder).orderBy(qApprovalEntity.submitTime.desc());
        return findAll(query);
    }
}