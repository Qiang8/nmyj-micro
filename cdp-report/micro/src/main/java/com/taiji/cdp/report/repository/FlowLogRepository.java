package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.common.enums.FlowLogNodeEnum;
import com.taiji.cdp.report.common.enums.FlowLogUserTypeEnum;
import com.taiji.cdp.report.entity.FlowLog;
import com.taiji.cdp.report.entity.QFlowLog;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlowLogRepository extends BaseJpaRepository<FlowLog, String> {
    /**
     * 根据舆情ID查询流程节点信息（自治区用户流程查看节点包括上报、研判、审批、处置、结束；
     * 盟市用户流程查看节点包括研判、审批、处置、结束）
     * @param consId 舆情ID
     * @param userType (用户类型)：自治区0、盟市1
     * @return 流程节点日志结果集
     */
    public List<FlowLog> findConsFlowsView(String consId, String userType) {
        QFlowLog qFlowLogEntity = QFlowLog.flowLog;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<FlowLog> query = from(qFlowLogEntity);
        builder.and(qFlowLogEntity.entityId.eq(consId));
        //自治区可查看的节点
        List<String> municipality = new ArrayList<>();
        municipality.add(FlowLogNodeEnum.REPORT.getFlowNode());
        municipality.add(FlowLogNodeEnum.JUDGE.getFlowNode());
        municipality.add(FlowLogNodeEnum.CENTER_AUDIT.getFlowNode());
        municipality.add(FlowLogNodeEnum.LEADER_AUDIT.getFlowNode());
        municipality.add(FlowLogNodeEnum.CMD.getFlowNode());
        municipality.add(FlowLogNodeEnum.CMD_END.getFlowNode());
        municipality.add(FlowLogNodeEnum.IGNORE.getFlowNode());
        //自治区可查看的节点
        if(FlowLogUserTypeEnum.ALLIANCE_CITY.getUserType().equals(userType)){
            //盟市比自治区少一个“上报”节点
            municipality.remove(FlowLogNodeEnum.REPORT.getFlowNode());
        }
        builder.and(qFlowLogEntity.flowNode.in(municipality));
        query.select(Projections.bean(FlowLog.class,
                qFlowLogEntity.entityId,
                qFlowLogEntity.dealUserName,
                qFlowLogEntity.dealTime,
                qFlowLogEntity.dealOrgName,
                qFlowLogEntity.flowNode,
                qFlowLogEntity.dealContent)).where(builder).orderBy(qFlowLogEntity.dealTime.asc());
        return findAll(query);
    }

    /**
     * 根据流程节点和舆情实体ID查询节点日志信息
     * @param flowNode 流程节点
     * @param entityId 舆情实体ID
     * @return 流程节点日志结果集
     */
    public List<FlowLog> getFlowLog(String flowNode, String entityId) {
        QFlowLog qFlowLogEntity = QFlowLog.flowLog;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<FlowLog> query = from(qFlowLogEntity);
        builder.and(qFlowLogEntity.flowNode.eq(flowNode));
        builder.and(qFlowLogEntity.entityId.eq(entityId));
        query.select(Projections.bean(FlowLog.class,
                qFlowLogEntity.id,
                qFlowLogEntity.entityId,
                qFlowLogEntity.dealUserName,
                qFlowLogEntity.dealTime,
                qFlowLogEntity.dealOrgName,
                qFlowLogEntity.flowNode,
                qFlowLogEntity.dealContent)).where(builder);
        return findAll(query);
    }
}
