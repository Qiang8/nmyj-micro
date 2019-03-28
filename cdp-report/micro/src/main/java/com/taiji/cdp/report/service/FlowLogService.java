package com.taiji.cdp.report.service;

import com.taiji.cdp.report.common.enums.FlowLogNodeEnum;
import com.taiji.cdp.report.entity.FlowLog;
import com.taiji.cdp.report.repository.FlowLogRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class FlowLogService extends BaseService<FlowLog, String> {
    FlowLogRepository flowLogRepository;

    /**
     * 新增流程节点日志
     * @param flowLog
     */
    public void addConsFlows(FlowLog flowLog) {
        flowLogRepository.save(flowLog);
    }

    /**
     * 根据舆情Id和用户类型查询流程日志
     * @param consId 舆情id
     * @param userType 用户类型
     * @return
     */
    public List<FlowLog> findConsFlowsView(String consId, String userType) {
        List<FlowLog> flowLogEntities = flowLogRepository.findConsFlowsView(consId,userType);
        //将结果集的流程节点转换成汉字返回
        if(flowLogEntities!=null&& !CollectionUtils.isEmpty(flowLogEntities)){
            for(FlowLog flowLog :flowLogEntities){
                flowLog.setFlowNode(FlowLogNodeEnum.flowNodeOf(flowLog.getFlowNode()).getDesc());
            }
        }
        return flowLogEntities;
    }

    /**
     * 根据流程节点和舆情实体ID查询日志
     * @param flowNode
     * @param entityId
     * @return
     */
    public List<FlowLog> getFlowLog(String flowNode, String entityId) {
        Assert.hasText(flowNode, "流程节点不能为空字符串");
        Assert.hasText(entityId, "实体ID不能为空字符串");
        return flowLogRepository.getFlowLog(flowNode,entityId);
    }
}
