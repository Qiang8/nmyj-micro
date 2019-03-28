package com.taiji.cdp.daily.daily.entity;

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
 * 日报管理实体对象
 * @author sunyi
 * @date 2019年1月18日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "WB_DAILY")
public class Daily extends BaseEntity<String> implements DelFlag{

    public Daily(){}

    /**
     * 日报标题
     */
    @Getter
    @Setter
    @Length(max = 200,message = "日报标题 title 最大长度不能超过200")
    @NotEmpty(message = "日报标题 title 不能为空字符串")
    private String title;


    /**
     * 日报状态,0为发布，1发布
     */
    @Getter
    @Setter
    @Length(max = 1,message = "日报状态 status 最大长度不能超过1")
    private String status;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
