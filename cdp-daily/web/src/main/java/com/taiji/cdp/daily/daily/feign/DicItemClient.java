package com.taiji.cdp.daily.daily.feign;

import com.taiji.base.sys.feign.IDicGroupItemRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <p>Title:DicClient.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:36</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "base-server-zuul/micro-base-sys", path ="api/dic/groupItem")
public interface DicItemClient extends IDicGroupItemRestService {

}

