package com.taiji.cdp.report.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-19 17:38
 **/
@FeignClient(value = "base-server-zuul/micro-cdp-report", path = "api/approval")
public interface ApprovalClient extends IApprovalRestService{
}
