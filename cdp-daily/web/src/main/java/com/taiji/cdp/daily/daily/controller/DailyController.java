package com.taiji.cdp.daily.daily.controller;

import com.taiji.cdp.daily.daily.service.DailyService;
import com.taiji.cdp.daily.daily.vo.DailySaveVo;
import com.taiji.cdp.daily.searchVo.DailyPageVo;
import com.taiji.cdp.daily.vo.DailyVo;
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
@RequestMapping("/dailys")
public class DailyController {

    DailyService service;

    /**
     * 新增日报管理
     * @param vo
     * @param principal 用户信息
     */
    @PostMapping
    public ResultEntity add(
            @Validated
            @NotNull(message = "DailySaveVo 不能为null")
            @RequestBody DailySaveVo vo, OAuth2Authentication principal)
            throws Exception{
            service.create(vo,principal);
            return ResultUtils.success();
    }

    /**
     * 修改日报管理
     * @param vo
     * @param id
     * @param principal 用户信息
     */
    @PutMapping(path = "/{id}")
    public ResultEntity update(
            @Validated
            @NotNull(message = "DailySaveVo 不能为null")
            @RequestBody DailySaveVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id
            ,OAuth2Authentication principal)
            throws Exception{
            service.update(vo,id,principal);
            return ResultUtils.success();
    }

    /**
     * 获取单条日报管理
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        DailyVo result = service.findOne(id);
        return ResultUtils.success(result);
    }

    /**
     * 删除日报管理信息
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity delete(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id)throws Exception {
        service.delete(id);
        return ResultUtils.success();
    }

    /**
     * 查询所以日报管理信息
     */
    @GetMapping(path = "/searchAll")
    public ResultEntity findList(){
        return ResultUtils.success(service.findList());
    }


    /**
     * 分页查询所以日报管理信息
     * @param vo
     */
    @PostMapping(path = "/search")
    public ResultEntity findPage(
            @NotNull(message = "dailyPageVo不能为空")
            @RequestBody DailyPageVo vo) throws Exception{
            return ResultUtils.success(service.findPage(vo));

    }
    /**
     * 日报管理信息发布
     * @param dailyId 日报id
     */
    @PutMapping(path = "/publish")
    public ResultEntity publish(
            @NotEmpty(message = "dailyId 不能为空字符串")
            @RequestParam("dailyId")
                    String dailyId){
        service.publish(dailyId);
        return ResultUtils.success();
    }

}
