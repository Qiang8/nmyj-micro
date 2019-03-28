package com.taiji.cdp.report.feign;

import com.taiji.cdp.report.vo.TopicKeywordVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专题管理关键字接口服务类
 *
 * @author sunyi
 * @date 2019年1月17日
 */
@FeignClient(value = "micro-report-topic-keywords")
public interface ITopicKeywordsService {

    /**
     * 新增专题关键字
     *
     * @param vo 新增专题关键字vo
     * @return ResponseEntity<TopicKeywordVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<TopicKeywordVo> create(@RequestBody TopicKeywordVo vo);


    /**
     * 更新专题管理关键字Vo
     * @param vo 专题管理关键字vo
     * @param id 专题管理关键字Id
     * @return ResponseEntity<TopicKeywordVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<TopicKeywordVo> update(@RequestBody TopicKeywordVo vo, @PathVariable("id") String id);

    /**
     * 根据id获取单条专题管理关键字信息Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<TopicKeywordVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<TopicKeywordVo> findOne(@PathVariable("id") String id);

    /**
     * 根据id逻辑删除单条专题管理关键字信息Vo
     * @param id 信息Id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);

    /**
     * 获取所以专题管理关键字信息Vo
     *
     * @param topicId 信息Id
     * @return ResponseEntity<TopicKeywordVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/searchAll")
    @ResponseBody
    ResponseEntity<List<TopicKeywordVo>> findList(@RequestParam("topicId")String topicId);

}
