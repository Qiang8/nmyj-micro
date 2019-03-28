package com.taiji.cdp.report.feign;

import com.taiji.cdp.base.midAtt.feign.IMidAttRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-cdp-base",path = "api/midFiles")
public interface MidAttClient extends IMidAttRestService {
}
