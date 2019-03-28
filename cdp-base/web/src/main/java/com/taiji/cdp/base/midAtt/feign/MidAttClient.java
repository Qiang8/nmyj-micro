package com.taiji.cdp.base.midAtt.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-cdp-base",path = "api/midFiles")
public interface MidAttClient extends IMidAttRestService{
}
