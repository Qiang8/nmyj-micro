package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.service.TopicService;
import com.taiji.cdp.report.vo.TopicVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/topics")
public class TopicController {

    TopicService service;

    /**
     * 新增专题管理
     * @param vo
     * @param principal 用户信息
     */
    @PostMapping
    public ResultEntity addTopic(
            @Validated
            @NotNull(message = "TopicVo 不能为null")
            @RequestBody TopicVo vo, OAuth2Authentication principal){
        TopicVo result = service.create(vo,principal);
        return ResultUtils.success(result);
    }

    /**
     * 修改专题管理
     * @param vo
     * @param id
     * @param principal 用户信息
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateTopic(
            @Validated
            @NotNull(message = "TopicVo 不能为null")
            @RequestBody TopicVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id
            ,OAuth2Authentication principal){
        TopicVo result = service.update(vo,id,principal);
        return ResultUtils.success(result);
    }

    /**
     * 获取单条专题管理
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        TopicVo result = service.findOne(id);
        return ResultUtils.success(result);
    }

    /**
     * 删除专题管理信息
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
     * 查询所以专题管理信息
     */
    @GetMapping(path = "/searchAll")
    public ResultEntity findList(){
        return ResultUtils.success(service.findList());
    }


    /**
     * 根据专题关键字和舆情标题和内容进行匹配，查询匹配上的专题信息列表
     * @param infoId
     */
    @GetMapping(path = "/match")
    public ResultEntity findMatch(
            @NotEmpty(message = "infoId 不能为空字符串")
            String infoId){
        return ResultUtils.success(service.findMatchList(infoId));
    }
}
