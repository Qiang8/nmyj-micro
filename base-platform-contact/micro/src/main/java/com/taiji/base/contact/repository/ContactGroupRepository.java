package com.taiji.base.contact.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.base.contact.entity.ContactGroup;
import com.taiji.base.contact.entity.QContactGroup;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:ContactGroupRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/12/20 16:16</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@Transactional(
        readOnly = true
)
public class ContactGroupRepository extends BaseJpaRepository<ContactGroup,String> {

    public List<ContactGroup> findAll(String userId){
        QContactGroup qContactGroup = QContactGroup.contactGroup;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(userId))
        {
            builder.and(qContactGroup.userId.eq(userId));
        }

        builder.and(qContactGroup.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        Sort sort = new Sort(Sort.Direction.ASC, "orders");
        return findAll(builder,sort);
    }

    @Override
    @Transactional
    public ContactGroup save(ContactGroup entity)
    {
        Assert.notNull(entity, "contactGroup must not be null!");

        ContactGroup result;
        if(entity.getId() == null)
        {
            result = super.save(entity);
        }
        else
        {
            ContactGroup tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }
}
