package com.taiji.cdp.daily.monthly.feign;

import com.taiji.cdp.base.midAtt.feign.IMidAttRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <p>Title:MidAttClient.java</p >
 * <p>Description: 附件表存储feign</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "base-server-zuul/micro-cdp-base", path ="api/midFiles")
public interface MidAttClient extends IMidAttRestService {
}
