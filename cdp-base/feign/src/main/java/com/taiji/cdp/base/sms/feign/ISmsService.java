package com.taiji.cdp.base.sms.feign;

import com.taiji.cdp.base.sms.searchVo.SmsListVo;
import com.taiji.cdp.base.sms.searchVo.SmsPageVo;
import com.taiji.cdp.base.sms.vo.SmsVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-base-sms")
public interface ISmsService  {
    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<SmsVo> create(@RequestBody SmsVo vo);

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<SmsVo> update(@RequestBody SmsVo vo, @PathVariable(value = "id") String id);

    /**
     * 获取单个
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<SmsVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 不分页查询
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<SmsVo>> findList(@RequestBody SmsListVo smsListVo);

    /**
     * 分页查询
     */
    @RequestMapping(method = RequestMethod.POST, path = "find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<SmsVo>> findPage(@RequestBody SmsPageVo smsPageVo);
}
