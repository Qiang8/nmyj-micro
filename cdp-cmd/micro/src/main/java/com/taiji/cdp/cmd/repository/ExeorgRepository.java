package com.taiji.cdp.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.cmd.entity.ExeorgEntity;
import com.taiji.cdp.cmd.entity.QExeorgEntity;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:ExeorgRepository.java</p >
 * <p>Description: 舆情调控单与处置单位关联ExeorgRepository类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/02/26 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
public class ExeorgRepository extends BaseJpaRepository<ExeorgEntity, String> {

    /**
     * 新增关联关系Vo
     */
    @Override
    @Transactional
    public ExeorgEntity save(ExeorgEntity entity) {
        Assert.notNull(entity, "ExeorgEntity 不能为null");
        ExeorgEntity result;
        if (!StringUtils.hasText(entity.getId())) {
            entity.setId(null);
            result = super.save(entity);
        } else {
            ExeorgEntity temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 根据舆情调控单id获取关联关系
     *
     * @param cmdId 舆情调控单信息Id
     * @return ResponseEntity<ExeorgEntity>
     */
    public List<ExeorgEntity> findByCmdId(String cmdId) {
        Assert.notNull(cmdId, "调控单信息id 不能为null");
        QExeorgEntity qExeorgEntity = QExeorgEntity.exeorgEntity;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<ExeorgEntity> query = from(qExeorgEntity);
        builder.and(qExeorgEntity.cdId.eq(cmdId));
        query.select(Projections.bean(ExeorgEntity.class, qExeorgEntity.id, qExeorgEntity.orgId, qExeorgEntity.cdId, qExeorgEntity.sendTime)).where(builder);
        return findAll(query);
    }

    /**
     * 根据组织机构id查询舆情调控单id
     *
     * @param orgId 组织机构Id
     * @return ResponseEntity<ExeorgEntity>
     */
    public List<ExeorgEntity> findcmdIdByOrgId(String orgId) {
        Assert.notNull(orgId, "组织机构id 不能为null");
        QExeorgEntity qExeorgEntity = QExeorgEntity.exeorgEntity;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<ExeorgEntity> query = from(qExeorgEntity);
        builder.and(qExeorgEntity.orgId.eq(orgId));
        query.select(Projections.bean(ExeorgEntity.class, qExeorgEntity.cdId)).where(builder);
        return findAll(query);
    }


}
