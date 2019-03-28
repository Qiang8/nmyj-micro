package com.taiji.cdp.report.feign;

import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 舆情信息接口服务类
 * @author qizhijie-pc
 * @date 2019年1月8日15:36:05
 */
@FeignClient(value = "micro-report-consInfo")
public interface IConsInfoRestService {

    /**
     * 新增舆情信息Vo
     * @param vo 舆情信息vo
     * @return ResponseEntity<ConsInfoVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<ConsInfoVo> create(@RequestBody ConsInfoVo vo);

    /**
     * 更新舆情信息Vo
     * @param vo 舆情信息vo
     * @param id 信息Id
     * @return ResponseEntity<ConsInfoVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<ConsInfoVo> update(@RequestBody ConsInfoVo vo, @PathVariable("id") String id);

    /**
     * 根据id获取单条舆情信息Vo
     * @param id 信息Id
     * @return ResponseEntity<ConsInfoVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<ConsInfoVo> findOne(@PathVariable("id") String id);

    /**
     * 根据id逻辑删除单条舆情信息Vo
     * @param id 信息Id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);

    /**
     * 舆情信息分页查询 -- 盟市使用
     * @param params
     * 参数;page,size
     * 参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)、createTimeStart(创建时间开始)
     *     、createTimeEnd(创建时间结束)、statuses(舆情信息状态,string[])、orgId(创建部门orgId)
     * @return ResponseEntity<RestPageImpl<ConsInfoVo>>
     *     不带取证信息
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<ConsInfoVo>> findPage(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 舆情信息分页查询 -- 自治区使用
     * @param params
     * 参数;page,size
     * 参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)、statuses(舆情信息状态,string[])
     * @return ResponseEntity<RestPageImpl<ConsInfoVo>>
     *     带取证信息
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/rec/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<ConsInfoVo>> findRecPage(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 舆情信息不分页查询
     * @param params
     * 参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)、statuses(舆情信息状态,string[])
     * @return ResponseEntity<List<ConsInfoVo>>
     *     不带取证信息
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/rec/list")
    @ResponseBody
    ResponseEntity<List<ConsInfoVo>> findRecList(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 根据舆情ID数组查询舆情信息列表-不分页（交接班使用）
     * @param infoIds 舆情ID数组
     * @return ResponseEntity<List<ConsInfoVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<ConsInfoVo>> findList(@RequestBody List<String> infoIds);

}
