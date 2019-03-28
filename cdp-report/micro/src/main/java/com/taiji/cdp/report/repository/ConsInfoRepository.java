package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.entity.ConsInfo;
import com.taiji.cdp.report.entity.QConsInfo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public class ConsInfoRepository extends BaseJpaRepository<ConsInfo, String> {

    @Override
    @Transactional
    public ConsInfo save(ConsInfo entity) {
        Assert.notNull(entity, "ConsInfo 不能为null");
        ConsInfo result;
        entity.setConsEvidences(null); //避免影响保存结果
        if (!StringUtils.hasText(entity.getId())) {
            entity.setId(null);
            result = super.save(entity);
        } else {
            ConsInfo temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, temp);
            result = super.save(temp);
        }

        return result;
    }

    //分页查询-- 盟市使用
    //参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)、createTimeStart(创建时间开始)
    //    、createTimeEnd(创建时间结束)、statuses(舆情信息状态,string[])、orgId(创建部门orgId)
    public Page<ConsInfo> findPage(MultiValueMap<String, Object> params, Pageable pageable) {
        QConsInfo consInfo = QConsInfo.consInfo;

        JPQLQuery<ConsInfo> query = from(consInfo);
        BooleanBuilder builder = new BooleanBuilder();

        if (params.containsKey("title")) {
            String title = params.getFirst("title").toString();
            if (!StringUtils.isEmpty(title)) {
                builder.and(consInfo.title.contains(title));
            }
        }

        if (params.containsKey("reportTimeStart")) {
            //LocalDateTime reportTimeStart = (LocalDateTime) params.getFirst("reportTimeStart");
            LocalDateTime reportTimeStart = DateUtil.strToLocalDateTime(params.getFirst("reportTimeStart").toString());
            if (null != reportTimeStart) {
                builder.and(consInfo.reportTime.after(reportTimeStart));
            }
        }

        if (params.containsKey("reportTimeEnd")) {
            //LocalDateTime reportTimeEnd = (LocalDateTime) params.getFirst("reportTimeEnd");
            LocalDateTime reportTimeEnd = DateUtil.strToLocalDateTime(params.getFirst("reportTimeEnd").toString());
            if (null != reportTimeEnd) {
                builder.and(consInfo.reportTime.before(reportTimeEnd));
            }
        }

        if (params.containsKey("createTimeStart")) {
            //LocalDateTime createTimeStart = (LocalDateTime) params.getFirst("createTimeStart");
            LocalDateTime createTimeStart = DateUtil.strToLocalDateTime(params.getFirst("createTimeStart").toString());
            if (null != createTimeStart) {
                builder.and(consInfo.createTime.after(createTimeStart));
            }
        }

        if (params.containsKey("createTimeEnd")) {
            //LocalDateTime createTimeEnd = (LocalDateTime) params.getFirst("createTimeEnd");
            LocalDateTime createTimeEnd = DateUtil.strToLocalDateTime(params.getFirst("createTimeEnd").toString());
            if (null != createTimeEnd) {
                builder.and(consInfo.createTime.before(createTimeEnd));
            }
        }

        if (params.containsKey("statuses")) {
            Object obj = params.get("statuses");
            List<String> statuses = (List<String>) obj;
            if (!CollectionUtils.isEmpty(statuses)) {
                builder.and(consInfo.status.in(statuses));
            }
        }

        if (params.containsKey("orgId")) {
            String orgId = params.getFirst("orgId").toString();
            if (!StringUtils.isEmpty(orgId)) {
                builder.and(consInfo.createOrgId.eq(orgId));
            }
        }

        builder.and(consInfo.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(
                ConsInfo.class,
                consInfo.id,
                consInfo.title,
                consInfo.infoUrl,
                consInfo.content,
                consInfo.sourceTypeName,
                consInfo.reportWay,
                consInfo.websiteName,
                consInfo.reporter,
                consInfo.reporterTel,
                consInfo.reportReason,
                consInfo.reportTime,
                consInfo.receiveOrgCode,
                consInfo.lastDealUser,
                consInfo.lastDealTime,
                consInfo.status,
                consInfo.createOrgId,
                consInfo.createOrgName,
                consInfo.createBy,
                consInfo.createTime
        )).where(builder).orderBy(consInfo.updateTime.desc());
        return findAll(query, pageable);
    }

    //分页查询-- 自治区使用
    //参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)
    //     、statuses(舆情信息状态,string[])
    public Page<ConsInfo> findRecPage(MultiValueMap<String, Object> params, Pageable pageable) {
        QConsInfo consInfo = QConsInfo.consInfo;

        JPQLQuery<ConsInfo> query = from(consInfo);
        BooleanBuilder builder = new BooleanBuilder();

        if (params.containsKey("title")) {
            String title = params.getFirst("title").toString();
            if (!StringUtils.isEmpty(title)) {
                builder.and(consInfo.title.contains(title));
            }
        }

        if (params.containsKey("reportTimeStart")) {
            //LocalDateTime reportTimeStart = (LocalDateTime) params.getFirst("reportTimeStart");
            LocalDateTime reportTimeStart = DateUtil.strToLocalDateTime(params.getFirst("reportTimeStart").toString());
            if (null != reportTimeStart) {
                builder.and(consInfo.reportTime.after(reportTimeStart));
            }
        }

        if (params.containsKey("reportTimeEnd")) {
            //LocalDateTime reportTimeEnd = (LocalDateTime) params.getFirst("reportTimeEnd");
            LocalDateTime reportTimeEnd = DateUtil.strToLocalDateTime(params.getFirst("reportTimeEnd").toString());
            if (null != reportTimeEnd) {
                builder.and(consInfo.reportTime.before(reportTimeEnd));
            }
        }

        if (params.containsKey("statuses")) {
            Object obj = params.get("statuses");
            List<String> statuses = (List<String>) obj;
            if (!CollectionUtils.isEmpty(statuses)) {
                builder.and(consInfo.status.in(statuses));
            }
        }
        if (params.containsKey("reportWay")) {
            Object obj = params.get("reportWay");
            List<String> reportWay = (List<String>) obj;
            if (!CollectionUtils.isEmpty(reportWay)) {
                builder.and(consInfo.reportWay.in(reportWay));
            }
        }

        builder.and(consInfo.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.where(builder).orderBy(consInfo.updateTime.desc());
        return findAll(query, pageable);
    }

    //不分页查询
    //参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)
    //     、statuses(舆情信息状态,string[])
    public List<ConsInfo> findRecList(MultiValueMap<String, Object> params) {
        QConsInfo consInfo = QConsInfo.consInfo;

        JPQLQuery<ConsInfo> query = from(consInfo);
        BooleanBuilder builder = new BooleanBuilder();

        if (params.containsKey("title")) {
            String title = params.getFirst("title").toString();
            if (!StringUtils.isEmpty(title)) {
                builder.and(consInfo.title.contains(title));
            }
        }

        if (params.containsKey("reportTimeStart")) {
            //LocalDateTime reportTimeStart = (LocalDateTime) params.getFirst("reportTimeStart");
            LocalDateTime reportTimeStart = DateUtil.strToLocalDateTime(params.getFirst("reportTimeStart").toString());
            if (null != reportTimeStart) {
                builder.and(consInfo.reportTime.after(reportTimeStart));
            }
        }

        if (params.containsKey("reportTimeEnd")) {
            //LocalDateTime reportTimeEnd = (LocalDateTime) params.getFirst("reportTimeEnd");
            LocalDateTime reportTimeEnd = DateUtil.strToLocalDateTime(params.getFirst("reportTimeEnd").toString());
            if (null != reportTimeEnd) {
                builder.and(consInfo.reportTime.before(reportTimeEnd));
            }
        }

        if (params.containsKey("statuses")) {
            Object obj = params.get("statuses");
            List<String> statuses = (List<String>) obj;
            if (!CollectionUtils.isEmpty(statuses)) {
                builder.and(consInfo.status.in(statuses));
            }
        }
        if(params.containsKey("orgId")){
            String orgId = params.getFirst("orgId").toString();
            if(!StringUtils.isEmpty(orgId)){
                builder.and(consInfo.createOrgId.eq(orgId));
            }
        }

        builder.and(consInfo.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(
                ConsInfo.class,
                consInfo.id,
                consInfo.title,
                consInfo.infoUrl,
                consInfo.content,
                consInfo.sourceTypeName,
                consInfo.reportWay,
                consInfo.websiteName,
                consInfo.reporter,
                consInfo.reporterTel,
                consInfo.reportReason,
                consInfo.reportTime,
                consInfo.receiveOrgCode,
                consInfo.lastDealUser,
                consInfo.lastDealTime,
                consInfo.status,
                consInfo.createOrgId,
                consInfo.createOrgName,
                consInfo.createBy,
                consInfo.createTime
        )).where(builder).orderBy(consInfo.updateTime.desc());

        return findAll(query);
    }

    /**
     * 根据舆情ID数组查询舆情信息列表-不分页（交接班使用）
     */
    public List<ConsInfo> findList(List<String> infoIds) {
        QConsInfo consInfo = QConsInfo.consInfo;
        BooleanBuilder builder = new BooleanBuilder();
        if (!CollectionUtils.isEmpty(infoIds)) {
            builder.and(consInfo.id.in(infoIds));
        }
        builder.and(consInfo.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        return findAll(builder, new Sort(Sort.Direction.DESC, "updateTime"));
    }

    /**
     * 根据舆情Id和上报时间查询
     *
     * @param set_2
     * @param params
     * @param pageable
     * @return
     * @author wangjian
     * @date 2019/1/20 1:27
     */
    public Page<ConsInfo> findByInfoIdAndTime(Set<String> set_2, MultiValueMap<String, Object> params, Pageable pageable) {
        QConsInfo qConsInfo = QConsInfo.consInfo;

        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<ConsInfo> query = from(qConsInfo);

        //这个时间目前不确定，下面三行代码为有效代码
        /*LocalDateTime startTime = DateUtil.strToLocalDateTime(params.getFirst("startTime").toString());
        LocalDateTime endTime = DateUtil.strToLocalDateTime(params.getFirst("endTime").toString());
        builder.and(qConsInfo.reportTime.between(startTime,endTime));*/
        if (params.containsKey("title")) {
            builder.and(qConsInfo.title.like(params.getFirst("title").toString()));
        }
        if (set_2.size() > 0) {
            builder.and(qConsInfo.id.in(set_2));
        } else {
            builder.and(qConsInfo.id.eq("1"));
        }
        query.select(Projections.bean(
                ConsInfo.class,
                qConsInfo.id,
                qConsInfo.title,
                qConsInfo.infoUrl,
                qConsInfo.content,
                qConsInfo.sourceTypeId,
                qConsInfo.sourceTypeName,
                qConsInfo.reportWay,
                qConsInfo.websiteName,
                qConsInfo.reporter,
                qConsInfo.reporterTel,
                qConsInfo.reportReason,
                qConsInfo.reportTime,
                qConsInfo.receiveOrgCode,
                qConsInfo.lastDealUser,
                qConsInfo.lastDealTime,
                qConsInfo.status
        )).where(builder).orderBy(qConsInfo.reportTime.desc());
        return findAll(query, pageable);
    }

    /**
     * 根据舆情Id和舆情名称查询
     *
     * @param set_2
     * @param params
     * @param pageable
     * @return
     * @author wangjian
     * @date 2019/1/28 1:27
     */
    public Page<ConsInfo> findByInfoIdAndName(Set<String> set_2, MultiValueMap<String, Object> params, Pageable pageable) {
        QConsInfo qConsInfo = QConsInfo.consInfo;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<ConsInfo> query = from(qConsInfo);

        if (params.containsKey("title")) {
            String title = params.getFirst("title").toString();
            if (!StringUtils.isEmpty(title)) {
                builder.and(qConsInfo.title.contains(title));
            }
        }

        if (CollectionUtils.isEmpty(set_2)) {
            return null;
        }
        builder.and(qConsInfo.id.in(set_2));
        query.select(Projections.bean(
                ConsInfo.class,
                qConsInfo.id,
                qConsInfo.title,
                qConsInfo.infoUrl,
                qConsInfo.content,
                qConsInfo.sourceTypeId,
                qConsInfo.sourceTypeName,
                qConsInfo.reportWay,
                qConsInfo.websiteName,
                qConsInfo.reporter,
                qConsInfo.reporterTel,
                qConsInfo.reportReason,
                qConsInfo.reportTime,
                qConsInfo.receiveOrgCode,
                qConsInfo.lastDealUser,
                qConsInfo.lastDealTime,
                qConsInfo.status
        )).where(builder).orderBy(qConsInfo.reportTime.desc());
        return findAll(query, pageable);
    }


    /**
     * 根据舆情Id和舆情名称查询
     * @author wangjian
     * @date 2019/1/28 1:27
     * @param set_2
     * @param params
     * @return
     */
    public List<ConsInfo> findByInfoIdAndName(Set<String> set_2, MultiValueMap<String, Object> params) {
        QConsInfo qConsInfo = QConsInfo.consInfo;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<ConsInfo> query = from(qConsInfo);

        if(params.containsKey("title")){
            String title = params.getFirst("title").toString();
            if(!StringUtils.isEmpty(title)){
                builder.and(qConsInfo.title.contains(title));
            }
        }

        if(CollectionUtils.isEmpty(set_2)){
            return null;
        }
        builder.and(qConsInfo.id.in(set_2));
        query.select(Projections.bean(
                ConsInfo.class,
                qConsInfo.id,
                qConsInfo.title,
                qConsInfo.infoUrl,
                qConsInfo.content,
                qConsInfo.sourceTypeId,
                qConsInfo.sourceTypeName,
                qConsInfo.reportWay,
                qConsInfo.websiteName,
                qConsInfo.reporter,
                qConsInfo.reporterTel,
                qConsInfo.reportReason,
                qConsInfo.reportTime,
                qConsInfo.receiveOrgCode,
                qConsInfo.lastDealUser,
                qConsInfo.lastDealTime,
                qConsInfo.status
        )).where(builder).orderBy(qConsInfo.reportTime.desc());
        return findAll(query);
    }
}
