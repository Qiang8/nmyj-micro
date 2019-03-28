package com.taiji.cdp.daily.feign;

import com.taiji.cdp.daily.searchVo.DailyPageVo;
import com.taiji.cdp.daily.vo.DailyVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日报管理接口服务类
 * @author sunyi
 * @date 2019年1月18日
 */
@FeignClient(value = "micro-report-daily")
public interface IDailyService {

    /**
     * 新增日报管理
     * @param vo 新增日报管理vo
     * @return ResponseEntity<DailyVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<DailyVo> create(@RequestBody DailyVo vo);


    /**
     * 更新日报管理Vo
     * @param vo 日报管理vo
     * @param id 日报管理Id
     * @return ResponseEntity<DailyVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<DailyVo> update(@RequestBody DailyVo vo, @PathVariable("id") String id);

    /**
     * 根据id获取单条日报管理信息Vo
     * @param id 信息Id
     * @return ResponseEntity<DailyVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<DailyVo> findOne(@PathVariable("id") String id);

    /**
     * 根据id逻辑删除单条日报管理信息Vo
     * @param id 信息Id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);

    /**
     * 根据参数获取 DailyVo 多条记录,分页信息
     *          page,size
     *  @return ResponseEntity<RestPageImpl<DailyVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<DailyVo>> findPage(@RequestBody DailyPageVo dailyPageVo);

    /**
     * 获取所以日报管理信息
     * @return ResponseEntity<DailyVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/searchAll")
    @ResponseBody
    ResponseEntity<List<DailyVo>> findList();

    /**
     * 获取所以日报管理信息
     * @return ResponseEntity<DailyVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/publish")
    @ResponseBody
    ResponseEntity<Void> publish(@RequestParam("dailyId") String dailyId);

}
