package com.taiji.base.sample.service;

import com.taiji.base.sample.entity.Sample;
import com.taiji.base.sample.repository.SampleRepository;
import com.taiji.micro.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
@Service
public class SampleService extends BaseService<Sample,String> {

    @Autowired
    private SampleRepository repository;

    public SampleService()
    {}


    public Page<Sample> findByTitleAndContent(String title,String content,Pageable pageable)
    {
        Page<Sample> result = repository.findPage(title, content, pageable);

        return result;
    }

    public Page<Sample> findPage(MultiValueMap<String, Object> params, Pageable pageable)
    {
        Page<Sample> result = repository.findPage(params, pageable);

        return result;
    }

    public Sample findOne(String id)
    {
        Sample result = repository.findOne(id);

        return result;
    }

    public Sample save(Sample sample)
    {
        Sample result = repository.save(sample);

        return result;
    }

    public void delete(String id)
    {
        repository.delete(id);
    }

    public List<Sample> findAll()
    {
        List<Sample> result = repository.findAll();

        return result;
    }


}
