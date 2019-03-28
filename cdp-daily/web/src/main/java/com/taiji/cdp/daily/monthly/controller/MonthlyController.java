package com.taiji.cdp.daily.monthly.controller;

import com.taiji.cdp.daily.issue.controller.BaseController;
import com.taiji.cdp.daily.monthly.service.MonthlyService;
import com.taiji.cdp.daily.monthly.vo.MonthlyInfoVo;
import com.taiji.cdp.daily.searchVo.MonthlyPageVo;
import com.taiji.cdp.daily.vo.MonthlyVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.XuWeiKai
 * @create: 2019-01-07 16:00
 **/
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/monthlys")
public class MonthlyController extends BaseController {

    MonthlyService service;

    /**
     * 新增月报信息
     *
     * @param vo
     * @return
     */
    @PostMapping
    public ResultEntity addMontherly(
            @RequestBody MonthlyInfoVo vo,
            OAuth2Authentication principal) throws Exception {
            service.create(vo,principal);
            return ResultUtils.success();
    }

    /**
     * 修改月报信息
     *
     * @param id
     * @param vo
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateMontherly(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            @NotNull(message = "UserVo不能为null")
            @RequestBody MonthlyInfoVo vo,OAuth2Authentication principal)
            throws Exception{
            service.update(vo, id,principal);
            return ResultUtils.success();
    }

    /**
     * 获取单个月报信息
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findMontherlyById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id) {
        MonthlyVo monthlyVo = service.findById(id);
        return ResultUtils.success(monthlyVo);
    }

    /**
     * 删除月报
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteMontherly(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id)
            throws Exception{
        service.delete(id);
        return ResultUtils.success();
    }

    /**
     * 查询月报列表——分页
     * @param pageVo
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findUsers(
            @NotEmpty(message = "pageVo不能为空")
            @RequestBody MonthlyPageVo pageVo)  throws Exception{
            RestPageImpl<MonthlyVo> pageList = service.findByPages(pageVo);
            return ResultUtils.success(pageList);
    }

    /**
     * 发布月报
     * @param id
     * @return
     */
    @PutMapping(path = "/publish")
    public ResultEntity publishMontherly(@NotEmpty(message = "id不能为空") @RequestParam(name = "id") String id) {
        service.publish(id);
        return ResultUtils.success();
    }


}