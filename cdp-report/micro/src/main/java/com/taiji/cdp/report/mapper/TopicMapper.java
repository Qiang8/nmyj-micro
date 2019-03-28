package com.taiji.cdp.report.mapper;

import com.taiji.cdp.report.entity.Topic;
import com.taiji.cdp.report.entity.TopicCons;
import com.taiji.cdp.report.vo.TopicVo;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper extends BaseMapper<Topic, TopicVo>{

    default List<TopicVo> topicConsListToVoList(List<TopicCons> entityList)
    {
        if ( entityList == null) {
            return null;
        }


        List<TopicVo> list = new ArrayList<>(entityList.size());

        for ( TopicCons entity : entityList ) {
            Topic topic = entity.getTopic();
            list.add( entityToVo(topic) );
        }

        return list;
    }

}
