package com.taiji.base.sys.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.base.sys.entity.Menu;
import com.taiji.base.sys.entity.QMenu;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统资源菜单Repository类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Repository
public class MenuRepository extends BaseJpaRepository<Menu,String>{
    public List<Menu> findAll(String parentId, List<String> menuIdList){
        QMenu qMenu = QMenu.menu;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(parentId))
        {
            builder.and(qMenu.parentId.eq(parentId));
        }

        if(!CollectionUtils.isEmpty(menuIdList))
        {
            builder.and(qMenu.id.in(menuIdList));
        }

        builder.and(qMenu.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder);
    }

    @Override
    @Transactional
    public Menu save(Menu entity)
    {
        Assert.notNull(entity, "user must not be null!");

        Menu result;
        if(entity.getId() == null)
        {
            result = super.save(entity);
        }
        else
        {
            Menu tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }
}
