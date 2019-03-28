package com.taiji.cdp.base.sms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.base.sms.entity.QSms;
import com.taiji.cdp.base.sms.entity.Sms;
import com.taiji.cdp.base.sms.searchVo.SmsListVo;
import com.taiji.cdp.base.sms.searchVo.SmsPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SmsRepository extends BaseJpaRepository<Sms,String> {
    //分页
    public Page<Sms> findPage(SmsPageVo smsPageVo, Pageable pageable){
        QSms sms = QSms.sms;
        JPQLQuery<Sms> query = from(sms);

        BooleanBuilder builder = new BooleanBuilder();

        String content = smsPageVo.getContent();
        String createBy = smsPageVo.getCreateBy();
        String buildOrgId = smsPageVo.getBuildOrgId();
        String sendStatus = smsPageVo.getSendStatus();
        LocalDateTime sendStartTime = smsPageVo.getSendStartTime();
        LocalDateTime sendEndTime = smsPageVo.getSendEndTime();

        if (StringUtils.hasText(content)){
            builder.and(sms.content.contains(content));
        }
        if (StringUtils.hasText(createBy)){
            builder.and(sms.createBy.contains(createBy));
        }
        if (StringUtils.hasText(buildOrgId)){
            builder.and(sms.buildOrgId.eq(buildOrgId));
        }
        if (StringUtils.hasText(sendStatus)){
            builder.and(sms.sendStatus.eq(sendStatus));
        }
        if (null != sendStartTime){
            builder.and(sms.sendTime.before(sendStartTime));
        }
        if (null != sendEndTime){
            builder.and(sms.sendTime.after(sendEndTime));
        }

        builder.and(sms.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Sms.class
                        ,sms.id
                        ,sms.content
                        ,sms.buildOrgId
                        ,sms.buildOrgName
                        ,sms.sendStatus
                        ,sms.sendTime
                        ,sms.createBy
                )).where(builder)
                .orderBy(sms.updateTime.desc());

        return findAll(query,pageable);
    }
    //不分页
    public List<Sms> findList(SmsListVo smsListVo){
        QSms sms = QSms.sms;
        JPQLQuery<Sms> query = from(sms);

        BooleanBuilder builder = new BooleanBuilder();

        String content = smsListVo.getContent();
        String createBy = smsListVo.getCreateBy();
        String buildOrgId = smsListVo.getBuildOrgId();
        String sendStatus = smsListVo.getSendStatus();
        LocalDateTime sendStartTime = smsListVo.getSendStartTime();
        LocalDateTime sendEndTime = smsListVo.getSendEndTime();

        if (StringUtils.hasText(content)){
            builder.and(sms.content.contains(content));
        }
        if (StringUtils.hasText(createBy)){
            builder.and(sms.createBy.contains(createBy));
        }
        if (StringUtils.hasText(buildOrgId)){
            builder.and(sms.buildOrgId.eq(buildOrgId));
        }
        if (StringUtils.hasText(sendStatus)){
            builder.and(sms.sendStatus.eq(sendStatus));
        }
        if (null != sendStartTime){
            builder.and(sms.sendTime.before(sendStartTime));
        }
        if (null != sendEndTime){
            builder.and(sms.sendTime.after(sendEndTime));
        }

        builder.and(sms.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(
                Projections.bean(Sms.class
                        ,sms.id
                        ,sms.content
                        ,sms.buildOrgId
                        ,sms.buildOrgName
                        ,sms.sendStatus
                        ,sms.sendTime
                        ,sms.createBy
                )).where(builder)
                .orderBy(sms.updateTime.desc());

        return findAll(query);
    }

    @Override
    @Transactional
    public Sms save(Sms entity){
        Assert.notNull(entity,"sms 对象不能为Null");

        Sms result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            Sms temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return  result;
    }
}
