package com.taiji.base.sys.controller;


import com.taiji.base.sys.service.MenuService;
import com.taiji.base.sys.service.RoleService;
import com.taiji.base.sys.vo.MenuVo;
import com.taiji.base.sys.vo.RoleVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Title:RoleInfoController.java</p >
 * <p>Description: 角色服务控制类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/roleInfo")
public class RoleInfoController extends BaseController{

    RoleService roleService;

    MenuService menuService;
    /**
     * 获取角色菜单关联信息
     * @param roleId
     * @return
     */
    @GetMapping(path = "/menu")
    public ResultEntity findRoleMenus(@NotEmpty(message = "roleId为空") @RequestParam(name = "roleId") String roleId){

        RoleVo r = roleService.findById(roleId);
        List<String> roleMenus = new ArrayList<>();

        Map<String,String> map = r.getMenuList().stream()
                .collect(Collectors.toMap(MenuVo::getId,MenuVo::getParentId));

        for(MenuVo vo : r.getMenuList()){
            if(!map.containsValue(vo.getId())){
                roleMenus.add(vo.getId());
            }
        }

        return ResultUtils.success(roleMenus);
    }

    /**
     * 保存角色资源信息
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/menu")
    public ResultEntity saveRoleMenus(@RequestBody Map<String, Object> paramsMap){

        if(paramsMap.containsKey("roleId")&&
                paramsMap.containsKey("menuIds")){
            String roleId = (String) paramsMap.get("roleId");
            List<String> menuIds = (List<String>)paramsMap.get("menuIds");

            RoleVo r = roleService.findById(roleId);
            List<MenuVo> menuVos = new ArrayList<>();
            for(String menuId : menuIds){
                MenuVo m = menuService.findById(menuId);
                menuVos.add(m);
            }

            r.setMenuList(menuVos);

            roleService.update(r,roleId);

            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }
}
