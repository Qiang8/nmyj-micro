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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 系统数据字典项实体类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "SYS_DIC_GROUP_ITEMS")
public class DicGroupItem extends BaseEntity<String> implements DelFlag {

    public DicGroupItem(){}

    /**
     * 字典名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "字典名称itemName字段最大长度100")
    private String itemName;

    /**
     * 字典编码
     */
    @Getter
    @Setter
    @Length(max = 100,message = "字典编码itemCode字段最大长度100")
    private String itemCode;

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
     * 删除标志 0:正常,1:删除
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 父节点
     */
    @Getter
    @Setter
    @Length(max = 36,message = "父节点编码parentId字段最大长度36")
    private String parentId;
}
