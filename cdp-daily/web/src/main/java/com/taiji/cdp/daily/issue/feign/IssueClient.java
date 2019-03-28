package com.taiji.cdp.daily.issue.feign;

import com.taiji.cdp.daily.feign.IIssueRestService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 14:46
 **/
@FeignClient(value = "base-server-zuul/micro-cdp-daily", path ="api/issue")
public interface IssueClient extends IIssueRestService {

}
