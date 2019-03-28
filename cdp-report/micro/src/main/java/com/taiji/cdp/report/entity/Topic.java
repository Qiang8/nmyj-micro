package com.taiji.cdp.report.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 专题管理实体对象
 * @author sunyi
 * @date 2019年1月17日11:30:57
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CJ_TOPIC")
public class Topic extends BaseEntity<String> implements DelFlag{

    public Topic(){}

    /**
     * 专题管理名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "专题管理名称 name 最大长度不能超过50")
    @NotEmpty(message = "专题管理名称 name 不能为空字符串")
    private String name;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
