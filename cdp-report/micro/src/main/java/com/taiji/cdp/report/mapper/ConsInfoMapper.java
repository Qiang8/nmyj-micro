package com.taiji.cdp.report.mapper;

import com.taiji.cdp.report.entity.ConsInfo;
import com.taiji.cdp.report.entity.TopicCons;
import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsInfoMapper extends BaseMapper<ConsInfo,ConsInfoVo>{

    default RestPageImpl<ConsInfoVo> topicConsPageToConsInfoVoPage(Page<TopicCons> entityPage, Pageable page)
    {
        if ( entityPage == null || page == null) {
            return null;
        }

        List<TopicCons> content = entityPage.getContent();

        List<ConsInfoVo> list = new ArrayList<ConsInfoVo>(content.size());

        for ( TopicCons entity : content ) {
            ConsInfo consInfo = entity.getConsInfo();
            list.add( entityToVo(consInfo) );
        }

        RestPageImpl<ConsInfoVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;
    }

}
