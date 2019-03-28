package com.taiji.base.sys.feign;

import com.taiji.base.sys.vo.RoleVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IRoleRestSevice.java</p >
 * <p>Description: 角色feign接口</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:26</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-role")
public interface IRoleRestSevice {

    /**
     * 根据RoleVo id获取一条记录。
     *
     * @param id
     * @return ResponseEntity<RoleVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<RoleVo> find(@PathVariable("id") String id);

    /**
     * 根据参数获取RoleVo多条记录。
     *
     * params参数key为roleName（可选）。
     *
     * @param params
     * @return ResponseEntity<List<RoleVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<RoleVo>> findList(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 根据参数获取分页RoleVo多条记录。
     *
     * params参数key为roleName（可选）。
     *
     * @param params
     * @return ResponseEntity<RestPageImpl<RoleVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<RoleVo>> findPage(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 新增RoleVo，RoleVo不能为空。
     *
     * @param vo
     * @return ResponseEntity<RoleVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create/")
    @ResponseBody
    ResponseEntity<RoleVo> create(@RequestBody RoleVo vo);

    /**
     * 更新RoleVo，RoleVo不能为空。
     *
     * @param vo
     * @param id 更新RoleVo Id
     * @return ResponseEntity<RoleVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<RoleVo> update(@RequestBody RoleVo vo,@PathVariable(value = "id") String id);


    /**
     * 根据id删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);

    /**
     * 根据参数判断用户名是否被占用。
     *
     * @param id       （可选）
     * @param roleCode （必选）
     * @return ResponseEntity<Boolean>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/checkUnique")
    @ResponseBody
    ResponseEntity<Boolean> checkUnique(@RequestParam(name = "id") String id, @RequestParam(name = "roleCode") String roleCode);
}
