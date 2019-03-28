package com.taiji.base.contact.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.contact.entity.ContactMember;
import com.taiji.base.contact.entity.ContactMid;
import com.taiji.base.contact.entity.QContactMid;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * <p>Title:ContactGroupMemberRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/12/20 16:16</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@Transactional(
        readOnly = true
)
public class ContactMidRepository extends BaseJpaRepository<ContactMid,String> {

    @PersistenceContext
    protected EntityManager em;

    public List<ContactMember> findAll(String groupId,String content){
        QContactMid qContactMid = QContactMid.contactMid;

        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(content)){
            BooleanBuilder innerBuilder = new BooleanBuilder();
            innerBuilder.or(qContactMid.member.mobile.contains(content))
                    .or(qContactMid.member.name.contains(content)
                            .or(qContactMid.member.orgName.contains(content)));
            builder.and(innerBuilder);
        }

        builder.and(qContactMid.group.id.eq(groupId));

        builder.and(qContactMid.member.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        JPQLQuery<ContactMember> query = from(qContactMid);

        query.select(Projections.bean(ContactMember.class,
                qContactMid.member.id, qContactMid.member.name,qContactMid.member.avatar,qContactMid.member.orgId,
                qContactMid.member.orgName,qContactMid.member.dutyTypeId,qContactMid.member.dutyTypeName,
                qContactMid.member.titleId,qContactMid.member.titleName,qContactMid.member.sex,
                qContactMid.member.birthday,qContactMid.member.address,qContactMid.member.mobile,
                qContactMid.member.email,qContactMid.member.telephone,qContactMid.member.otherWay,
                qContactMid.member.orders,qContactMid.member.fax,qContactMid.member.notes))
                .where(builder).orderBy(qContactMid.member.createTime.desc());

        return findAll(query);
    }

    public Page<ContactMember> findPage(MultiValueMap<String, Object> params, Pageable pageable){

        QContactMid qContactMid = QContactMid.contactMid;

        String  groupId = "";
        String content = "";
        String letter = "";
        String orgCode = "";

        if(params.containsKey("content"))
        {
            content = params.getFirst("content").toString();
        }

        if(params.containsKey("letter"))
        {
            letter = params.getFirst("letter").toString();
        }

        if(params.containsKey("orgCode"))
        {
            orgCode = params.getFirst("orgCode").toString();
        }

        if(params.containsKey("groupId"))
        {
            groupId = params.getFirst("groupId").toString();
        }

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qContactMid.group.id.eq(groupId));

        if(StringUtils.hasText(content)){
            BooleanBuilder innerBuilder = new BooleanBuilder();
            innerBuilder.or(qContactMid.member.mobile.contains(content))
                    .or(qContactMid.member.name.contains(content)
                            .or(qContactMid.member.orgName.contains(content)));
            builder.and(innerBuilder);
        }

        if(StringUtils.hasText(orgCode)){
            builder.and(qContactMid.member.orgCode.startsWith(orgCode));
        }

        if(StringUtils.hasText(letter)){
            builder.and(qContactMid.member.pinyin.startsWith(letter.toLowerCase()));
        }

        builder.and(qContactMid.member.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        JPQLQuery<ContactMember> query = from(qContactMid);

        query.select(Projections.bean(ContactMember.class,
                qContactMid.member.id, qContactMid.member.name,qContactMid.member.avatar,qContactMid.member.orgId,
                qContactMid.member.orgName,qContactMid.member.dutyTypeId,qContactMid.member.dutyTypeName,
                qContactMid.member.titleId,qContactMid.member.titleName,qContactMid.member.sex,
                qContactMid.member.birthday,qContactMid.member.address,qContactMid.member.mobile,
                qContactMid.member.email,qContactMid.member.telephone,qContactMid.member.otherWay,
                qContactMid.member.orders,qContactMid.member.fax,qContactMid.member.notes))
                .where(builder).orderBy(qContactMid.member.createTime.desc());

        return findAll(query,pageable);
    }

    public List<ContactMid> findAllByGroupId(String groupId){
        QContactMid qContactMid = QContactMid.contactMid;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(groupId))
        {
            builder.and(qContactMid.group.id.eq(groupId));
        }

        return findAll(builder);
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteByGroupId(String groupId)
    {
        List<ContactMid> list = findAllByGroupId(groupId);
        deleteInBatch(list);
    }

    @Transactional(rollbackFor=Exception.class)
    public void save(List<ContactMid> list, String groupId)
    {
        deleteByGroupId(groupId);
        save(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMemberByMemberId(String memberId)
    {
        Query query = this.em.createNativeQuery("delete from dw_contact_mid where member_id = :memberId");
        query.setParameter("memberId",memberId);
        query.executeUpdate();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMemberByGroupId(String groupId)
    {
        Query query = this.em.createNativeQuery("delete from dw_contact_mid where group_id = :groupId");
        query.setParameter("groupId",groupId);
        query.executeUpdate();
    }
}
