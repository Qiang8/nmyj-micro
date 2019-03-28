package com.taiji.base.sys.controller;

import com.taiji.base.sys.entity.User;
import com.taiji.base.sys.feign.IUserRestService;
import com.taiji.base.sys.mapper.UserMapper;
import com.taiji.base.sys.service.UserService;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.exception.ResultException;
import com.taiji.micro.common.utils.PasswordUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author scl
 * @date 2018-02-07
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController implements IUserRestService {

    BCryptPasswordEncoder passwordEncoder;

    UserService service;

    UserMapper mapper;

    /**
     * 根据UserVo id获取一条记录。
     *
     * @param id    用户id
     * @return ResponseEntity<UserVo>
     */
    @Override
    public ResponseEntity<UserVo> find(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id) {

        User   entity = service.findOne(id);
        UserVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据参数获取UserVo一条记录。
     * <p>
     * params参数key为account。
     *
     * @param params    查询参数集合
     * @return ResponseEntity<UserVo>
     */
    @Override
    public ResponseEntity<UserVo> findOne(@RequestParam MultiValueMap<String, Object> params) {
        User   entity = service.findOne(params);
        UserVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据参数获取UserVo多条记录。
     * <p>
     * params参数key为account（可选），name（可选）。
     *
     * @param params    查询参数集合
     * @return ResponseEntity<List<UserVo>>
     */
    @Override
    public ResponseEntity<List<UserVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        String account = "";
        String name    = "";

        if (params.containsKey("account")) {
            account = params.getFirst("account").toString();
        }

        if (params.containsKey("name")) {
            name = params.getFirst("name").toString();
        }
        List<User>   result = service.findAll(account, name);
        List<UserVo> voList = mapper.entityListToVoList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取分页UserVo多条记录。
     * <p>
     * params参数key为account（可选），name（可选）。
     *
     * @param params    查询参数集合
     * @return ResponseEntity<RestPageImpl       <       UserVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<UserVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);

        String account = "";
        String name    = "";

        if (params.containsKey("account")) {
            account = params.getFirst("account").toString();
        }

        if (params.containsKey("name")) {
            name = params.getFirst("name").toString();
        }

        Page<User>           result = service.findPage(account, name, pageable);
        RestPageImpl<UserVo> voPage = mapper.entityPageToVoPage(result, pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 新增UserVo，UserVo不能为空。
     *
     * @param vo    用户vo
     * @return ResponseEntity<UserVo>
     */
    @Override
    public ResponseEntity<UserVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody UserVo vo) {

        User   tempEntity = mapper.voToEntity(vo);
        User   entity     = service.create(tempEntity);
        UserVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 更新UserVo，UserVo不能为空。
     *
     * @param vo    用户vo
     * @param id 更新UserVo Id
     * @return ResponseEntity<UserVo>
     */
    @Override
    public ResponseEntity<UserVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody UserVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {

        User   tempEntity = mapper.voToEntity(vo);
        User   entity     = service.update(tempEntity, id);
        UserVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id    用户id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);

        return ResponseEntity.ok().build();
    }

    /**
     * 根据参数判断用户名是否被占用。
     *
     * @param id      用户id（可选）
     * @param account 用户账号（必选）
     * @return ResponseEntity<Boolean>
     */
    @Override
    public ResponseEntity<Boolean> checkUnique(
            @RequestParam(name = "id")
                    String id,
            @RequestParam(name = "account")
            @NotEmpty(message = "account不能为空")
                    String account) {
        Boolean result = service.checkUnique(id, account);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据用户id重置用户密码。
     *
     * @param id    用户id
     */
    @Override
    public ResponseEntity<Void> resetPassword(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id) {
        User user = service.findOne(id);
        if (null != user) {
            String defaultPassword = "123456";
            String md5Password = PasswordUtils.encodePasswordMd5(defaultPassword);
            String encodePassword = passwordEncoder.encode(md5Password);
            user.setPassword(encodePassword);
            service.update(user, id);
        } else {
            throw new RuntimeException("查找的记录不存在！");
        }

        return ResponseEntity.ok().build();
    }

    /**
     * 根据用户id,旧密码，新密码更新用户密码。
     *
     * @param id             用户id
     * @param oldPassword   旧密码
     * @param newPassword   新密码
     */
    @Override
    public ResponseEntity<Void> updatePassword(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id,
            @NotEmpty(message = "oldPassword不能为空")
            @RequestParam(name = "oldPassword")
                    String oldPassword,
            @NotEmpty(message = "newPassword不能为空")
            @RequestParam(name = "newPassword")
                    String newPassword) {

        User user = service.findOne(id);
        if (null != user) {
            String md5Str = PasswordUtils.decodeBase64Md5SaltPassword(oldPassword,"yjgl");

            if (passwordEncoder.matches(md5Str,user.getPassword())) {
                String md5NewStr = PasswordUtils.encodePasswordMd5(newPassword);
                String encodeNewPassword = passwordEncoder.encode(md5NewStr);
                user.setPassword(encodeNewPassword);
                service.update(user, id);
            } else {
                throw new ResultException(ResultCodeEnum.PASSWORD_ERROR);
            }
        } else {
            throw new RuntimeException("查找的记录不存在！");
        }


        return ResponseEntity.ok().build();
    }

    /**
     * 根据orgId获取UserVo对象(目前返回 id与account,profiles)
     *
     * @param orgId 用户所属单位id
     * @return ResponseEntity<List < UserVo>>
     */
    @Override
    public ResponseEntity<List<UserVo>> findListByOrgId(
            @NotEmpty(message = "orgId不能为空")
            @PathVariable("orgId") String orgId) {
        List<User> list = service.findListByOrgId(orgId);
        List<UserVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据orgIds获取UserVo List对象(目前返回 id与account,profiles)
     *
     * @param orgIds 用户所属单位id串
     * @return ResponseEntity<List < UserVo>>
     */
    @Override
    public ResponseEntity<List<UserVo>> findListByOrgIds(
            @NotNull(message = "orgIds 不能为null")
            @RequestBody List<String> orgIds) {
        List<User> list = service.findListByOrgIds(orgIds);
        List<UserVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
