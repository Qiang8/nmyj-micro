package com.taiji.cdp.cmd.controller;

import com.taiji.cdp.cmd.service.TreatService;
import com.taiji.cdp.cmd.vo.FeedbackSaveVo;
import com.taiji.cdp.cmd.vo.FeedbackVo;
import com.taiji.cdp.cmd.vo.TreatVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: nmyj-micro
 * @description: 处置办理控制类
 * @author: TaiJi.WangJian
 * @create: 2019-03-04 14:28
 **/
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/treatments")
public class TreatController {
    TreatService treatService;

    /**
     * 记录调控单办理情况
     * @param treatVo 调控单办理情况Vo
     * @return
     */
    @PostMapping
    public ResultEntity addTreatment(
            @NotNull(message = "treatVo 不能为null")
            @RequestBody TreatVo treatVo,OAuth2Authentication principal){
        treatService.addTreatment(treatVo,principal);
        return ResultUtils.success();
    }

    /**
     * 更新调控单办理情况
     * @param treatVo 调控单办理情况Vo
     * @param id 办理情况Id
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateTreatment(
            @NotNull(message = "treatVo 不能为null")
            @RequestBody TreatVo treatVo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id,OAuth2Authentication principal){
        treatService.updateTreatment(treatVo,id,principal);
        return ResultUtils.success();
    }

    /**
     * 根据Id获取单条调控单办理信息
     * @param id 操作Id
     * @return 操作Vo
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOneTreatment(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        TreatVo treatVo = treatService.findOneTreatment(id);
        return ResultUtils.success(treatVo);
    }

    /**
     * 根据Id删除单条调控单办理信息
     * @param id 操作Id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteOneTreatment(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        treatService.deleteOneTreatment(id);
        return ResultUtils.success();
    }

    /**
     * 查询办理情况列表-不分页
     * @return 办理情况结果集
     */
    @GetMapping(path = "/searchAll")
    public ResultEntity searchAllTreatment(){
        List<TreatVo> treatVos = treatService.searchAllTreatment();
        return ResultUtils.success(treatVos);
    }

}
