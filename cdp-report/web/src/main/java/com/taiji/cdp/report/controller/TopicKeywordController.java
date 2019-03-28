package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.service.TopicKeywordService;
import com.taiji.cdp.report.vo.TopicKeywordVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
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
@RequestMapping("/topicKeywords")
public class TopicKeywordController {

    TopicKeywordService service;

    /**
     * 新增专题管理关键字
     * @param vo
     */
    @PostMapping
    public ResultEntity addKeyword(
            @Validated
            @NotNull(message = "TopicKeywordVo 不能为null")
            @RequestBody TopicKeywordVo vo){
        TopicKeywordVo result = service.careat(vo);
        if(result==null){
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"keyword 关键字已存在");
        }
        return ResultUtils.success(result);
    }

    /**
     * 修改专题管理关键字
     * @param vo
     * @param id
     * @param principal 用户信息
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateKeyword(
            @Validated
            @NotNull(message = "TopicKeywordVo 不能为null")
            @RequestBody TopicKeywordVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        TopicKeywordVo result = service.update(vo,id);
        return ResultUtils.success(result);
    }

    /**
     * 获取单条专题管理关键字
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        TopicKeywordVo result = service.findOne(id);
        return ResultUtils.success(result);
    }

    /**
     * 删除专题管理关键字信息
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity delete(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        service.delete(id);
        return ResultUtils.success();
    }

    /**
     * 根据topicId查询所以专题管理关键字信息
     * @param topicId
     */
    @GetMapping(path = "/searchAll")
    public ResultEntity findList(
            @NotEmpty(message = "topicId 不能为空字符串")
                    @RequestParam("topicId") String topicId){
        return ResultUtils.success(service.findListByTopicId(topicId));
    }

}
