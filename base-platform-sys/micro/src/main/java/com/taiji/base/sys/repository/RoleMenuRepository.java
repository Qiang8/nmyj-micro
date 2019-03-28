package com.taiji.base.sys.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * <p>Title:RoleMenuRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/25 15:11</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
public class RoleMenuRepository {
    @PersistenceContext
    protected EntityManager em;

    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenuByMenuId(String menuId)
    {
        Query query = this.em.createNativeQuery("delete from sys_role_menus where menu_id = :menuId");
        query.setParameter("menuId",menuId);
        query.executeUpdate();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenuByRoleId(String roleId)
    {
        Query query = this.em.createNativeQuery("delete from sys_role_menus where role_id = :roleId");
        query.setParameter("roleId",roleId);
        query.executeUpdate();
    }
}
