package com.taiji.cdp.daily.daily.feign;

import com.taiji.cdp.daily.feign.IDailyService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-cdp-daily", path = "api/dailys")
public interface DailyClient extends IDailyService {
}
