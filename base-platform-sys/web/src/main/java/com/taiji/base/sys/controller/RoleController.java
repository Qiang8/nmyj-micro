package com.taiji.base.sys.controller;

import com.taiji.base.sys.service.RoleService;
import com.taiji.base.sys.vo.RoleVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:RoleController.java</p >
 * <p>Description: 角色管理控制类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController{

    RoleService roleService;

    /**
     * 新增角色
     * @param roleVo
     * @return
     */
    @PostMapping
    public ResultEntity addUser(
            @RequestBody
            @Validated
            @NotNull(message = "roleVo不能为null") RoleVo roleVo){
        roleService.create(roleVo);

        return ResultUtils.success();
    }

    /**
     * 获取单个角色信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findUserById(@NotEmpty(message = "id不能为空")
                                     @PathVariable(name = "id") String id){
        RoleVo userVo = roleService.findById(id);

        return ResultUtils.success(userVo);
    }

    /**
     * 修改角色信息
     * @param id
     * @param roleVo
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateUser(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id,
                                   @NotNull(message = "roleVo不能为null") @RequestBody RoleVo roleVo){
        roleService.update(roleVo,id);

        return ResultUtils.success();
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteUser(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id){
        roleService.delete(id);

        return ResultUtils.success();
    }

    /**
     * 查询角色列表——分页
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findUsers(@RequestBody Map<String, Object> paramsMap){

        if(paramsMap.containsKey("page") && paramsMap.containsKey("size")){
            RestPageImpl<RoleVo> pageList = roleService.findRoles(paramsMap);

            return ResultUtils.success(pageList);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 查询角色列表——不分页
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findUsersAll(@RequestBody Map<String, Object> paramsMap){

        List<RoleVo> allList = roleService.findRolesAll(paramsMap);

        return ResultUtils.success(allList);

    }

    /**
     * 检查roleCode唯一性
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/checkRoleCode")
    public ResultEntity checkRoleCode( @RequestBody Map<String, String> paramsMap){

        if(paramsMap.containsKey("roleCode")){
            Boolean result = roleService.checkRoleCode("roleCode");

            return ResultUtils.success(result);

        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }
}
