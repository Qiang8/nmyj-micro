package com.taiji.cdp.daily.duty.controller;

import com.taiji.cdp.daily.duty.service.DutyService;
import com.taiji.cdp.daily.duty.vo.DutySearchVo;
import com.taiji.cdp.daily.duty.vo.LeaderUserVo;
import com.taiji.cdp.daily.issue.controller.BaseController;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.DateUtil;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.XuWeiKai
 * @create: 2019-02-14 11:00
 **/
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/dutys")
public class DutyController extends BaseController {

    public static Logger logger = LoggerFactory.getLogger(DutyController.class);

    DutyService service;

    /**
     * 获取当天值班信息
     */
    @GetMapping(path = "/today")
    public ResultEntity getTodyDuty() {
        List<Map<String, Object>> result = null;
        try {
            result = service.getTodyDuty();
        } catch (Exception e) {
            logger.error("获取当天值班信息异常！!", e);
        }
        return ResultUtils.success(result);
    }

    /**
     * 获取值班小组信息
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity getTodyDuty(@Validated
                                    @NotNull(message = "DutySearchVo 不能为null") @RequestBody DutySearchVo vo) {
        List<Map<String, Object>> result = null;
        int isBak = -1;
        Date startDate;
        Date endDate;
        String duty = vo.getDuty();
        if (null != vo.getStartDate()){
            startDate = DateUtil.createDate(vo.getStartDate());
            endDate = DateUtil.createDate(vo.getEndDate());
            isBak = Integer.parseInt(vo.getIsBak());
        }else {
            startDate = null;
            endDate = null;
        }
        try {
            result = service.getDutyTeam(isBak, duty, startDate, endDate);
        } catch (Exception e) {
            logger.error("获取当天值班信息异常！!", e);
        }
        return ResultUtils.success(result);
    }

    /**
     * 获取当天领导信息
     */
    @PostMapping(path = "/todayLeader")
    public ResultEntity getTodyLeader(@RequestBody Map<String, Object> map) {
        List<LeaderUserVo> result = null;
        try {
            int dutyCode = Integer.parseInt((String) map.get("dutyCode"));
            int dutyType = Integer.parseInt((String) map.get("dutyType"));
            result = service.getTodyLeader(dutyCode, dutyType);
        } catch (Exception e) {
            logger.error("获取当天领导信息异常！!", e);
        }
        return ResultUtils.success(result);
    }
}