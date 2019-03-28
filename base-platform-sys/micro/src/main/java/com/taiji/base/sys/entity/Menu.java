package com.taiji.base.sys.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 资源菜单实体类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "SYS_MENU")
public class Menu extends BaseEntity<String> implements DelFlag {
    public Menu(){}

    /**
     * 父节点ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "父节点编码parentId字段最大长度36")
    private String parentId;

    /**
     * 菜单名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "菜单名称menuName字段最大长度50")
    private String menuName;

    /**
     * 权限标识
     */
    @Getter
    @Setter
    @Length(max = 50,message = "权限标识permission字段最大长度50")
    private String permission;

    /**
     * 路径
     */
    @Getter
    @Setter
    @Length(max = 100,message = "路径path字段最大长度100")
    private String path;

    /**
     * 路由名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "路由名称routerName字段最大长度100")
    private String routerName;

    /**
     * 重定向
     */
    @Getter
    @Setter
    @Length(max = 100,message = "重定向路径redirect字段最大长度100")
    private String redirect;

    /**
     * 图标
     */
    @Getter
    @Setter
    @Length(max = 50,message = "图标icon字段最大长度50")
    private String icon;

    /**
     * 组件
     */
    @Getter
    @Setter
    @Length(max = 100,message = "组件component字段最大长度100")
    private String component;

    /**
     * 排序
     */
    @Getter
    @Setter
    private Integer orders;


    /**
     * 删除标志 0:正常,1:删除
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 菜单类型 0:菜单,1:按钮
     */
    @Getter
    @Setter
    @Length(max = 1,message = "类型标识type字段最大长度1")
    private String type;

    /**
     * 是否隐藏，默认false
     */
    @Getter
    @Setter
    @NotNull
    private Boolean hidden;

    /**
     * 是否显示，默认false
     */
    @Getter
    @Setter
    @NotNull
    private Boolean alwaysShow;
}
