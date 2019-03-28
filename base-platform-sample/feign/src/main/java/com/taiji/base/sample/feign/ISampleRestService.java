package com.taiji.base.sample.feign;

import com.taiji.base.sample.vo.SampleVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * sample feign接口
 *
 * <p>
 * <p>Title:ISampleRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年04月22</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "eureka-service-producer-sample")
public interface ISampleRestService {

    /**
     * 根据SampleVo id获取一条记录。
     *
     * @param id
     * @return ResponseEntity<SampleVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<SampleVo> find(@PathVariable("id") String id);

    /**
     * 根据参数获取分页SampleVo多条记录。
     *
     * params参数key为title（可选），content（可选）。
     *
     * @param params
     * @return ResponseEntity<RestPageImpl<SampleVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<SampleVo>> findPage(@RequestParam MultiValueMap<String, Object> params);


    /**
     * 保存SampleVo，SampleVo不能为空。
     *
     * @param vo
     * @return ResponseEntity<SampleVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/save")
    @ResponseBody
    ResponseEntity<SampleVo> save(@RequestBody SampleVo vo);


    /**
     * 根据id删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable("id") String id);
}
