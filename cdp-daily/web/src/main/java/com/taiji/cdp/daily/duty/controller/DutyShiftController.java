package com.taiji.cdp.daily.duty.controller;

import com.taiji.cdp.daily.duty.service.DutyShiftService;
import com.taiji.cdp.daily.duty.vo.DutyAddVo;
import com.taiji.cdp.daily.issue.controller.BaseController;
import com.taiji.cdp.daily.vo.DutyShiftVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.XuWeiKai
 * @create: 2019-01-07 16:00
 **/
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/dutyShifts")
public class DutyShiftController extends BaseController {

    public static Logger logger = LoggerFactory.getLogger(DutyShiftController.class);

    DutyShiftService service;

    /**
     * 新增交接班信息
     *
     * @param vo
     * @return
     */
    @PostMapping
    public ResultEntity addDutyShift(@RequestBody DutyAddVo vo, OAuth2Authentication principal) {
        try {
            service.addDuty(vo, principal);
        } catch (Exception e) {
            logger.error("新增交接班信息异常!", e);
            return ResultUtils.fail(ResultCodeEnum.OPERATE_FAIL, "新增交接班信息异常!");
        }
        return ResultUtils.success();
    }

    /**
     * 获取单条交接班信息
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findDutyShiftById(@NotEmpty(message = "id不能为空")
                                          @PathVariable(name = "id") String id) {
        DutyAddVo vo = null;
        try {
            vo = service.findById(id);
        } catch (Exception e) {
            logger.error("根据id获取交接班信息异常！", e);
            return ResultUtils.fail(ResultCodeEnum.INTERNAL_ERROR);
        }
        return ResultUtils.success(vo);
    }

    /**
     * 查询月报列表——分页
     * <p>
     * 查询参数 account,name
     *
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findUsers(@RequestBody Map<String, Object> paramsMap) {

        if (paramsMap.containsKey("page") && paramsMap.containsKey("size")) {
            RestPageImpl<DutyShiftVo> pageList = null;
            try {
                pageList = service.findByPages(paramsMap);
            } catch (Exception e) {
                logger.error("分页查询交接班信息异常！", e);
            }
            return ResultUtils.success(pageList);
        }
        return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
    }

    /**
     * 获取最新的交接班记录
     *
     * @return
     */
    @GetMapping(path = "/lately")
    public ResultEntity findDutyShiftById() {
        DutyAddVo vo = null;
        try {
            vo = service.findNewDuty();
        } catch (Exception e) {
            logger.error("获取最新的交接班记录信息异常！", e);
            return ResultUtils.fail(ResultCodeEnum.INTERNAL_ERROR, "获取最新的交接班记录信息异常");
        }
        return ResultUtils.success(vo);
    }

}