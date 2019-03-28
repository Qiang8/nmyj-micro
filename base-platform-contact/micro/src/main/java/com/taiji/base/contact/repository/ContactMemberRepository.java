package com.taiji.base.contact.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.base.contact.entity.ContactMember;
import com.taiji.base.contact.entity.QContactMember;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:ContactMemberRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/12/20 16:17</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@Transactional(
        readOnly = true
)
public class ContactMemberRepository extends BaseJpaRepository<ContactMember,String> {

    public List<ContactMember> findAll(String content){
        QContactMember qContactMember = QContactMember.contactMember;

        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(content)){
            BooleanBuilder innerBuilder = new BooleanBuilder();
            innerBuilder.or(qContactMember.mobile.contains(content))
                    .or(qContactMember.name.contains(content)
                    .or(qContactMember.orgName.contains(content)));
            builder.and(innerBuilder);
        }

        builder.and(qContactMember.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder);
    }

    public Page<ContactMember> findPage(MultiValueMap<String, Object> params, Pageable pageable){
        QContactMember qContactMember = QContactMember.contactMember;

        String content = "";
        String letter = "";
        String orgCode = "";
        String name = "";

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

        if(params.containsKey("name"))
        {
            name = params.getFirst("name").toString();
        }

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(name)){
            builder.and(qContactMember.name.contains(name));
        }

        if(StringUtils.hasText(content)){
            BooleanBuilder innerBuilder = new BooleanBuilder();
            innerBuilder.or(qContactMember.mobile.contains(content))
                    .or(qContactMember.name.contains(content))
                    .or(qContactMember.orgName.contains(content));
            builder.and(innerBuilder);
        }

        if(StringUtils.hasText(orgCode)){
            builder.and(qContactMember.orgCode.startsWith(orgCode));
        }

        if(StringUtils.hasText(letter)){
            builder.and(qContactMember.pinyin.startsWith(letter.toLowerCase()));
        }

        builder.and(qContactMember.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder,pageable);
    }

    @Override
    @Transactional
    public ContactMember save(ContactMember entity)
    {
        Assert.notNull(entity, "contactMember must not be null!");

        ContactMember result;
        if(entity.getId() == null)
        {
            result = super.save(entity);
        }
        else
        {
            ContactMember tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }

}
