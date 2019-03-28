package com.taiji.base.sample.mapper;

import com.taiji.base.sample.entity.Sample;
import com.taiji.base.sample.vo.SampleVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author scl
 *
 * @date 2018-03-19
 */
@Mapper(componentModel = "spring")
public interface SampleMapper {
    SampleVo entityToVo(Sample sample);
    Sample voToEntity(SampleVo sampleVo);

    default RestPageImpl<SampleVo> entityPageToVoPage(Page<Sample> entityPage,Pageable page)
    {
        if ( entityPage == null || page == null) {
            return null;
        }

        List<Sample> content = entityPage.getContent();

        List<SampleVo> list = new ArrayList<SampleVo>(content.size());

        for ( Sample entity : content ) {
            list.add( entityToVo(entity) );
        }

        RestPageImpl<SampleVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;
    }
}
