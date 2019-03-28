package com.taiji.base.contact.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * <p>Title:ContactGroup.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/12/20 15:25</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "DW_CONTACT_GROUP")
public class ContactGroup extends BaseEntity<String> implements DelFlag {

    public ContactGroup() {
    }

    /**
     * 分组名称
     */
    @Length(max = 50,message = "分组名称name字段最大长度50")
    @NotBlank(message = "分组名称不能为空字符串")
    @Getter
    @Setter
    private String name;

    /**
     * 用户Id
     */
    @Length(max = 36,message = "用户userId字段最大长度36")
    @Getter
    @Setter
    private String userId;

    /**
     * 顺序
     */
    @Getter
    @Setter
    private Integer orders;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1, message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, targetEntity = ContactMid.class,mappedBy = "group")
    private List<ContactMid> midList;
}
