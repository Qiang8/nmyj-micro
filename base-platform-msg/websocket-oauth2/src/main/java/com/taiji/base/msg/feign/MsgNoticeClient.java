package com.taiji.base.msg.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <p>
 * <p>Title:MsgNoticeClient.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 14:52</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "base-server-zuul/micro-base-msg", path ="api/msg/notice")
public interface MsgNoticeClient extends IMsgNoticeRestService{
}
