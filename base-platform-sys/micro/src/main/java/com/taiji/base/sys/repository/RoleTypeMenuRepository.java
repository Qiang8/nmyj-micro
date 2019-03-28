package com.taiji.base.sys.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.base.sys.entity.QRoleTypeMenu;
import com.taiji.base.sys.entity.RoleTypeMenu;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:RoleTypeMenuRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 15:07</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@Transactional(
        readOnly = true,
        rollbackFor = Exception.class
)
public class RoleTypeMenuRepository extends BaseJpaRepository<RoleTypeMenu,String> {

    public List<RoleTypeMenu> findAllByRoleType(String roleType){
        QRoleTypeMenu qRoleTypeMenu = QRoleTypeMenu.roleTypeMenu;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(roleType))
        {
            builder.and(qRoleTypeMenu.roleType.contains(roleType));
        }

        return findAll(builder);
    }

    public List<RoleTypeMenu> findAllByMenuId(String menuId){
        QRoleTypeMenu qRoleTypeMenu = QRoleTypeMenu.roleTypeMenu;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(menuId))
        {
            builder.and(qRoleTypeMenu.menuId.contains(menuId));
        }

        return findAll(builder);
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteByRoleType(String roleType)
    {
        List<RoleTypeMenu> list = findAllByRoleType(roleType);
        deleteInBatch(list);
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteByMenuId(String menuId)
    {
        List<RoleTypeMenu> list = findAllByMenuId(menuId);
        deleteInBatch(list);
    }

    @Transactional(rollbackFor=Exception.class)
    public void save(List<RoleTypeMenu> list,String roleType)
    {
        deleteByRoleType(roleType);
        save(list);
    }
}
