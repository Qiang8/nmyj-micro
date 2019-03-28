package com.taiji.cdp.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.cmd.entity.QTreat;
import com.taiji.cdp.cmd.entity.Treat;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: nmyj-micro
 * @Description:
 * @Author: TAIJI.WangJian
 * @Date: 2019/3/5 16:48
 **/
@Repository
public class TreatRepository extends BaseJpaRepository<Treat, String> {
    public List<Treat> searchAllTreatment() {
        QTreat qTreat = QTreat.treat;
        JPQLQuery<Treat> query = from(qTreat);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qTreat.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(
                Treat.class,
                qTreat.id,
                qTreat.cdId,
                qTreat.treatment,
                qTreat.treatBy,
                qTreat.treatTime,
                qTreat.notes,
                qTreat.delFlag
        )).where(builder).orderBy(qTreat.updateTime.desc());
        return findAll(query);
    }
}
