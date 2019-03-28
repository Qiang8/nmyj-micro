package com.taiji.cdp.cmd.feign;

import com.taiji.cdp.cmd.vo.FeedbackVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处置反馈接口服务类
 * @author sunyi
 * @date 2019年2月20日
 */
@FeignClient(value = "micro-cmd-feedback")
public interface IFeedbackService {

    /**
     * 新增处置反馈
     *
     * @param vo 新增处置反馈vo
     * @return ResponseEntity<FeedbackVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<FeedbackVo> create(@RequestBody FeedbackVo vo);

    /**
     * 根据id获取单条处置反馈信息Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<FeedbackVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<FeedbackVo> findOne(@PathVariable("id") String id);


    /**
     * 获取所以处置反馈信息
     * @return ResponseEntity<FeedbackVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/searchAll")
    @ResponseBody
    ResponseEntity<List<FeedbackVo>> findList();

}
