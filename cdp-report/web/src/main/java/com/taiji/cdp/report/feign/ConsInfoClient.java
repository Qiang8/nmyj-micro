package com.taiji.cdp.report.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-cdp-report",path = "api/reports")
public interface ConsInfoClient extends IConsInfoRestService{
}
