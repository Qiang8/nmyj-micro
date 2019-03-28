package com.taiji.cdp.report.feign;

import com.taiji.cdp.report.vo.EvidenceVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 电子取证接口服务类
 *
 * @author xuweikai-pc
 * @date 2019年1月11日17:53:05
 */
@FeignClient(value = "micro-report-evidence")
public interface IEvidenceRestService {

    /**
     * 新增电子取证Vo
     *
     * @param vo 新增电子取证vo
     * @return ResponseEntity<EvidenceVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<EvidenceVo> create(@RequestBody EvidenceVo vo);

    /**
     * 根据id获取单条电子取证信息Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<EvidenceVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<EvidenceVo> findOne(@PathVariable("id") String id);

    /**
     * 根据舆情信息id获取单条电子取证信息Vo
     *
     * @param infoId 信息Id
     * @return ResponseEntity<EvidenceVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/consEvidences")
    @ResponseBody
    ResponseEntity<List<EvidenceVo>> findByJudgeId(@RequestParam("infoId") String infoId);
}
