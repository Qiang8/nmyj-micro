package com.taiji.cdp.daily.issue.feign;

import com.taiji.base.file.feign.IAttachmentRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author: sunyi
 * @create: 2019-01-07 14:46
 **/
@FeignClient(value = "base-server-zuul/micro-file-simple", path ="api/file")
public interface AttSimpleClient extends IAttachmentRestService {

}
