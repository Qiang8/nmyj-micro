package com.taiji.base.sys.service;

import com.taiji.base.sys.feign.RoleClient;
import com.taiji.base.sys.feign.RoleTypeClient;
import com.taiji.base.sys.vo.RoleTypeMenuVo;
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
 * <p>Title:RoleTypeService.java</p >
 * <p>Description: 角色信息服务类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class RoleTypeService extends BaseService{

    RoleTypeClient client;

    /**
     * 获取角色类型的菜单列表
     * @param params
     * @return
     */
    public List<RoleTypeMenuVo> findRoleTypeMenusAll(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<RoleTypeMenuVo>> result = client.findList(super.convertMap2MultiValueMap(params));
        List<RoleTypeMenuVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 保存角色类型 菜单权限
     * @param list
     * @param roleType
     */
    public  void saveRoleTypeMenus(List<RoleTypeMenuVo> list , String roleType){
        Assert.hasText(roleType,"roleType不能为null或空字符串!");

        ResponseEntity<Void> result = client.save(list,roleType);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }
}
