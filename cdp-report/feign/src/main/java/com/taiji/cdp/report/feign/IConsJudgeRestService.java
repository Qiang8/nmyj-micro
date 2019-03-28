package com.taiji.cdp.report.feign;

import com.taiji.cdp.report.vo.ConsJudgeVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 舆情信息研判接口服务类
 *
 * @author xuweikai-pc
 * @date 2019年1月11日15:53:05
 */
@FeignClient(value = "micro-report-consJudge")
public interface IConsJudgeRestService {

    /**
     * 新增研判信息Vo
     *
     * @param vo 新增研判信息vo
     * @return ResponseEntity<ConsJudgeVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<ConsJudgeVo> create(@RequestBody ConsJudgeVo vo);

    /**
     * 更新研判信息Vo
     *
     * @param vo 研判信息vo
     * @param id 信息Id
     * @return ResponseEntity<ConsJudgeVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<ConsJudgeVo> update(@RequestBody ConsJudgeVo vo, @PathVariable("id") String id);

    /**
     * 根据id获取单条研判信息Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<ConsJudgeVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<ConsJudgeVo> findOne(@PathVariable("id") String id);

    /**
     * 根据舆情信息id获取单条研判信息Vo
     *
     * @param infoid 信息Id
     * @return ResponseEntity<ConsJudgeVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/consJudges")
    @ResponseBody
    ResponseEntity<List<ConsJudgeVo>> findByJudgeId(@RequestParam("infoid") String infoid);
}
