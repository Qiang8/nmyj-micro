package com.taiji.base.sys.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统字典项实体类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "SYS_DIC_GROUP")
public class DicGroup extends BaseEntity<String> implements DelFlag {

    public DicGroup(){}

    /**
     * 类别名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "类别名称dicName字段最大长度50")
    private String dicName;

    /**
     * 类别编码
     */
    @Getter
    @Setter
    @Length(max = 50,message = "类别名称dicCode字段最大长度50")
    private String dicCode;

    /**
     * 类型 0:列表，1:树型
     */
    @Getter
    @Setter
    @Length(max = 1,message = "类型type字段最大长度1")
    private String type;

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
     * 禁用标志 0:禁用,1:启用
     */
    @Getter
    @Setter
    @Length(max = 1,message = "禁用标识status字段最大长度1")
    private String status;
}
