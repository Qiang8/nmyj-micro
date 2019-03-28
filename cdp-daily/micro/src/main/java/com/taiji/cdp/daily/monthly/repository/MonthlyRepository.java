package com.taiji.cdp.daily.monthly.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.daily.monthly.entity.Monthly;
import com.taiji.cdp.daily.monthly.entity.QMonthly;
import com.taiji.cdp.daily.searchVo.MonthlyPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:MonthlyRepository.java</p >
 * <p>Description: 月报管理MonthlyRepository类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
public class MonthlyRepository extends BaseJpaRepository<Monthly, String> {

    /**
     * 新增月报信息，Monthly不能为空。
     *
     * @param entity 月报entity
     */
    @Transactional
    public Monthly saveEntity(Monthly entity) {
        Assert.notNull(entity, "monthly must not be null!");
        Monthly result;
        if (entity.getId() == null) {
            result = super.save(entity);
        } else {
            Monthly tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }
        return result;
    }

    /**
     * 根据id发布一条记录
     */
    public void publish(String orgId, StatusEnum statusEnum) {
        Monthly monthly = findOne(orgId);
        monthly.setStatus(statusEnum.getCode());
        super.save(monthly);
    }

    /**
     * 分页查询所有的数据
     *
     * @param pageable
     * @return
     */
    public Page<Monthly> findPage(MonthlyPageVo pageVo, Pageable pageable) {

        QMonthly qMonthly = QMonthly.monthly;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<Monthly> query = from(qMonthly);
        String title = pageVo.getTitle();
        if(StringUtils.hasText(title)) {
            builder.and(qMonthly.title.contains(title));
        }
        List<String> ids = pageVo.getIds();
        if(!CollectionUtils.isEmpty(ids)) {
            builder.and(qMonthly.id.in(ids));
        }

        String status = pageVo.getStatus();
        if(StringUtils.hasText(status)) {
            builder.and(qMonthly.status.eq(status));
        }
        String createBy = pageVo.getCreateBy();
        if (StringUtils.hasText(createBy)) {
            builder.and(qMonthly.createBy.eq(createBy));
        }
        builder.and(qMonthly.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(Monthly.class, qMonthly.id, qMonthly.title, qMonthly.status, qMonthly.createTime, qMonthly.createBy, qMonthly.updateBy, qMonthly.updateTime, qMonthly.delFlag)).where(builder).orderBy(qMonthly.createTime.desc());
        return findAll(query, pageable);
    }

}