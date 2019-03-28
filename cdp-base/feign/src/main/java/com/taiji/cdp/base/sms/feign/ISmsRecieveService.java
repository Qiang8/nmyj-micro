package com.taiji.cdp.base.sms.feign;

import com.taiji.cdp.base.sms.vo.SmsRecieveVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-base-smsRecieve")
public interface ISmsRecieveService {
    /**
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<SmsRecieveVo> create(@RequestBody SmsRecieveVo vo);

    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<SmsRecieveVo> update(@RequestBody SmsRecieveVo vo, @PathVariable(value = "id") String id);

    /**
     * 获取单个
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<SmsRecieveVo> findOne(@PathVariable(value = "id") String id);

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
    ResponseEntity<List<SmsRecieveVo>> findList(@RequestBody SmsRecieveVo smsRecieveVo);

}
