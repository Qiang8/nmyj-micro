package com.taiji.cdp.base.caseMa.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-cdp-base",path = "api/cases")
public interface CaseClient extends ICaseInfoService {
}
