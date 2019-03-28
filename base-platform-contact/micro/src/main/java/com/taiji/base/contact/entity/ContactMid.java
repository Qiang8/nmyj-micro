package com.taiji.base.contact.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <p>Title:ContactMid.java</p >
 * <p>Description: 通讯录人员关联表</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 15:07</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "DW_CONTACT_MID")
public class ContactMid extends IdEntity<String> {
    public ContactMid()
    {}

    @Getter
    @Setter
    @ManyToOne(targetEntity = ContactMember.class)
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "id")
    private ContactMember member;

    @Getter
    @Setter
    @ManyToOne(targetEntity = ContactGroup.class)
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "id")
    private ContactGroup group;
}
