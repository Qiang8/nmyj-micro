package com.taiji.cdp.report.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * 流程日志增查
 * @author: WangJian
 * @create: 2019-02-27 00:50
 */
@FeignClient(value = "base-server-zuul/micro-cdp-report", path = "api/flowLog")
public interface FlowLogClient extends IFlowLogRestService{
}
