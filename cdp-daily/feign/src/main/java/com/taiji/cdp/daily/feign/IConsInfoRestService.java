package com.taiji.cdp.daily.feign;

import com.taiji.cdp.daily.vo.ConsInfoVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 舆情信息接口服务类
 * @author qizhijie-pc
 * @date 2019年1月8日15:36:05
 */
@FeignClient(value = "micro-daily-consInfo")
public interface IConsInfoRestService {

    /**
     * 根据舆情ID数组查询舆情信息列表-不分页（交接班使用）
     * @param infoIds 舆情ID数组
     * @return ResponseEntity<List<ConsInfoVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<ConsInfoVo>> findList(@RequestBody List<String> infoIds);

}
