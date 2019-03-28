package com.taiji.cdp.daily.daily.feign;

import com.taiji.cdp.daily.feign.IConsInfoRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-cdp-daily",path = "api/reports")
public interface ConsInfoClient extends IConsInfoRestService {
}
