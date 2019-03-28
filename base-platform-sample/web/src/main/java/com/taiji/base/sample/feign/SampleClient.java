package com.taiji.base.sample.feign;

import com.taiji.config.FeignAuthConfig;
import org.springframework.cloud.netflix.feign.FeignClient;

//@FeignClient(value = "eureka-service-sample",path = "api/sample")
@FeignClient(value = "base-server-zuul/micro-base-sample", path ="api/sample",configuration = {FeignAuthConfig.class})
public interface SampleClient extends ISampleRestService {
}
