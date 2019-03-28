package com.taiji.cdp.cmd.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.cmd.entity.CommandEntity;
import com.taiji.cdp.cmd.entity.QCommandEntity;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * <p>Title:CommandRepository.java</p >
 * <p>Description: 舆情调控单接CommandRepository类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/02/26 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
public class CommandRepository extends BaseJpaRepository<CommandEntity, String> {

    /**
     * 新增舆情调控单
     *
     * @param entity CommandEntity
     * @return ResponseEntity<CommandVo>
     */
    @Override
    @Transactional
    public CommandEntity save(CommandEntity entity) {
        Assert.notNull(entity, "舆情调控单信息不能为空给！");
        CommandEntity result;
        if (entity.getId() == null) {
            result = super.save(entity);
        } else {
            CommandEntity tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }
        return result;
    }

    /**
     * 分页查询所有的数据
     *
     * @param pageable
     * @return
     */
    public Page<CommandEntity> findPage(MultiValueMap<String, Object> params, Pageable pageable) {

        QCommandEntity qCommandEntity = QCommandEntity.commandEntity;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<CommandEntity> query = from(qCommandEntity);
        if (params.containsKey("handleFlag")) {
            builder.and(qCommandEntity.handleFlag.contains(params.getFirst("handleFlag").toString()));
        }
        if (params.containsKey("createOrgId")) {
            builder.and(qCommandEntity.createOrgId.contains(params.getFirst("createOrgId").toString()));
        }
        builder.and(qCommandEntity.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(CommandEntity.class, qCommandEntity.id, qCommandEntity.title, qCommandEntity.infoId, qCommandEntity.createTime, qCommandEntity.createBy, qCommandEntity.updateBy, qCommandEntity.updateTime, qCommandEntity.delFlag, qCommandEntity.centerOpinion, qCommandEntity.disposalSituation, qCommandEntity.handleFlag, qCommandEntity.judgeOpinion, qCommandEntity.leaderOpinion, qCommandEntity.startTime, qCommandEntity.transactOpinion, qCommandEntity.transmission, qCommandEntity.startWebsite)).where(builder).orderBy(qCommandEntity.createTime.desc());
        return findAll(query, pageable);
    }

    /**
     * 分页查询所有的数据,recive的数据，接受的数据
     *
     * @param pageable
     * @return
     */
    public Page<CommandEntity> findRecivePage(MultiValueMap<String, Object> params, Pageable pageable) {

        QCommandEntity qCommandEntity = QCommandEntity.commandEntity;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<CommandEntity> query = from(qCommandEntity);
        if (params.containsKey("handleFlag")) {
            builder.and(qCommandEntity.handleFlag.contains(params.getFirst("handleFlag").toString()));
        }
        if (params.containsKey("cdId")) {
            builder.and(qCommandEntity.id.in((List) params.getFirst("cdId")));
        }
        builder.and(qCommandEntity.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(CommandEntity.class, qCommandEntity.id, qCommandEntity.title, qCommandEntity.infoId, qCommandEntity.createTime, qCommandEntity.createBy, qCommandEntity.updateBy, qCommandEntity.updateTime, qCommandEntity.delFlag, qCommandEntity.centerOpinion, qCommandEntity.disposalSituation, qCommandEntity.handleFlag, qCommandEntity.judgeOpinion, qCommandEntity.leaderOpinion, qCommandEntity.startTime, qCommandEntity.transactOpinion, qCommandEntity.transmission, qCommandEntity.startWebsite)).where(builder).orderBy(qCommandEntity.createTime.desc());
        return findAll(query, pageable);
    }

    /**
     * 根据id发布一条记录
     */
    public void updateStatus(String cdId, String status) {
        CommandEntity entity = findOne(cdId);
        entity.setHandleFlag(status);
        super.save(entity);
    }

}
