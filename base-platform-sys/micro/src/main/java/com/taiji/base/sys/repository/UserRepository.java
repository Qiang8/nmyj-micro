package com.taiji.base.sys.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.sys.entity.QUser;
import com.taiji.base.sys.entity.User;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统用户Repository类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Repository
@Transactional(
        readOnly = true
)
public class UserRepository extends BaseJpaRepository<User,String>{

    public User findOne(MultiValueMap<String, Object> params)
    {
        QUser qUser = QUser.user;

        String account = "";

        if(params.containsKey("account"))
        {
            account = params.getFirst("account").toString();
        }

        Assert.hasText(account, "account must not be null!");

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(account))
        {
            builder.and(qUser.account.eq(account));
        }

        builder.and(qUser.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findOne(builder);
    }

    public List<User> findAll(String account,String name){
        QUser qUser = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(account))
        {
            builder.and(qUser.account.eq(account));
        }

        if(StringUtils.hasText(name))
        {
            builder.and(qUser.profile.name.contains(name));
        }

        builder.and(qUser.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        builder.and(qUser.status.eq(StatusEnum.ENABLE.getCode()));

        return findAll(builder);
    }

    public Page<User> findPage(String account,String name, Pageable pageable){
        QUser qUser = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(account))
        {
            builder.and(qUser.account.eq(account));
        }

        if(StringUtils.hasText(name))
        {
            builder.and(qUser.profile.name.contains(name));
        }

        builder.and(qUser.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder,pageable);
    }

    @Override
    @Transactional
    public User save(User entity)
    {
        Assert.notNull(entity, "user must not be null!");

        User result;
        if(entity.getId() == null)
        {
            result = super.save(entity);
        }
        else
        {
            User tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }

    public Boolean checkUnique(String id,String account) {
        QUser qUser = QUser.user;

        JPQLQuery<User> query = from(qUser);

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(id))
        {
            builder.and(qUser.id.ne(id));
        }

        builder.and(qUser.account.eq(account));
        builder.and(qUser.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        List<User> list = query.where(builder).fetch();
        if(list.size() > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 根据orgId获取User对象(目前返回 id与account,profiles)
     * 消息提醒使用
     * @author qizhijie-pc
     * @date 2018年11月28日11:24:40
     * @param orgId
     * @return List<User>
     */
    public List<User> findListByOrgId(String orgId){
        Assert.hasText(orgId,"orgId must not be null");
        QUser qUser = QUser.user;

        JPQLQuery<User> query = from(qUser);

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qUser.profile.orgId.eq(orgId));
        builder.and(qUser.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        builder.and(qUser.status.eq(StatusEnum.ENABLE.getCode()));

        query.select(Projections.bean(User.class,qUser.id,qUser.account,qUser.profile)).where(builder);

        return findAll(query);
    }

    /**
     * 根据orgIds获取User对象(目前返回 id与account,profiles)
     * 消息提醒使用
     * @author qizhijie-pc
     * @date 2018年12月18日14:49:24
     * @param orgIds
     * @return List<User>
     */
    public List<User> findListByOrgIds(List<String> orgIds){

        QUser qUser = QUser.user;

        JPQLQuery<User> query = from(qUser);

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qUser.profile.orgId.in(orgIds));
        builder.and(qUser.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        builder.and(qUser.status.eq(StatusEnum.ENABLE.getCode()));

        query.select(Projections.bean(User.class,qUser.id,qUser.account,qUser.profile)).where(builder);

        return findAll(query);
    }

}
