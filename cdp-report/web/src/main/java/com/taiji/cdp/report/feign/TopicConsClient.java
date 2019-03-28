package com.taiji.cdp.report.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-cdp-report",path = "api/topic/cons")
public interface TopicConsClient extends ITopicConsRestService{
}
