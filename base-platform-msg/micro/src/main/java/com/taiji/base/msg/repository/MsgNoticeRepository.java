package com.taiji.base.msg.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.base.msg.entity.MsgNotice;
import com.taiji.base.msg.entity.QMsgNotice;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * <p>Title:MsgNoticeRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/29 17:13</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@Transactional(
        readOnly = true
)
public class MsgNoticeRepository extends BaseJpaRepository<MsgNotice,String> {

    public Page<MsgNotice> findPage(MultiValueMap<String, Object> params, Pageable pageable){
        QMsgNotice qMsgNotice = QMsgNotice.msgNotice;

        String account = "";
        String orgId = "";

        if(params.containsKey("account"))
        {
            account = params.getFirst("account").toString();
        }

        if(params.containsKey("orgId"))
        {
            orgId = params.getFirst("orgId").toString();
        }

        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(account)){
            builder.and(qMsgNotice.createBy.eq(account));
        }

        if(StringUtils.hasText(orgId)){
            builder.and(qMsgNotice.sendOrg.eq(orgId));
        }

        builder.and(qMsgNotice.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder,pageable);
    }

    @Override
    @Transactional
    public MsgNotice save(MsgNotice entity)
    {
        Assert.notNull(entity, "MsgNotice must not be null!");

        MsgNotice result;
        if(!StringUtils.hasText(entity.getId()))
        {
            result = super.saveAndFlush(entity);
        }
        else
        {
            MsgNotice tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.saveAndFlush(tempEntity);
        }

        return result;
    }
}
