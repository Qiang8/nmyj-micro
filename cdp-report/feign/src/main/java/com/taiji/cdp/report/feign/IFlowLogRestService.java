package com.taiji.cdp.report.feign;

import com.taiji.cdp.report.vo.FlowLogVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-report-flowLog")
public interface IFlowLogRestService {
    @RequestMapping(method = RequestMethod.POST, path = "/consFlows")
    @ResponseBody
    ResponseEntity<Void> addConsFlows(@RequestBody FlowLogVo flowLogVo);

    @RequestMapping(method = RequestMethod.GET, path = "/consFlows/view")
    @ResponseBody
    ResponseEntity<List<FlowLogVo>> findConsFlowsView(@RequestParam("consId")String consId, @RequestParam("userType")String userType);

}
