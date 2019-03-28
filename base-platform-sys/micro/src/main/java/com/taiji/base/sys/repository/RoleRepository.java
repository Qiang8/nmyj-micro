package com.taiji.base.sys.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.sys.entity.QRole;
import com.taiji.base.sys.entity.Role;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:RoleRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/28 10:13</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@Transactional(
        readOnly = true
)
public class RoleRepository extends BaseJpaRepository<Role,String> {

    public List<Role> findAll(String roleName)
    {
        QRole qRole = QRole.role;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(roleName))
        {
            builder.and(qRole.roleName.contains(roleName));
        }

        builder.and(qRole.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        builder.and(qRole.status.eq(StatusEnum.ENABLE.getCode()));

        return findAll(builder);
    }

    public Page<Role> findPage(String roleName, Pageable pageable){
        QRole qRole = QRole.role;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(roleName))
        {
            builder.and(qRole.roleName.contains(roleName));
        }

        builder.and(qRole.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder,pageable);
    }

    @Override
    @Transactional
    public Role save(Role entity)
    {
        Assert.notNull(entity, "role must not be null!");

        Role result;
        if(entity.getId() == null)
        {
            result = super.save(entity);
        }
        else
        {
            Role tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }

    public Boolean checkUnique(String id,String roleCode) {
        QRole qRole = QRole.role;

        JPQLQuery<Role> query = from(qRole);

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(id))
        {
            builder.and(qRole.id.ne(id));
        }

        builder.and(qRole.roleCode.eq(roleCode));
        builder.and(qRole.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        List<Role> list = query.where(builder).fetch();
        if(list.size() > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
