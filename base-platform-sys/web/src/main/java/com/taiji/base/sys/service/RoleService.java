package com.taiji.base.sys.service;

import com.taiji.base.sys.feign.RoleClient;
import com.taiji.base.sys.vo.RoleVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <p>Title:UserService.java</p >
 * <p>Description: 角色信息服务类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class RoleService extends BaseService{

    RoleClient client;

    /**
     * 根据id获取角色信息
     * @param id
     * @return
     */
    public RoleVo findById(String id){
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<RoleVo> result = client.find(id);
        RoleVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 新增角色信息
     * @param vo
     * @return
     */
    public void create(RoleVo vo){
        Assert.notNull(vo, "RoleVo不能为null值。");

        ResponseEntity<RoleVo> result = client.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 更新角色信息
     * @param vo
     * @param id
     */
    public void update(RoleVo vo, String id){
        Assert.notNull(vo,"vo不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<RoleVo> result = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 逻辑删除角色
     * @param id
     */
    public void delete(String id){
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 查询分页列表
     * @param params
     * @return
     */
    public RestPageImpl<RoleVo> findRoles(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<RestPageImpl<RoleVo>> result = client.findPage(super.convertMap2MultiValueMap(params));
        RestPageImpl<RoleVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 查询列表
     * @param params
     * @return
     */
    public List<RoleVo> findRolesAll(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<RoleVo>> result = client.findList(super.convertMap2MultiValueMap(params));
        List<RoleVo> body =  ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 检查用户登录名的重复性
     * @param roleCode
     * @return
     */
    public Boolean checkRoleCode(String roleCode){
        Assert.hasText(roleCode, "roleCode不能为null或空字符串!");

        ResponseEntity<Boolean> result= client.checkUnique("",roleCode);
        Boolean body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return  body;
    }
}
