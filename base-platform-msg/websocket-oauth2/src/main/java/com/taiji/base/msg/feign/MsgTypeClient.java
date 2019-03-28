package com.taiji.base.msg.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <p>
 * <p>Title:MsgTypeClient.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 14:52</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "base-server-zuul/micro-base-msg", path ="api/msg/type")
public interface MsgTypeClient extends IMsgTypeRestService{
}
