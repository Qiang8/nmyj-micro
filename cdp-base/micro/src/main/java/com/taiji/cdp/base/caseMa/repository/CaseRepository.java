package com.taiji.cdp.base.caseMa.repository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.base.caseMa.entity.Case;
import com.taiji.cdp.base.caseMa.entity.QCase;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;


@Repository
@Transactional
public class CaseRepository extends BaseJpaRepository<Case,String> {

    /**
     * 保存、修改案例信息
     */

    @Override
    @Transactional
    public Case save(Case entity){
        Assert.notNull(entity,"Case 不能为null");
        Case result;
        if(!StringUtils.hasText(entity.getId()))
        {
            entity.setId(null);
            result = super.save(entity);
        }
        else
        {
            Case temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, temp);
            result = super.save(temp);
        }

        return result;
    }

    /**
     * 案例信息分页查询
     * @param params
     * 参数;page,size
     * 参数：title(可选)、startTimeStart(首发时间开始)、startTimeEnd(上报时间结束)、caseTypeIds(案例类型,string[])
     * @return ResponseEntity<RestPageImpl<CaseVo>>
     *     不带取证信息
     */
    public Page<Case> findPage(MultiValueMap<String, Object> params, Pageable pageable){
        QCase aCase = QCase.case$;
        JPQLQuery<Case> query = from(aCase);
        BooleanBuilder builder = new BooleanBuilder();
        if(params.containsKey("title")){
            String title = params.getFirst("title").toString();
            if(!StringUtils.isEmpty(title)){
                builder.and(aCase.title.contains(title));
            }
        }

        if(params.containsKey("startTimeStart")){
            LocalDateTime reportTimeStart = DateUtil.strToLocalDateTime(params.getFirst("startTimeStart").toString());
            if(null!=reportTimeStart){
                builder.and(aCase.createTime.after(reportTimeStart));
            }
        }

        if(params.containsKey("startTimeEnd")){
            LocalDateTime reportTimeEnd = DateUtil.strToLocalDateTime(params.getFirst("startTimeEnd").toString());
            if(null!=reportTimeEnd){
                builder.and(aCase.createTime.before(reportTimeEnd));
            }
        }
        //遍历数组查询包含某个ID的数据
        if(params.containsKey("caseTypeIds")){
            Object obj = params.get("caseTypeIds");
            List<String> caseTypeIds = (List<String>)obj;
            if(!CollectionUtils.isEmpty(caseTypeIds)){
                for(String typeId : caseTypeIds){
                    builder.or(aCase.caseTypeIds.contains(typeId));
                }
            }
        }
        //只查询未删除状态的数据
        builder.and(aCase.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(
                Case.class,
                aCase.id,
                aCase.title,
                aCase.infoId,
                aCase.startTime,
                aCase.startWebsite,
                aCase.caseTypeIds,
                aCase.caseTypeNames,
                aCase.notes,
                aCase.createBy,
                aCase.createTime,
                aCase.updateBy,
                aCase.updateTime,
                aCase.delFlag
        )).where(builder).orderBy(aCase.updateTime.desc());
        return findAll(query,pageable);
    }
}
