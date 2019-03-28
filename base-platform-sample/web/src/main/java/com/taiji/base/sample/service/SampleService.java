package com.taiji.base.sample.service;

import com.taiji.base.sample.feign.SampleClient;
import com.taiji.base.sample.vo.SampleVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

/**
 * @author scl
 * @date 2018-03-18
 */
@Service
public class SampleService {

    @Autowired
    SampleClient client;

    /**
     * 根据SampleVo id获取一条记录。
     *
     * @param id
     * @return ResponseEntity<SampleVo>
     */
    public SampleVo find(String id)
    {
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<SampleVo> resultVo = client.find(id);
        return resultVo.getBody();
    }

    /**
     * 根据参数获取分页SampleVo多条记录。
     *
     * params参数key为title（可选），content（可选）。
     *
     * @param params
     * @return ResponseEntity<RestPageImpl<SampleVo>>
     */
    public RestPageImpl<SampleVo> findPage(MultiValueMap<String, Object> params)
    {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<RestPageImpl<SampleVo>> resultVo = client.findPage(params);
        return resultVo.getBody();
    }


    /**
     * 保存SampleVo，SampleVo不能为空。
     *
     * @param vo
     * @return ResponseEntity<SampleVo>
     */
    public SampleVo save(SampleVo vo){
        Assert.notNull(vo, "SampleVo不能为null值");
        ResponseEntity<SampleVo> resultVo = client.save(vo);
        return resultVo.getBody();
    }


    /**
     * 根据id删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    public void delete(String id)
    {
        Assert.hasText(id, "id不能为null值或空字符串。");

        client.delete(id);
    }
}
