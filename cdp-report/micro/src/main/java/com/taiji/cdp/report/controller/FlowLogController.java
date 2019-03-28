package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.entity.FlowLog;
import com.taiji.cdp.report.feign.IFlowLogRestService;
import com.taiji.cdp.report.mapper.FlowLogMapper;
import com.taiji.cdp.report.service.FlowLogService;
import com.taiji.cdp.report.vo.FlowLogVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/flowLog")
@AllArgsConstructor
public class FlowLogController implements IFlowLogRestService {

    FlowLogService flowLogService;
    FlowLogMapper flowLogMapper;
    @Override
    public ResponseEntity<Void> addConsFlows(
            @RequestBody
            @NotNull(message = "flowLogVo 不能为null")FlowLogVo flowLogVo) {
        List<FlowLog> flowLogEntities = flowLogService.getFlowLog(flowLogVo.getFlowNode(),flowLogVo.getEntityId());
        if(!CollectionUtils.isEmpty(flowLogEntities)&&flowLogEntities!=null){
            FlowLog flowLog = flowLogMapper.voToEntity(flowLogVo);
            flowLog.setId(flowLogEntities.get(0).getId());
            flowLogService.addConsFlows(flowLog);
        }else{
            FlowLog flowLog = flowLogMapper.voToEntity(flowLogVo);
            flowLogService.addConsFlows(flowLog);
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<FlowLogVo>> findConsFlowsView(
            @RequestParam("consId")
            @NotEmpty(message = "consId 不能为空字符串")String consId,
            @RequestParam("userType")
            @NotEmpty(message = "userType 不能为空字符串")String userType) {
        List<FlowLog> flowLogEntities = flowLogService.findConsFlowsView(consId,userType);
        List<FlowLogVo> flowLogVos = flowLogMapper.entityListToVoList(flowLogEntities);
        return ResponseEntity.ok(flowLogVos);
    }
}
