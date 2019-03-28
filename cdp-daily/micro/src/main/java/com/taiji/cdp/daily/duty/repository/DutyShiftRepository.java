package com.taiji.cdp.daily.duty.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.daily.duty.entity.DutyShiftEntity;
import com.taiji.cdp.daily.duty.entity.QDutyShiftEntity;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * <p>Title:DutyShiftRepository.java</p >
 * <p>Description: 交接班DutyShiftRepository类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/21 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
public class DutyShiftRepository extends BaseJpaRepository<DutyShiftEntity, String> {

    /**
     * 分页查询所有的数据
     *
     * @param pageable
     * @return
     */
    public Page<DutyShiftEntity> findPage(MultiValueMap<String, Object> params, Pageable pageable) {

        QDutyShiftEntity qD = QDutyShiftEntity.dutyShiftEntity;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<DutyShiftEntity> query = from(qD);
        if (params.containsKey("title")) {
            builder.and(qD.title.contains(params.getFirst("title").toString()));
        }
        if (params.containsKey("fromTeamId")) {
            builder.and(qD.fromTeamId.contains(params.getFirst("fromTeamId").toString()));
        }
        if (params.containsKey("toTeamId")) {
            builder.and(qD.toTeamId.contains(params.getFirst("toTeamId").toString()));
        }
        query.select(Projections.bean(DutyShiftEntity.class, qD.id, qD.title, qD.fromTeamId, qD.createTime, qD.createBy, qD.fromTeamName, qD.toTeamId, qD.toTeamName, qD.createOrgId, qD.notes)).where(builder).orderBy(qD.createTime.desc());
        return findAll(query, pageable);
    }

    /**
     * 获取最新一条交接班信息
     *
     * @return DutyShiftEntity
     */
    @Transactional
    public DutyShiftEntity findNewDuty() {
        QDutyShiftEntity qD = QDutyShiftEntity.dutyShiftEntity;
        JPQLQuery<DutyShiftEntity> query = from(qD);
        query.select(Projections.bean(DutyShiftEntity.class, qD.id, qD.title, qD.fromTeamId, qD.createTime, qD.createBy, qD.fromTeamName, qD.toTeamId, qD.toTeamName, qD.createOrgId, qD.notes)).orderBy(qD.createTime.desc());
        List<DutyShiftEntity> dtList = findAll(query);
        if (dtList != null && dtList.size() > 0) {
            return dtList.get(0);
        }
        return null;
    }

}
