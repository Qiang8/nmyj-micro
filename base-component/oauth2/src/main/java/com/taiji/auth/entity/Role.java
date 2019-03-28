package com.taiji.auth.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

/**
 * 系统角色实体类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Entity
@Table(name = "SYS_ROLE")
public class Role extends BaseEntity<String> implements DelFlag {

    public Role(){}

    /**
     * 角色名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "角色名称roleName字段最大长度50")
    private String  roleName;

    /**
     * 角色编码
     */
    @Getter
    @Setter
    @Length(max = 50,message = "角色编码roleCode字段最大长度50")
    private String roleCode;

    /**
     * 角色类型
     */
    @Getter
    @Setter
    @Length(max = 36,message = "角色类型roleType字段最大长度36")
    private String roleType;

    /**
     * 角色默认页面
     */
    @Getter
    @Setter
    @Length(max = 100,message = "角色默认页面homePage字段最大长度100")
    private String homePage;

    /**
     * 角色描述
     */
    @Getter
    @Setter
    @Length(max = 500,message = "角色描述description字段最大长度500")
    private String description;

    /**
     * 排序
     */
    @Getter
    @Setter
    private Integer orders;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 禁用标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "禁用标识status字段最大长度1")
    private String status;

    /**
     * 是否超级角色
     */
    @Getter
    @Setter
    private Boolean isSuper;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_ROLE_MENUS", joinColumns = {@JoinColumn(name = "ROLE_ID", updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "MENU_ID", updatable = false)})
    @OrderBy(value = "orders asc")
    private List<Menu> menuList;
}
