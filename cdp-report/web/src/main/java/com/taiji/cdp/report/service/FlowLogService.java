package com.taiji.cdp.report.service;

import com.taiji.cdp.report.feign.FlowLogClient;
import com.taiji.cdp.report.vo.FlowLogVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-31 17:00
 **/
@Service
@AllArgsConstructor
public class FlowLogService extends BaseService {
    FlowLogClient flowLogClient;

    /**
     * 新增流程节点日志
     * @param flowLogVo
     */
    public void addConsFlows(FlowLogVo flowLogVo) {
        ResponseEntity<Void> result = flowLogClient.addConsFlows(flowLogVo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 根据舆情ID和用户类型查询流程节点信息
     * @param consId 舆情ID
     * @param userType 用户类型：自治区0、盟市1
     * @return
     */
    public List<FlowLogVo> findConsFlowsView(String consId, String userType) {
        Assert.hasText(consId, "consId 不能为空字符串");
        Assert.hasText(userType, "userType 不能为空字符串");
        ResponseEntity<List<FlowLogVo>> result = flowLogClient.findConsFlowsView(consId, userType);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }


}