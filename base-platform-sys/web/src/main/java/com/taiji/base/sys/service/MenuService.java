package com.taiji.base.sys.service;

import com.taiji.base.sys.feign.MenuClient;
import com.taiji.base.sys.vo.MenuVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <p>Title:MenuService.java</p >
 * <p>Description: 菜单信息服务类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class MenuService extends BaseService{

    MenuClient client;

    /**
     * 根据id获取菜单信息
     * @param id
     * @return
     */
    public MenuVo findById(String id){
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<MenuVo> result = client.find(id);
        MenuVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 新增菜单信息
     * @param vo
     * @return
     */
    public void create(MenuVo vo){
        Assert.notNull(vo, "MenuVo不能为null值。");

        ResponseEntity<MenuVo> result = client.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 更新菜单信息
     * @param vo
     * @param id
     */
    public void update(MenuVo vo, String id){
        Assert.notNull(vo,"vo不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<MenuVo> result = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 逻辑删除菜单
     * @param id
     */
    public void delete(String id){
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }


    /**
     * 查询列表
     * @param params
     * @return
     */
    public List<MenuVo> findMenusAll(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<MenuVo>> result = client.findList(super.convertMap2MultiValueMap(params));
        List<MenuVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

}
