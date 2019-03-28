package com.taiji.base.sys.controller;


import com.taiji.base.sys.service.UserService;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.PasswordUtils;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:UserController.java</p >
 * <p>Description: 用户管理控制类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController extends BaseController{

    BCryptPasswordEncoder passwordEncoder;

    UserService userService;

    /**
     * 新增用户
     * @param userVo
     * @return
     */
    @PostMapping
    public ResultEntity addUser( @RequestBody UserVo userVo){
        String defaultPassword = "123456";
        String md5Password = PasswordUtils.encodePasswordMd5(defaultPassword);
        String encodePassword = passwordEncoder.encode(md5Password);
        userVo.setPassword(encodePassword);

        userService.create(userVo);

        return ResultUtils.success();
    }

    /**
     * 获取单个用户信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findUserById(@NotEmpty(message = "id不能为空")
                                     @PathVariable(name = "id") String id){
        UserVo userVo = userService.findById(id);

        return ResultUtils.success(userVo);
    }

    /**
     * 修改用户信息
     * @param id
     * @param userVo
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateUser(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id,
                                   @NotNull(message = "UserVo不能为null") @RequestBody UserVo userVo){
        userService.update(userVo,id);

        return ResultUtils.success();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteUser(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id){
        userService.delete(id);

        return ResultUtils.success();
    }

    /**
     * 查询用户列表——分页
     *
     * 查询参数 account,name
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findUsers(@RequestBody Map<String, Object> paramsMap){

        if(paramsMap.containsKey("page") && paramsMap.containsKey("size")){
            RestPageImpl<UserVo> pageList = userService.findUsers(paramsMap);

            return ResultUtils.success(pageList);
        }else{
           return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 查询用户列表——不分页
     *
     * 查询参数 account,name
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findUsersAll(@RequestBody Map<String, Object> paramsMap){

        List<UserVo> allList = userService.findUsersAll(paramsMap);

        return ResultUtils.success(allList);

    }

    /**
     * 检查用户名唯一性
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/checkAccount")
    public ResultEntity checkAccount(@RequestBody Map<String, String> paramsMap){

        if(paramsMap.containsKey("account")){
            Boolean result = userService.checkAccount(paramsMap.get("account"));

            return ResultUtils.success(result);

        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }


    public static void main(String[] args)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "123456";

        {
            String encodePassword = encoder.encode(password);

            System.out.println(encodePassword);
        }

        {
            String encodePassword = encoder.encode(password);

            System.out.println(encodePassword);
        }

    }
}
