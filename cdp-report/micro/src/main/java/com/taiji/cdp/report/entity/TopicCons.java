package com.taiji.cdp.report.entity;

import com.taiji.micro.common.entity.IdEntity;
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
 * 专题与舆情信息中间表
 * @author qizhijie-pc
 * @date 2019年1月22日10:18:37
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CJ_CONS_TOPIC")
public class TopicCons extends IdEntity<String>{

    public TopicCons(){}

    /**
     * 舆情ID
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = ConsInfo.class)
    @JoinColumn(name = "info_id")
    private ConsInfo consInfo;

    /**
     * 专题ID
     */
    @Getter@Setter
    @ManyToOne(targetEntity = Topic.class)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    /**
     * 专题名称
     */
    @Getter@Setter
    @Length(max = 200,message = "专题名称 topicName 最大长度不能超过36个字符")
    private String topicName;

}
