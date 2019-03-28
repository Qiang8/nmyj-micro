package com.taiji.cdp.report.feign;

import com.taiji.cdp.report.vo.TopicVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专题管理接口服务类
 *
 * @author sunyi
 * @date 2019年1月17日
 */
@FeignClient(value = "micro-report-topic")
public interface ITopicService {

    /**
     * 新增专题管理
     *
     * @param vo 新增专题管理vo
     * @return ResponseEntity<TopicVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<TopicVo> create(@RequestBody TopicVo vo);


    /**
     * 更新专题管理Vo
     * @param vo 专题管理vo
     * @param id 专题管理Id
     * @return ResponseEntity<TopicVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<TopicVo> update(@RequestBody TopicVo vo, @PathVariable("id") String id);

    /**
     * 根据id获取单条专题管理信息Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<TopicVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<TopicVo> findOne(@PathVariable("id") String id);

    /**
     * 根据id逻辑删除单条专题管理信息Vo
     * @param id 信息Id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);

    /**
     * 获取所以专题管理信息
     * @return ResponseEntity<TopicVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/searchAll")
    @ResponseBody
    ResponseEntity<List<TopicVo>> findList();

}
