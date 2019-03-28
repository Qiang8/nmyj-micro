package com.taiji.base.sys.feign;

import com.taiji.base.sys.vo.OrgVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IOrgRestService.java</p >
 * <p>Description: 组织机构feign接口</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:25</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-org")
public interface IOrgRestService {
    /**
     * 根据参数获取OrgVo多条记录。
     *
     * params参数key为parentId（可选），orgName（可选）。
     *
     * @return ResponseEntity<OrgVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<OrgVo>> findList(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 根据OrgVoid获取一条记录。
     *
     * @param id
     * @return ResponseEntity<OrgVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<OrgVo> find(@PathVariable("id") String id);

    /**
     * 新增OrgVo，OrgVo不能为空。
     *
     * @param vo
     * @return ResponseEntity<OrgVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<OrgVo> create(@RequestBody OrgVo vo);

    /**
     * 更新OrgVo，OrgVo不能为空。
     *
     * @param vo
     * @param id 更新OrgVo Id
     * @return ResponseEntity<OrgVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<OrgVo> update(@RequestBody OrgVo vo, @PathVariable(value = "id") String id);


    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.POST, path = "/find/orgInfo")
    @ResponseBody
    ResponseEntity<List<OrgVo>> findOrgInfo(@RequestBody List<String> orgIds);
}
