package com.taiji.cdp.report.feign;

import com.taiji.base.sys.feign.IUserRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <p>Title:UserClient.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:40</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "base-server-zuul/micro-base-sys", path ="api/user")
public interface UserClient extends IUserRestService {
}
