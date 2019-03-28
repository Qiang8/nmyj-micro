package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.entity.ConsInfo;
import com.taiji.cdp.report.entity.QTopicCons;
import com.taiji.cdp.report.entity.Topic;
import com.taiji.cdp.report.entity.TopicCons;
import com.taiji.cdp.report.vo.TopicConsVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Repository
@Transactional
public class TopicConsRepository extends BaseJpaRepository<TopicCons,String> {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    ConsInfoRepository consInfoRepository;

    @Transactional
    public TopicCons create(TopicConsVo vo){
        String infoId = vo.getInfoId();
        Assert.hasText(infoId,"infoId 不能为空字符");
        String topicId = vo.getTopicId();
        Assert.hasText(topicId,"topicId 不能为空字符");
        TopicCons oldResult = findTopicConsByInfoIdAndTopicId(infoId,topicId); //避免重复入库
        if(null==oldResult){
            TopicCons entity = new TopicCons();
            ConsInfo info = consInfoRepository.findOne(infoId);
            Topic topic = topicRepository.findOne(topicId);
            entity.setConsInfo(info);
            entity.setTopic(topic);
            entity.setTopicName(topic.getName());
            return super.save(entity);
        }else{
            return oldResult;
        }
    }

    private TopicCons findTopicConsByInfoIdAndTopicId(String infoId,String topicId){
        QTopicCons topicCons = QTopicCons.topicCons;
        JPQLQuery<TopicCons> query = from(topicCons);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(topicCons.consInfo.id.eq(infoId));
        builder.and(topicCons.topic.id.eq(topicId));
        query.where(builder);
        List<TopicCons> list = findAll(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     *  根据重点专题ID查询舆情信息列表-分页（重点专题使用）
     *  @param params 参数：topicId,page,size
     */
    public Page<TopicCons> findPageByTopic(MultiValueMap<String, Object> params, Pageable pageable){
        if(!params.containsKey("topicId")){
            return null;
        }
        String topicId = params.getFirst("topicId").toString();
        Assert.hasText(topicId,"topicId 不能为空");
        QTopicCons topicCons = QTopicCons.topicCons;
        JPQLQuery<TopicCons> query = from(topicCons);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(topicCons.topic.id.eq(topicId));
        builder.and(topicCons.consInfo.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(
                TopicCons.class,
                topicCons.consInfo
        )).where(builder).orderBy(topicCons.consInfo.createTime.desc());
        return findAll(query,pageable);
    }

    /**
     * 根据舆情信息查询重点专题List-不分页
     * @param params 参数：infoId
     */
    public List<TopicCons> findListByInfo(MultiValueMap<String, Object> params){
        if(!params.containsKey("infoId")){
            return null;
        }
        String infoId = params.getFirst("infoId").toString();
        Assert.hasText(infoId,"topicId 不能为空");
        QTopicCons topicCons = QTopicCons.topicCons;
        JPQLQuery<TopicCons> query = from(topicCons);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(topicCons.consInfo.id.eq(infoId));
        builder.and(topicCons.topic.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(
                TopicCons.class,
                topicCons.topic
        )).where(builder).orderBy(topicCons.topic.createTime.desc());
        return findAll(query);
    }

}
