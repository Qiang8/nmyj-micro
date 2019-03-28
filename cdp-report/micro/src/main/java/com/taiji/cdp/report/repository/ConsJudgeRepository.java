package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.entity.ConsJudgeInfo;
import com.taiji.cdp.report.entity.QConsJudgeInfo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:ConsJudgeRepository.java</p >
 * <p>Description: 信息研判ConsJudgeRepository类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
public class ConsJudgeRepository extends BaseJpaRepository<ConsJudgeInfo, String> {

    /**
     * 新增研判信息Vo
     *
     * @param entity 新增研判信息entity
     * @return ConsJudgeInfo
     */
    @Override
    @Transactional
    public ConsJudgeInfo save(ConsJudgeInfo entity) {
        Assert.notNull(entity, "ConsJudgeInfo 不能为null");
        ConsJudgeInfo result;
        if (!StringUtils.hasText(entity.getId())) {
            entity.setId(null);
            result = super.save(entity);
        } else {
            ConsJudgeInfo temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据舆情信息id获取单条研判信息Vo
     *
     * @param judgeId 信息Id
     * @return ResponseEntity<ConsJudgeVo>
     */
    public List<ConsJudgeInfo> findByJudgeId(String judgeId) {
        Assert.notNull(judgeId, "舆情信息id 不能为null");
        QConsJudgeInfo qConsJudgeInfo = QConsJudgeInfo.consJudgeInfo;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<ConsJudgeInfo> query = from(qConsJudgeInfo);
        builder.and(qConsJudgeInfo.infoId.eq(judgeId));
        query.select(Projections.bean(ConsJudgeInfo.class, qConsJudgeInfo.id, qConsJudgeInfo.infoId, qConsJudgeInfo.infoTypeIds,qConsJudgeInfo.isInvolve, qConsJudgeInfo.opinion,qConsJudgeInfo.tagType, qConsJudgeInfo.createTime, qConsJudgeInfo.createBy, qConsJudgeInfo.updateBy, qConsJudgeInfo.updateTime, qConsJudgeInfo.delFlag)).where(builder).fetch();
        return findAll(query);
    }

    /**
     * 根据舆情信息id获取单条研判信息Vo
     *
     * @param ids 信息Id
     * @return ResponseEntity<ConsJudgeVo>
     */
    public List<ConsJudgeInfo> findStatTypes(List<String> ids) {
        QConsJudgeInfo qConsJudgeInfo = QConsJudgeInfo.consJudgeInfo;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<ConsJudgeInfo> query = from(qConsJudgeInfo);
        if(!CollectionUtils.isEmpty(ids)){
            builder.and(qConsJudgeInfo.infoId.in(ids));
        }
        builder.and(qConsJudgeInfo.tagType.isNotEmpty());
        builder.and(qConsJudgeInfo.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(
                ConsJudgeInfo.class,
                qConsJudgeInfo.tagType
        )).where(builder);
        return findAll(query);
    }




}
