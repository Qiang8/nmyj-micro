package com.taiji.cdp.cmd.feign;

import com.taiji.cdp.cmd.vo.CommandVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * 舆情调控单接口服务类
 *
 * @author xuweikai-pc
 * @date 2019年2月20日17:53:05
 */
@FeignClient(value = "micro-cmd-command")
public interface ICommandService {

    /**
     * 新增舆情调控单Vo
     *
     * @param vo CommandVo
     * @return ResponseEntity<CommandVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<CommandVo> create(@RequestBody CommandVo vo);

    /**
     * 更新舆情调控单Vo
     *
     * @param vo 研判信息vo
     * @param id 信息Id
     * @return ResponseEntity<CommandVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<CommandVo> update(@RequestBody CommandVo vo, @PathVariable("id") String id);

    /**
     * 根据id获取单条舆情调控单Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<CommandVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<CommandVo> findOne(@PathVariable("id") String id);

    /**
     * 根据参数获取分页CommandVo多条记录。
     * params参数key为account（可选），name（可选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<CommandVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<CommandVo>> findPage(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 根据参数获取分页CommandVo多条记录。查询收到的信息
     * params参数key为account（可选），name（可选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<CommandVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/recivePage")
    @ResponseBody
    ResponseEntity<RestPageImpl<CommandVo>> findRecivePage(@RequestParam MultiValueMap<String, Object> params);


    /**
     * 根据id发布一条记录。
     *
     * @param id 舆情调控单id
     * @return ResponseEntity<CommandVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/updateStatus")
    @ResponseBody
    ResponseEntity<CommandVo> updateStatus(@RequestParam("cdId") String id, @RequestParam("status") String status);

}
