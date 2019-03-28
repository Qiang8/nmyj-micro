package com.taiji.base.sys.feign;

import com.taiji.base.sys.vo.UserVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IUserRestService.java</p >
 * <p>Description: 用户管理feign接口</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:20</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-user")
public interface IUserRestService {

    /**
     * 根据UserVo id获取一条记录。
     *
     * @param id 用户id
     * @return ResponseEntity<UserVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<UserVo> find(@PathVariable("id") String id);

    /**
     * 根据参数获取UserVo一条记录。
     * <p>
     * params参数key为account。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<UserVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findOne")
    @ResponseBody
    ResponseEntity<UserVo> findOne(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 根据参数获取UserVo多条记录。
     * <p>
     * params参数key为account（可选），name（可选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<List<UserVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<UserVo>> findList(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 根据参数获取分页UserVo多条记录。
     * <p>
     * params参数key为account（可选），name（可选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<UserVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<UserVo>> findPage(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 新增UserVo，UserVo不能为空。
     *
     * @param vo 用户vo
     * @return ResponseEntity<UserVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<UserVo> create(@RequestBody UserVo vo);

    /**
     * 更新UserVo，UserVo不能为空。
     *
     * @param vo 用户vo
     * @param id 更新UserVo Id
     * @return ResponseEntity<UserVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<UserVo> update(@RequestBody UserVo vo, @PathVariable(value = "id") String id);


    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id 用户id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);

    /**
     * 根据参数判断用户名是否被占用。
     *
     * @param id      用户id（可选）
     * @param account 用户账号（必选）
     * @return ResponseEntity<Boolean>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/checkUnique")
    @ResponseBody
    ResponseEntity<Boolean> checkUnique(@RequestParam(name = "id") String id, @RequestParam(name = "account") String account);


    /**
     * 根据用户id重置用户密码。
     *
     * @param id 用户id
     */
    @RequestMapping(method = RequestMethod.POST, path = "/resetPassword/{id}")
    @ResponseBody
    ResponseEntity<Void> resetPassword(@PathVariable("id") String id);

    /**
     * 根据用户id,旧密码，新密码更新用户密码。
     *
     * @param id          用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @RequestMapping(method = RequestMethod.POST, path = "/updatePassword/{id}")
    @ResponseBody
    ResponseEntity<Void> updatePassword(
            @PathVariable("id") String id,
            @RequestParam(name = "oldPassword") String oldPassword,
            @RequestParam(name = "newPassword") String newPassword);

    /**
     * 根据orgId获取UserVo List对象(目前返回 id与account,profiles)
     *
     * @param orgId 用户所属单位id
     * @return ResponseEntity<List<UserVo>>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findListByOrgId/{orgId}")
    @ResponseBody
    ResponseEntity<List<UserVo>> findListByOrgId(@PathVariable("orgId") String orgId);

    /**
     * 根据orgIds获取UserVo List对象(目前返回 id与account,profiles)
     * @param orgIds 用户所属单位id串
     * @return ResponseEntity<List<UserVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findListByOrgIds")
    @ResponseBody
    ResponseEntity<List<UserVo>> findListByOrgIds(@RequestBody List<String> orgIds);
}
