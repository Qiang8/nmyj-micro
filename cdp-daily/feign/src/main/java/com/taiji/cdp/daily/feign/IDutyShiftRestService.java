package com.taiji.cdp.daily.feign;

import com.taiji.cdp.daily.vo.DutyShiftItemVo;
import com.taiji.cdp.daily.vo.DutyShiftVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IDutyShiftRestService.java</p >
 * <p>Description: 交接班管理feign接口</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/21 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-report-duty")
public interface IDutyShiftRestService {

    /**
     * 1.新增交接班信息，DutyShiftVo。
     *
     * @param vo 交接班vo
     * @return ResponseEntity<DutyShiftVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/createDuty")
    @ResponseBody
    ResponseEntity<DutyShiftVo> createDuty(@RequestBody DutyShiftVo vo);

    /**
     * 2.新增事项信息，DutyShiftItemVo。
     *
     * @param vo 交接班事项vo
     * @return ResponseEntity<DutyShiftItemVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/createDutyItem")
    @ResponseBody
    ResponseEntity<DutyShiftItemVo> createDutyItem(@RequestBody DutyShiftItemVo vo);

    /**
     * 3.根据id获取一条交接班信息。
     *
     * @param id
     * @return ResponseEntity<DutyShiftVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findDuty/{id}")
    @ResponseBody
    ResponseEntity<DutyShiftVo> findDuty(@PathVariable("id") String id);

    /**
     * 4.根据 dutyId 获取一条交接班事项信息。
     *
     * @return ResponseEntity<DutyShiftItemVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findDutyItem")
    @ResponseBody
    ResponseEntity<List<DutyShiftItemVo>> findDutyItem(@RequestParam("id") String id);

    /**
     * 5.根据参数获取分页DutyShiftVo多条记录。
     * params参数key为toTeamId（可选），fromTeamId（可选），title（可选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<DutyShiftVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<DutyShiftVo>> findPage(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 6.获取最新一条交接班信息
     *
     * @return ResponseEntity<DutyShiftItemVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findNewDuty")
    @ResponseBody
    ResponseEntity<DutyShiftVo> findNewDuty();

}
