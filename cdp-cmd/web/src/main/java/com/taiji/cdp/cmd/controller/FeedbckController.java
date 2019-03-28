package com.taiji.cdp.cmd.controller;

import com.taiji.cdp.cmd.service.FeedbackService;
import com.taiji.cdp.cmd.vo.FeedbackSaveVo;
import com.taiji.cdp.cmd.vo.FeedbackVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/feedbacks")
public class FeedbckController {

    FeedbackService service;

    /**
     * 新增处置反馈
     * @param vo
     * @param principal 用户信息
     */
    @PostMapping
    public ResultEntity addFeedback(
            @Validated
            @NotNull(message = "FeedbackVo 不能为null")
            @RequestBody FeedbackSaveVo vo, OAuth2Authentication principal){
        service.create(vo,principal);

        return ResultUtils.success();
    }


    /**
     * 获取单条处置反馈
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        FeedbackVo result = service.findOne(id);
        return ResultUtils.success(result);
    }


    /**
     * 查询所以处置反馈信息
     */
    @GetMapping(path = "/searchAll")
    public ResultEntity findList(){
        return ResultUtils.success(service.findList());
    }

}
