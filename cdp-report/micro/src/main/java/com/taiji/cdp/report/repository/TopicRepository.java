package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.entity.QTopic;
import com.taiji.cdp.report.entity.Topic;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author sunyi
 */
@Repository
@Transactional(readOnly = true)
public class TopicRepository extends BaseJpaRepository<Topic, String> {

    /**
     * 新增专题管理信息Vo
     *
     * @param entity 新增专题管理entity
     * @return Topic
     */
    @Override
    @Transactional
    public Topic save(Topic entity) {
        Assert.notNull(entity, "Topic 不能为null");
        Topic result;
        if (!StringUtils.hasText(entity.getId())) {
            entity.setId(null);
            result = super.save(entity);
        } else {
            Topic temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, temp);
            result = super.save(temp);
        }
        return result;
    }


    /**
     * 查询所以
     */
    public List<Topic> findListAll(){
        QTopic topic = QTopic.topic;
        JPQLQuery<Topic> query = from(topic);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(topic.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(topic.updateTime.desc());
        return findAll(query);
    }

}
