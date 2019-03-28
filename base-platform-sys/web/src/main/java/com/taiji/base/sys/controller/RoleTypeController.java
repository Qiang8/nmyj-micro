package com.taiji.base.sys.controller;


import com.taiji.base.sys.service.MenuService;
import com.taiji.base.sys.service.RoleService;
import com.taiji.base.sys.service.RoleTypeService;
import com.taiji.base.sys.vo.MenuVo;
import com.taiji.base.sys.vo.RoleTypeMenuVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Title:RoleTypeController.java</p >
 * <p>Description: 用户类型控制类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/roleType")
public class RoleTypeController extends BaseController{

    RoleService roleService;

    RoleTypeService roleTypeService;

    MenuService menuService;


    /**
     * 获取特定roleType所拥有的菜单权限
     * @param roleType
     * @return
     */
    @GetMapping(path = "/menu")
    public ResultEntity findRoleTypeMenus(@NotEmpty(message = "roleType不能为空")
                                              @RequestParam(name = "roleType") String roleType){

        Map<String,Object> params = new HashMap<>();
        List<String>  menus = new ArrayList<>();
        params.put("roleType",roleType);
        List<RoleTypeMenuVo> typeList = roleTypeService.findRoleTypeMenusAll(params);

        List<MenuVo> menuList = menuService.findMenusAll(new HashMap<>());

        Map<String,String> map = menuList.stream()
                .collect(Collectors.toMap(MenuVo::getId,MenuVo::getParentId));

        for(RoleTypeMenuVo v : typeList){
            if(!map.containsValue(v.getMenuId())){
                menus.add(v.getMenuId());
            }
        }

        return ResultUtils.success(menus);
    }

    /**
     * 保存特定roleType所拥有的菜单权限
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/menu")
    public ResultEntity saveRoleTypeMenus(@RequestBody Map<String, Object> paramsMap){

        if(paramsMap.containsKey("roleType")&&
                paramsMap.containsKey("menuIds")){
            String roleType = (String) paramsMap.get("roleType");
            List<String> menuIds = (List<String>)paramsMap.get("menuIds");


            List<RoleTypeMenuVo> menuVos = new ArrayList<>();
            for(String menuId : menuIds){
                RoleTypeMenuVo vo = new RoleTypeMenuVo();
                vo.setMenuId(menuId);
                vo.setRoleType(roleType);
                menuVos.add(vo);
            }

            roleTypeService.saveRoleTypeMenus(menuVos,roleType);

            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }
}
