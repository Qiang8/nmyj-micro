package com.taiji.base.sys.controller;


import com.taiji.base.sys.service.MenuService;
import com.taiji.base.sys.service.UserService;
import com.taiji.base.sys.vo.CurrentMenuDTO;
import com.taiji.base.sys.vo.MenuVo;
import com.taiji.base.sys.vo.RoleVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.AssemblyTreeUtils;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * <p>Title:MenuInfoController.java</p >
 * <p>Description: 菜单服务控制类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/menuInfo")
public class MenuInfoController extends BaseController{

    MenuService menuService;

    UserService userService;

    /**
     * 获取当前用户菜单权限
     * @return
     */
    @GetMapping(path = "/currentMenu")
    public ResultEntity findCurrentMenuList(Principal principal){

        String account = principal.getName();
        Map<String,Object> params = new HashMap<>();
        Assert.hasText(account,"account不能为空");
        params.put("account",account);
        UserVo u = userService.findOneUser(params);

        List<RoleVo> roleList = u.getRoleList();
        List<MenuVo> menus = new ArrayList<>();
        for(RoleVo vo : roleList){
            List<MenuVo> mList = vo.getMenuList().stream().filter(a ->
                    a.getType().equals("0")).collect(Collectors.toList());
            menus.addAll(mList);
        }

        List<MenuVo> uniqueMenu = menus.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getId()))),ArrayList::new));


        MenuVo top = new MenuVo();
        top.setId("-1");
        top.setMenuName("系统菜单");
        top.setParentId("-1");
        uniqueMenu.add(top);

        List<CurrentMenuDTO> cmenus= new ArrayList<>();
        for(MenuVo v : uniqueMenu){
            CurrentMenuDTO dto = new CurrentMenuDTO(v);
            cmenus.add(dto);
        }

        List<CurrentMenuDTO> currentMenu = AssemblyTreeUtils.assemblyTree(cmenus);

        return ResultUtils.success(currentMenu.get(0).getChildren());
    }

    /**
     * 获取当前用户按钮权限
     * @return
     */
    @GetMapping(path = "/currentButton")
    public ResultEntity findCurrentButtonList(Principal principal){
        String account = principal.getName();
        Map<String,Object> params = new HashMap<>();
        Assert.hasText(account,"account不能为空");

        params.put("account",account);

        UserVo u = userService.findOneUser(params);

        List<RoleVo> roleList = u.getRoleList();
        List<String> buttonAuth = new ArrayList<>();
        for(RoleVo vo : roleList){
            List<String> mList = vo.getMenuList().stream().filter(a ->a.getType().equals("1"))
                    .map(MenuVo::getPermission).collect(Collectors.toList());

            buttonAuth.addAll(mList);
        }

        List<String> uniqueButton = buttonAuth.stream().distinct().collect(Collectors.toList());


        return ResultUtils.success(uniqueButton);
    }
}
