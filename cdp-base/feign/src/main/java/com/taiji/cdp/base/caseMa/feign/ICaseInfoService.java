package com.taiji.cdp.base.caseMa.feign;
import com.taiji.cdp.base.caseMa.vo.CaseVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "micro-cdp-base")
public interface ICaseInfoService {

    /**
     * 新增案例
     * @param vo
     * @return ResponseEntity<CaseVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/case")
    @ResponseBody
    ResponseEntity<CaseVo> create(@RequestBody CaseVo vo);

    /**
     * 根据ID查询案例信息
     * @param id
     * @return ResponseEntity<CaseVo>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/case/{id}")
    @ResponseBody
    ResponseEntity<CaseVo> finCaseById(@PathVariable("id") String id);

    /**
     * 根据ID删除案例信息
     * @param id
     * @return ResponseEntity<CaseVo>
     * */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/case/{id}")
    @ResponseBody
    ResponseEntity<CaseVo> deleteLogic(@PathVariable("id") String id);

    /**
     * 根据ID修改案例信息
     * @param id,vo
     * @return ResponseEntity<CaseVo>
     * */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/case/{id}")
    @ResponseBody
    ResponseEntity<CaseVo> update(@RequestBody CaseVo vo, @PathVariable("id") String id);

    /**
     * 案例信息分页查询
     * @param params
     * 参数;page,size
     * 参数：title(可选)、startTimeStart(首发时间开始)、startTimeEnd(上报时间结束)、caseTypeIds(案例类型,string[])
     * @return ResponseEntity<RestPageImpl<CaseVo>>
     *     不带取证信息
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<CaseVo>> findPage(@RequestParam MultiValueMap<String,Object> params);
}
