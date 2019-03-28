package com.taiji.cdp.report.mapper;

import com.taiji.cdp.report.entity.Topic;
import com.taiji.cdp.report.entity.TopicKeyword;
import com.taiji.cdp.report.vo.TopicKeywordVo;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicKeywordMapper extends BaseMapper<TopicKeyword, TopicKeywordVo>{

    @Override
    default TopicKeyword voToEntity(TopicKeywordVo vo) {
        TopicKeyword entity = new TopicKeyword();
        Topic topic = new Topic();
        topic.setId(vo.getTopicId());
        entity.setId(vo.getId());
        entity.setKeyword(vo.getKeyword());
        entity.setTopic(topic);
        return entity;
    }
    @Override
    default TopicKeywordVo entityToVo(TopicKeyword entity) {
        if(entity==null){
            return null;
        }
        TopicKeywordVo vo = new TopicKeywordVo();
        vo.setId(vo.getId());
        vo.setKeyword(entity.getKeyword());
        vo.setTopicId(entity.getTopic().getId());
        return vo;
    }
    @Override
    default List<TopicKeywordVo> entityListToVoList(List<TopicKeyword> list) {
        if(list==null){
            return null;
        }
        List<TopicKeywordVo> voList = new ArrayList<>();
        for (TopicKeyword entity : list){
            TopicKeywordVo vo = new TopicKeywordVo();
            vo.setId(entity.getId());
            vo.setKeyword(entity.getKeyword());
            vo.setTopicId(entity.getTopic().getId());
            vo.setTopicName(entity.getTopic().getName());
            voList.add(vo);
        }
        return voList;
    }
}
