package com.taiji.cdp.cmd.feign;

import com.taiji.cdp.cmd.vo.ExeorgVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 舆情调控单与处置单位关联接口服务类
 *
 * @author xuweikai-pc
 * @date 2019年2月20日17:53:05
 */
@FeignClient(value = "micro-cmd-exeorg")
public interface IExeorgService {

    /**
     * 新增关联关系Vo
     *
     * @param vo ExeorgVo
     * @return ResponseEntity<ExeorgVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<ExeorgVo> create(@RequestBody ExeorgVo vo);

    /**
     * 新增关联关系List<Vo>
     *
     * @param vo ExeorgVo
     * @return ResponseEntity<ExeorgVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create/list")
    @ResponseBody
    ResponseEntity<List<ExeorgVo>> createList(@RequestBody List<ExeorgVo> vo);

    /**
     * 更新联关系Vo
     *
     * @param vo ExeorgVo
     * @return ResponseEntity<ExeorgVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<ExeorgVo> update(@RequestBody ExeorgVo vo, @PathVariable("id") String id);

    /**
     * 根据舆情调控单id获取关联关系
     *
     * @param cdId cdId
     * @return ResponseEntity<EvidenceVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findByCmdId")
    @ResponseBody
    ResponseEntity<List<ExeorgVo>> findByCmdId(@RequestParam("cdId") String cdId);

    /**
     * 根据组织机构id查询舆情调控单id
     *
     * @param orgId orgId
     * @return ResponseEntity<String>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findcmdIdByOrgId")
    @ResponseBody
    ResponseEntity<List<ExeorgVo>> findcmdIdByOrgId(@RequestParam("orgId") String orgId);


}
