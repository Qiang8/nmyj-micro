package com.taiji.cdp.report.feign;

import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.cdp.report.vo.TopicConsVo;
import com.taiji.cdp.report.vo.TopicVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 舆情信息专题关联接口服务类
 * @author qizhijie-pc
 * @date 2019年1月24日10:38:43
 */
@FeignClient(value = "micro-topic-consInfo")
public interface ITopicConsRestService {

    /**
     * 新增舆情、专题中间表记录
     * @param vo 中间表记录vo
     * @return ResponseEntity<TopicConsVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create/one")
    @ResponseBody
    ResponseEntity<TopicConsVo> createOne(@RequestBody TopicConsVo vo);

    /**
     * 批量新增舆情、专题中间表记录
     * @param vos 中间表记录vo数组
     * @return ResponseEntity<List<TopicConsVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create/list")
    @ResponseBody
    ResponseEntity<List<TopicConsVo>> createList(@RequestBody List<TopicConsVo> vos);

    /**
     * 根据重点专题ID查询舆情信息列表-分页（重点专题使用）
     * @param params
     * 参数：topicId,page,size
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page/topic")
    @ResponseBody
    ResponseEntity<RestPageImpl<ConsInfoVo>> findPageByTopic(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 根据舆情信息查询重点专题List-不分页
     * @param params
     * 参数：infoId
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list/info")
    @ResponseBody
    ResponseEntity<List<TopicVo>> findListByInfo(@RequestParam MultiValueMap<String,Object> params);

}
