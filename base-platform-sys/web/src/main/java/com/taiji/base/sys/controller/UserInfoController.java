package com.taiji.base.sys.controller;


import com.taiji.base.sys.service.RoleService;
import com.taiji.base.sys.service.UserService;
import com.taiji.base.sys.vo.RoleMapDto;
import com.taiji.base.sys.vo.RoleVo;
import com.taiji.base.sys.vo.UserRoleDto;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:UserInfoController.java</p >
 * <p>Description: 用户服务控制类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {

    UserService userService;

    RoleService roleService;

    /**
     * 获取当前用户信息
     *
     * @param principal
     *
     * @return
     */
    @GetMapping(path = "/current")
    public ResultEntity findCurrentUser(Principal principal) {
        String              account = principal.getName();
        Map<String, Object> params  = new HashMap<>();
        Assert.hasText(account, "account不能为空");
        params.put("account", account);
        UserVo u = userService.findOneUser(params);

        List<RoleVo> roleList = u.getRoleList();
        if(!CollectionUtils.isEmpty(roleList)){
            u.setHomePage(roleList.get(0).getHomePage());
        }

        return ResultUtils.success(u);
    }

    /**
     * 获取用户赋角色信息
     *
     * @param userId
     *
     * @return
     */
    @GetMapping(path = "/roles")
    public ResultEntity findUserRoles(@RequestParam(name = "userId") String userId) {

        UserRoleDto urDto = new UserRoleDto();
        UserVo      u     = userService.findById(userId);
        if (null != u) {
            if (null != u.getRoleList()) {
                for (RoleVo r : u.getRoleList()) {
                    urDto.getBelong().add(r.getId());
                }
            }

            Map<String, Object> params = new HashMap<>();
            List<RoleVo>        list   = roleService.findRolesAll(params);
            if (null != list) {
                for (RoleVo r : list) {
                    RoleMapDto rmDto = new RoleMapDto();
                    rmDto.setKey(r.getId());
                    rmDto.setLabel(r.getRoleName());
                    urDto.getAll().add(rmDto);
                }
            }
            return ResultUtils.success(urDto);
        } else {

            return ResultUtils.fail(ResultCodeEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 保存用户角色信息
     *
     * @param paramsMap
     *
     * @return
     */
    @PostMapping(path = "/roles")
    public ResultEntity saveUserRoles(@RequestBody Map<String, Object> paramsMap) {

        if (paramsMap.containsKey("userId") &&
                paramsMap.containsKey("roleIds")) {
            String       userId  = (String) paramsMap.get("userId");
            List<String> roleIds = (List<String>) paramsMap.get("roleIds");
            UserVo       u       = userService.findById(userId);
            List<RoleVo> roleVos = new ArrayList<>();
            for (String roleId : roleIds) {
                RoleVo r = roleService.findById(roleId);
                roleVos.add(r);
            }
            u.setRoleList(roleVos);

            userService.update(u, userId);

            return ResultUtils.success();
        } else {
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 用户密码重置
     *
     * @param paramsMap
     *
     * @return
     */
    @PostMapping(path = "/resetPassword")
    public ResultEntity resetPassword(@RequestBody Map<String, Object> paramsMap) {

        if (paramsMap.containsKey("userId")) {

            String userId = (String) paramsMap.get("userId");

            userService.resetPassword(userId);

            return ResultUtils.success();
        } else {
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 用户密码修改
     *
     * @param paramsMap
     *
     * @return
     */
    @PostMapping(path = "/editPassword")
    public ResultEntity editPassword(@RequestBody Map<String, String> paramsMap) {

        if (paramsMap.containsKey("userId")
                && paramsMap.containsKey("oldPassword")
                && paramsMap.containsKey("newPassword")) {

            String userId      = (String) paramsMap.get("userId");
            String oldPassword = (String) paramsMap.get("oldPassword");
            String newPassword = (String) paramsMap.get("newPassword");

            userService.changePassword(userId, oldPassword, newPassword);

            return ResultUtils.success();
        } else {
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }
}
