package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.entity.EvidenceInfo;
import com.taiji.cdp.report.entity.QEvidenceInfo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:EvdienceRepository.java</p >
 * <p>Description: 电子取证EvdienceRepository类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
public class EvdienceRepository extends BaseJpaRepository<EvidenceInfo, String> {


    /**
     * 新增电子取证信息Vo
     *
     * @param entity 新增电子取证信息entity
     * @return EvidenceInfo
     */
    @Override
    @Transactional
    public EvidenceInfo save(EvidenceInfo entity) {
        Assert.notNull(entity, "ConsJudgeInfo 不能为null");
        EvidenceInfo result;
        if (!StringUtils.hasText(entity.getId())) {
            entity.setId(null);
            result = super.save(entity);
        } else {
            EvidenceInfo temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据舆情信息id获取单条取证信息Vo
     *
     * @param judgeId 信息Id
     * @return ResponseEntity<EvidenceInfo>
     */
    public List<EvidenceInfo> findByJudgeId(String judgeId) {
        Assert.notNull(judgeId, "舆情信息id 不能为null");
        QEvidenceInfo qEvidenceInfo = QEvidenceInfo.evidenceInfo;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<EvidenceInfo> query = from(qEvidenceInfo);
        builder.and(qEvidenceInfo.infoId.eq(judgeId));
        query.select(Projections.bean(EvidenceInfo.class, qEvidenceInfo.id, qEvidenceInfo.infoId, qEvidenceInfo.infoUrl, qEvidenceInfo.photoLocation,qEvidenceInfo.photoServerLocation,qEvidenceInfo.edTime)).where(builder).fetch();
        return findAll(query);
    }

}
