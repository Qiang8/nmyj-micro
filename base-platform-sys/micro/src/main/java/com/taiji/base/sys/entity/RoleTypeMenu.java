package com.taiji.base.sys.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>Title:RoleTypeMenu.java</p >
 * <p>Description: 角色类型资源</p >
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
@Table(name = "SYS_ROLE_TYPE_MENU")
public class RoleTypeMenu extends IdEntity<String> {
    public RoleTypeMenu()
    {}

    @Length(max = 36, message = "菜单Id字段最大长度36")
    @NotBlank(message = "菜单Id不能为空字符串")
    @Getter
    @Setter
    private String menuId;

    @Length(max = 36, message = "角色类型menuId字段最大长度36")
    @NotBlank(message = "角色类型menuId不能为空字符串")
    @Getter
    @Setter
    private String roleType;
}
