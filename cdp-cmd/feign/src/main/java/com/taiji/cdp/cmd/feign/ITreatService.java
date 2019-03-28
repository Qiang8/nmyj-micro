package com.taiji.cdp.cmd.feign;

import com.taiji.cdp.cmd.vo.TreatVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: nmyj-micro
 * @Description: 处置办理服务类
 * @Author: TAIJI.WangJian
 * @Date: 2019/3/4 14:56
 **/
@FeignClient(value = "micro-cmd-treat")
public interface ITreatService {
    /**
     * 记录调控单办理情况
     * @param treatVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/addTreatment")
    @ResponseBody
    ResponseEntity<Void> addTreatment(@RequestBody TreatVo treatVo);

    /**
     * 更新调控单办理情况
     * @param treatVo
     * @param id 办理Id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/updateTreatment/{id}")
    @ResponseBody
    ResponseEntity<Void> updateTreatment(@RequestBody TreatVo treatVo,
                                         @PathVariable("id") String id);

    /**
     * 根据Id获取单条调控单办理信息
     * @param id
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findOneTreatment/{id}")
    @ResponseBody
    ResponseEntity<TreatVo> findOneTreatment(@PathVariable("id") String id);

    /**
     * 根据Id删除单条调控单办理信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteOneTreatment/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteOneTreatment(@PathVariable("id") String id);

    /**
     * 查询办理情况列表-不分页
     * @return 查询结果集
     */
    @RequestMapping(method = RequestMethod.GET, path = "/searchAllTreatment")
    @ResponseBody
    ResponseEntity<List<TreatVo>> searchAllTreatment();
}
