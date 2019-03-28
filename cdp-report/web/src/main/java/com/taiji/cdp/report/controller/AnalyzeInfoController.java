package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.service.AnalyzeInfoService;
import com.taiji.cdp.report.vo.AnalyzeInfoDTO;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.bind.annotation.*;

/**
 * 舆情信息解析类 controller
 * @author qizhijie-pc
 * @2019年1月14日11:06:49
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/reportAnalyzes")
public class AnalyzeInfoController {

    AnalyzeInfoService service;

    /**
     * 通过链接URL解析获取舆情相应的标题和内容信息
     * @param infoUrl 舆情信息链接
     */
    @GetMapping
    public ResultEntity getAnalyzeInfo(
            @URL(message = "url 格式不合法")
            @NotEmpty(message = "infoUrl 不能为空字符串")
            @RequestParam("infoUrl")String infoUrl){
        AnalyzeInfoDTO result = service.getAnalyzeInfoByUrl(infoUrl);
        return ResultUtils.success(result);
    }

}
