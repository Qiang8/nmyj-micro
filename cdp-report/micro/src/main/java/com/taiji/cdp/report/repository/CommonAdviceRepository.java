package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.entity.CommonAdvice;
import com.taiji.cdp.report.entity.QCommonAdvice;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-19 20:19
 **/
@Repository
public class CommonAdviceRepository extends BaseJpaRepository<CommonAdvice, String> {
    /**
     * 根据审批信息ID查询
     * @param id
     * @return
     */
    public List<CommonAdvice> findByApprovalId(String id) {
        QCommonAdvice qCommonAdviceEntity = QCommonAdvice.commonAdvice;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<CommonAdvice> query = from(qCommonAdviceEntity);
        builder.and(qCommonAdviceEntity.approvalId.eq(id));
        query.select(Projections.bean(CommonAdvice.class,qCommonAdviceEntity.commonAdviceId)).where(builder);
        return findAll(query);
    }

    /**
     * 根据意见为“上日报”查询
     * @return
     */
    public List<CommonAdvice> findDaily() {
        QCommonAdvice qCommonAdviceEntity = QCommonAdvice.commonAdvice;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<CommonAdvice> query = from(qCommonAdviceEntity);
        builder.and(qCommonAdviceEntity.commonAdviceId.eq("daily"));
        query.select(Projections.bean(CommonAdvice.class,qCommonAdviceEntity.commonAdviceId,qCommonAdviceEntity.approvalId)).where(builder);
        return findAll(query);
    }
}