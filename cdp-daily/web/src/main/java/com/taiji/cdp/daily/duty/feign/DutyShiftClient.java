package com.taiji.cdp.daily.duty.feign;

import com.taiji.cdp.daily.feign.IDutyShiftRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <p>Title:DutyShiftClient.java</p >
 * <p>Description: 交接班管理对外feign层 service</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "base-server-zuul/micro-cdp-daily", path = "api/duty")
public interface DutyShiftClient extends IDutyShiftRestService {
}
