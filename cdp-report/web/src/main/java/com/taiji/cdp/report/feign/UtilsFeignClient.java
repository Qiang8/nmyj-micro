package com.taiji.cdp.report.feign;

import com.taiji.micro.common.feign.IUtilsRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(
        value = "base-server-zuul/micro-base-sys",
        path = "api/utils"
)
public interface UtilsFeignClient extends IUtilsRestService {
}
