package com.taiji.base.sys.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.taiji.base.sys.entity.Org;
import com.taiji.base.sys.entity.QOrg;
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
 * 系统组织机构Repository类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Repository
public class OrgRepository extends BaseJpaRepository<Org,String>{

    public List<Org> findAll(String parentId,String orgName)
    {
        QOrg qOrg = QOrg.org;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(parentId))
        {
            builder.and(qOrg.parentId.eq(parentId));
        }

        if(StringUtils.hasText(orgName))
        {
            builder.and(qOrg.orgName.eq(orgName));
        }

        builder.and(qOrg.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder);
    }

    @Override
    @Transactional
    public synchronized Org save(Org entity)
    {
        Assert.notNull(entity, "role must not be null!");

        Org result;
        if(entity.getId() == null)
        {
            String parentId = entity.getParentId();

            Org parentOrg = findOne(parentId);

            String parentOrgOrgCode;
            if(null == parentOrg)
            {
                parentOrgOrgCode = "";
            }
            else
            {
                parentOrgOrgCode = parentOrg.getOrgCode();
            }

            Org lastChildOrg = findByParentIdForLastChild(parentId);
            if(null == lastChildOrg)
            {
                String orgCode = parentOrgOrgCode + "100";
                entity.setOrgCode(orgCode);
                result = super.save(entity);
            }
            else
            {
                String orgCode = lastChildOrg.getOrgCode();
                String lastSeq = orgCode.substring(orgCode.length() - 3);
                Integer lastSeqNum = Integer.valueOf(lastSeq);
                if(lastSeqNum == 999)
                {
                    throw new RuntimeException("组织机构的子节点最多999个！");
                }
                else
                {
                    String newOrgCode;
                    if("".equals(parentOrgOrgCode))
                    {
                        newOrgCode = String.valueOf(lastSeqNum + 1);
                    }
                    else
                    {
                        newOrgCode =String.valueOf(parentOrgOrgCode) + String.valueOf(lastSeqNum + 1);
                    }

                    entity.setOrgCode(newOrgCode.toString());
                    result = super.save(entity);
                }
            }
        }
        else
        {
            Org tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }

    @Transactional
    @Override
    public Org deleteLogic(Org entity, DelFlagEnum delFlagEnum)
    {
        QOrg qOrg = QOrg.org;

        JPAUpdateClause updateClause = querydslUpdate(qOrg);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOrg.orgCode.startsWith(entity.getOrgCode()));
        builder.and(qOrg.delFlag.eq(DelFlagEnum.NORMAL.getCode())); //未删除的

        updateClause.set(qOrg.delFlag,delFlagEnum.getCode()).where(builder).execute();

        entity = findOne(entity.getId());

        return entity;
    }

    private Org findByParentIdForLastChild(String parentId)
    {
        QOrg qOrg = QOrg.org;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOrg.parentId.eq(parentId));
        builder.and(qOrg.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        JPQLQuery<Org> query = from(qOrg);
        query.where(builder);
        query.orderBy(qOrg.orgCode.desc());
        query.limit(1);

        return query.fetchFirst();
    }

    public List<Org> findOrgInfo(List<String> orgIds) {
        QOrg org = QOrg.org;
        JPQLQuery<Org> query = from(org);
        BooleanBuilder builder = new BooleanBuilder();
        if (!CollectionUtils.isEmpty(orgIds)){
            builder.and(org.id.in(orgIds));
        }
        builder.and(org.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(Org.class
                ,org.id
                ,org.orgName
                ,org.orgLevelId
        )).where(builder);
        return findAll(query);
    }
}
