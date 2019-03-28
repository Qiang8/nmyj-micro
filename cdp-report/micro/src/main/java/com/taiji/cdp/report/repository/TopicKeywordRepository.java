package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.entity.QTopicKeyword;
import com.taiji.cdp.report.entity.TopicKeyword;
import com.taiji.cdp.report.vo.TopicKeywordVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author sunyi
 */
@Repository
@Transactional(readOnly = true)
public class TopicKeywordRepository extends BaseJpaRepository<TopicKeyword, String> {

    /**
     * 新增专题管理关键字信息Vo
     * @param entity 新增专题管理关键字entity
     * @return TopicKeyword
     */
    @Override
    @Transactional
    public TopicKeyword save(TopicKeyword entity) {
        Assert.notNull(entity, "TopicKeyword 不能为null");
        TopicKeyword result;
        Boolean distinctBy = findDistinctBy(entity.getKeyword());
        if(!distinctBy){
           return null;
        }
        if (!StringUtils.hasText(entity.getId())) {
            entity.setId(null);
            result = super.save(entity);
        } else {
            TopicKeyword temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 查询所以
     */
    public Boolean findDistinctBy(String keyword){
        QTopicKeyword topic = QTopicKeyword.topicKeyword;
        JPQLQuery<TopicKeyword> query = from(topic);
        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(keyword)) {
            builder.and(topic.keyword.eq(keyword));
        }
        builder.and(topic.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder);
        if(CollectionUtils.isEmpty(findAll(query))){
            return true;
        }else {
            return false;
        }
    }




    /**
     * 查询所以
     */
    public List<TopicKeyword> findList(TopicKeywordVo vo){
        QTopicKeyword topic = QTopicKeyword.topicKeyword;
        JPQLQuery<TopicKeyword> query = from(topic);
        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(vo.getTopicId())) {
            builder.and(topic.topic.id.eq(vo.getTopicId()));
        }
        builder.and(topic.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(topic.id.desc());
        return findAll(query);
    }

}
