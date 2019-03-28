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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 专题管理关键字实体对象
 * @author sunyi
 * @date 2019年1月17日11:30:57
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CJ_TOPIC_KEYWORD")
public class TopicKeyword extends BaseEntity<String> implements DelFlag{

    public TopicKeyword(){}

    /**
     * 专题ID
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = Topic.class)
    @JoinColumn(name = "TOPIC_ID", referencedColumnName = "ID")
    private Topic topic;


    /**
     * 专题关键词
     */
    @Getter
    @Setter
    @Length(max = 1000,message = "专题关键词 keyword 最大长度不能超过1000")
    @NotEmpty(message = "专题关键词 keyword 不能为空字符串")
    private String keyword;


    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
