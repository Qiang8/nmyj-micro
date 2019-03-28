package com.taiji.base.sample.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.sample.entity.QSample;
import com.taiji.base.sample.entity.Sample;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
@Repository
public class SampleRepository extends BaseJpaRepository<Sample,String> {

    public Page<Sample> findPage(String title, String content, Pageable pageable){
        QSample        sample  = QSample.sample;
        BooleanBuilder builder = new BooleanBuilder();
        if(title != null)
        {
            builder.and(sample.title.contains(title));
        }

        if(content != null)
        {
            builder.and(sample.content.contains(content));
        }
        return findAll(builder,pageable);
    }

    public Page<Sample> findPage(MultiValueMap<String, Object> params, Pageable pageable){
        QSample sample = QSample.sample;

        JPQLQuery<Sample> query = from(sample);

        BooleanBuilder builder = new BooleanBuilder();
        if(params.containsKey("title"))
        {
            builder.and(sample.title.contains(params.getFirst("title").toString()));
        }

        if(params.containsKey("content"))
        {
            builder.and(sample.content.contains(params.getFirst("content").toString()));
        }

        query.select(Projections.bean(Sample.class,sample.id, sample.title,sample.content,sample.createTime)).where(builder);


        return findAll(query,pageable);
    }



    public List<Sample> findByContent(String content){
        Assert.hasText(content, "content不能为null或空字符串!");

        QSample sample = QSample.sample;
        return findAll(sample.content.contains(content));
    }


    public Page<Sample> findByContent( @Nonnull String content, @Nonnull Pageable pageable){
        QSample sample = QSample.sample;
        return findAll(sample.content.contains(content),pageable);
    }

    @Override
    @Transactional
    public Sample save(Sample sample)
    {
        Assert.notNull(sample, "sample must not be null!");

        Sample result;
        if(!StringUtils.hasText(sample.getId()))
        {
            sample.setId(null);
            result = super.save(sample);
        }
        else
        {
            Sample tempSample = findOne(sample.getId());
            BeanUtils.copyNonNullProperties(sample, tempSample);
            result = super.save(tempSample);
        }

        return result;
    }
}
