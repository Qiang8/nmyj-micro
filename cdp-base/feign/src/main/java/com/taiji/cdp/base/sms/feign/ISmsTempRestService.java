package com.taiji.cdp.base.sms.feign;

import com.taiji.cdp.base.sms.vo.SmsTempVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-base-smsTemp")
public interface ISmsTempRestService {

    /**
     * 新增短信模板vo
     * @param vo 短信模板vo
     * @return ResponseEntity<SmsTempVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<SmsTempVo> create(@RequestBody SmsTempVo vo);

    /**
     * 修改短信模板vo
     * @param vo 短信模板vo
     * @param id 短信模板vo id
     * @return ResponseEntity<SmsTempVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<SmsTempVo> update(@RequestBody SmsTempVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id获取单个短信模板vo
     * @param id 短信模板vo id
     * @return ResponseEntity<SmsTempVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<SmsTempVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 逻辑删除短信模板vo
     * @param id 短信模板vo id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询短信模板列表  --不分页查询
     * @param params
     * 参数：name,content
     * @return ResponseEntity<List<SmsTempVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<SmsTempVo>> findList(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 根据条件查询短信模板列表 --分页查询
     * @param params
     * 参数：page,size,name,content
     * @return ResponseEntity<RestPageImpl<SmsTempVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<SmsTempVo>> findPage(@RequestParam MultiValueMap<String,Object> params);

}
