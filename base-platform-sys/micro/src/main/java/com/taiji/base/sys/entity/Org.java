package com.taiji.base.sys.entity;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 组织机构实体类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "SYS_ORG")
public class Org extends BaseEntity<String> implements DelFlag {

    public Org(){}

    /**
     * 机构名称
     */
    @Getter
    @Setter
    @NotBlank
    @Length(max = 100,message = "机构名称orgName字段最大长度100")
    private String orgName;


    /**
     * 机构编码
     */
    @Getter
    @Setter
    @Length(max = 30,message = "机构编码orgCode字段最大长度30")
    private String orgCode;

    /**
     * 机构地址
     */
    @Getter
    @Setter
    @Length(max = 100,message = "机构地址address字段最大长度100")
    private String address;

    /**
     * 排序
     */
    @Getter
    @Setter
    private Integer orders;

    /**
     * 机构描述
     */
    @Getter
    @Setter
    @Length(max = 200,message = "机构描述description字段最大长度200")
    private String description;

    /**
     * 机构简称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "机构简称shortName字段最大长度50")
    private String shortName;

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

    /**
     * 层级
     */
    @Getter
    @Setter
    @Length(max = 36,message = "层级orgLevelId字段最大长度36")
    private String orgLevelId;

}
