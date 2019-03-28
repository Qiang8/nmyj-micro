package com.taiji.base.sample.controller;

import com.taiji.base.sample.entity.Sample;
import com.taiji.base.sample.feign.ISampleRestService;
import com.taiji.base.sample.mapper.SampleMapper;
import com.taiji.base.sample.service.SampleService;
import com.taiji.base.sample.vo.SampleVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/sample")
public class SampleController extends BaseController implements ISampleRestService {
    protected static final Logger log = LoggerFactory.getLogger(SampleController.class);

    SampleService service;

    SampleMapper mapper;

    /**
     * 根据SampleVo id获取一条记录。
     *
     * @param id
     * @return ResponseEntity<SampleVo>
     */
    @Override
    public ResponseEntity<SampleVo> find(@PathVariable("id") String id) {
        Assert.hasText(id, "id不能为null!");

        Sample   entity = service.findOne(id);
        SampleVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据参数获取分页SampleVo多条记录。
     *
     * params参数key为title（可选），content（可选）。
     *
     * @param params
     * @return ResponseEntity<RestPageImpl < SampleVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<SampleVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);

        Page<Sample>            result = service.findPage(params,pageable);
        RestPageImpl<SampleVo> voPage = mapper.entityPageToVoPage(result, pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 保存SampleVo，SampleVo不能为空。
     *
     * @param vo
     * @return ResponseEntity<SampleVo>
     */
    @Override
    public ResponseEntity<SampleVo> save(@RequestBody SampleVo vo) {
        Assert.notNull(vo, "SystemOperationLogVo不能为null!");

        Sample tempEntity = mapper.voToEntity(vo);
        Sample entity = service.save(tempEntity);
        SampleVo tempVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据id删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        Assert.hasText(id, "id不能为null!");

        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
