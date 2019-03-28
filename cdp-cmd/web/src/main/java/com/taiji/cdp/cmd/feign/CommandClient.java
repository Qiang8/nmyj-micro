package com.taiji.cdp.cmd.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "base-server-zuul/micro-cdp-cmd",path = "api/command")
public interface CommandClient extends ICommandService{
}
