package com.taiji.base.sys.controller;

import com.taiji.base.sys.service.MenuService;
import com.taiji.base.sys.vo.MenuVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.exception.ResultException;
import com.taiji.micro.common.utils.AssemblyTreeUtils;
import com.taiji.micro.common.utils.ITreeNode;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:MenuContoller.java</p >
 * <p>Description: 菜单管理控制类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/menus")
public class MenuContoller extends BaseController{

    MenuService menuService;

    /**
     * 获取菜单树
     * @param parentId
     * @param roleType
     * @return
     */
    @GetMapping
    public ResultEntity findMenuList(@RequestParam(name = "parentId",required = false) String parentId,
                                     @RequestParam(name = "roleType",required = false) String roleType){

        Map<String,Object> params = new HashMap<>();
        if(StringUtils.isNotEmpty(parentId)){
            params.put("parentId",parentId);
        }
        if(StringUtils.isNotEmpty(roleType)){
            params.put("roleType",roleType);
        }

        List<MenuVo> voList =  menuService.findMenusAll(params);

        MenuVo top = new MenuVo();
        top.setId("-1");
        top.setMenuName("系统菜单");
        top.setParentId("-1");
        voList.add(top);

        List<MenuVo> root = AssemblyTreeUtils.assemblyTree(voList);

        return ResultUtils.success(root);

    }

    /**
     * 新增菜单
     * @param menuVo
     * @return
     */
    @PostMapping
    public ResultEntity addMenu(
            @RequestBody
            @Validated
            @NotNull(message = "menuVo不能为null") MenuVo menuVo){

        if(menuVo.getOrders() == null) {
            menuVo.setOrders(999);
        }

        menuService.create(menuVo);

        return ResultUtils.success();
    }

    /**
     * 获取单个菜单信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findMenuById(@NotEmpty(message = "id不能为空")
                                     @PathVariable(name = "id") String id){
        MenuVo menuVo = menuService.findById(id);

        return ResultUtils.success(menuVo);
    }

    /**
     * 编辑菜单信息
     * @param id
     * @param menuVo
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateMenu(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id,
                                   @NotNull(message = "menuVo不能为null") @RequestBody MenuVo menuVo){
        menuService.update(menuVo,id);

        return ResultUtils.success();
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteMenu(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id){

        menuService.delete(id);

        return ResultUtils.success();
    }

}
