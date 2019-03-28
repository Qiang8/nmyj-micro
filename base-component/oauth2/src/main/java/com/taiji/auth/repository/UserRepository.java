package com.taiji.auth.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.auth.entity.QUser;
import com.taiji.auth.entity.User;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

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
}
