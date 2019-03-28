package com.taiji.cdp.daily.feign;

import com.taiji.cdp.daily.searchVo.MonthlyPageVo;
import com.taiji.cdp.daily.vo.MonthlyVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Title:IMonthlyRestService.java</p >
 * <p>Description: 月报管理feign接口</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-monthly")
public interface IMonthlyRestService {

    /**
     * 新增月报信息，MonthlyVo不能为空。
     *
     * @param vo 月报vo
     * @return ResponseEntity<MonthlyVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<MonthlyVo> create(@RequestBody MonthlyVo vo);

    /**
     * 根据参数获取分页MonthlyVo多条记录。
     * params参数key为account（可选），name（可选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<MonthlyVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<MonthlyVo>> findPage(@RequestBody MonthlyPageVo pageVo);

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return ResponseEntity<MonthlyVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<MonthlyVo> find(@PathVariable("id") String id);

    /**
     * 更新MonthlyVo，MonthlyVo不能为空。
     *
     * @param vo
     * @param id 更新MonthlyVo Id
     * @return ResponseEntity<MonthlyVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<MonthlyVo> update(@RequestBody MonthlyVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id 用户id
     * @return ResponseEntity<MonthlyVo>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<MonthlyVo> deleteLogic(@PathVariable("id") String id);

    /**
     * 根据id发布一条记录。
     *
     * @param id 用户id
     * @return ResponseEntity<MonthlyVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/publish")
    @ResponseBody
    ResponseEntity<MonthlyVo> publishInfo(@RequestParam("id") String id);
}
