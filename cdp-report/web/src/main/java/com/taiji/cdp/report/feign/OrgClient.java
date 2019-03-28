package com.taiji.cdp.report.feign;

import com.taiji.base.sys.feign.IOrgRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <p>Title:OrgClient.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:39</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "base-server-zuul/micro-base-sys", path ="api/org")
public interface OrgClient extends IOrgRestService{

}
