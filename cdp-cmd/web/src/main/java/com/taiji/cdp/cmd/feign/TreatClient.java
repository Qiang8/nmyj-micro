package com.taiji.cdp.cmd.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: nmyj-micro
 * @Description: 处置办理feign
 * @Author: TAIJI.WangJian
 * @Date: 2019/3/4 14:54
 **/
@FeignClient(value = "base-server-zuul/micro-cdp-cmd", path ="api/treat")
public interface TreatClient extends ITreatService{

}
