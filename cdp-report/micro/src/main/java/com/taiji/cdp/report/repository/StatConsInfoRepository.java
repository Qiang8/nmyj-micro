package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.common.global.StatGlobal;
import com.taiji.cdp.report.entity.ConsInfo;
import com.taiji.cdp.report.entity.QConsInfo;
import com.taiji.cdp.report.vo.StatVQueryVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class StatConsInfoRepository extends BaseJpaRepository<ConsInfo,String>{

    /**
     *  根据不同条件 统计舆情数量 （统计使用）
     */
    public List<ConsInfo> findStatCons(StatVQueryVo statVQueryVo){
        QConsInfo consInfo = QConsInfo.consInfo;
        JPQLQuery<ConsInfo> query = from(consInfo);
        BooleanBuilder builder = new BooleanBuilder();
        LocalDateTime today_start = statVQueryVo.getStartLocalDate();
        LocalDateTime today_end = statVQueryVo.getEndLocalDate();

        String orgId = statVQueryVo.getOrgId();
        List<String> status = statVQueryVo.getStatus();
        if( today_start!=null && today_end!=null){
            builder.and(consInfo.lastDealTime.between(today_start,today_end));
            builder.and(consInfo.lastDealTime.isNotNull());
        }
        if(today_end == null && today_start!=null) {
            builder.and(consInfo.lastDealTime.loe(today_start));
        }
        if(!StringUtils.isEmpty(orgId)){
            builder.and(consInfo.createOrgId.eq(orgId));
        }
        if(!CollectionUtils.isEmpty(status)){
            builder.and(consInfo.status.in(status));
        }

        builder.and(consInfo.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(consInfo.lastDealTime.desc());
        return findAll(query);
    }

    /**
     * 舆情信息上报来源统计(本年度)
     */
    public List<ConsInfo> findStatTypes(String orgId, LocalDate localDate){
        QConsInfo consInfo = QConsInfo.consInfo;
        JPQLQuery<ConsInfo> query = from(consInfo);
        BooleanBuilder builder = new BooleanBuilder();

        LocalDateTime endLocalDate =  LocalDateTime.of(localDate, LocalTime.MAX);
        LocalDateTime startLocalDate = localDate.plusDays(-7).atStartOfDay();
        if(StringUtils.hasText(orgId)){
            if(!StatGlobal.STAT_ORG_ID.equals(orgId)){
                builder.and(consInfo.createOrgId.eq(orgId));
            }
        }
        builder.and(consInfo.lastDealTime.between(startLocalDate,endLocalDate));
        builder.and(consInfo.lastDealTime.isNotNull());
        builder.and(consInfo.status.in(StatGlobal.STAT_CONS_INFO_ONE));
        builder.and(consInfo.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(consInfo.updateTime.desc());
        return findAll(query);
    }

}
