package com.taiji.cdp.daily.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.daily.duty.entity.DutyShiftItemEntity;
import com.taiji.cdp.daily.duty.entity.QDutyShiftItemEntity;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>Title:DutyShiftItemRepository.java</p >
 * <p>Description: 交接班信息DutyShiftItemRepository类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/21 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
public class DutyShiftItemRepository extends BaseJpaRepository<DutyShiftItemEntity, String> {

    /**
     * 根据交接班dutyId获取交接班信息列表
     *
     * @param id 信息Id
     * @return ResponseEntity<DutyShiftItemEntity>
     */
    public List<DutyShiftItemEntity> findByDutyId(String id) {
        Assert.notNull(id, "舆情信息id 不能为null");
        QDutyShiftItemEntity qDutyShiftItemEntity = QDutyShiftItemEntity.dutyShiftItemEntity;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<DutyShiftItemEntity> query = from(qDutyShiftItemEntity);
        builder.and(qDutyShiftItemEntity.dutyId.eq(id));
        query.select(Projections.bean(DutyShiftItemEntity.class, qDutyShiftItemEntity.id, qDutyShiftItemEntity.infoId, qDutyShiftItemEntity.dutyId)).where(builder).fetch();
        return findAll(query);
    }


}
