package com.taiji.cdp.base.sms.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value="base-server-zuul/micro-cdp-base",path = "api/smstemps")
public interface SmsTempClient extends ISmsTempRestService{
}
