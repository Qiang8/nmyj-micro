package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.entity.Topic;
import com.taiji.cdp.report.feign.ITopicService;
import com.taiji.cdp.report.mapper.TopicMapper;
import com.taiji.cdp.report.service.TopicService;
import com.taiji.cdp.report.vo.TopicVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 专题管理信息接口实现类controller
 * @author sunyi
 * @date 2019年1月17日
 */
@Slf4j
@RestController
@RequestMapping("/api/topics")
@AllArgsConstructor
public class TopicController implements ITopicService {

    TopicService service;
    TopicMapper mapper;

    /**
     * 新增专题管理
     * @param vo 新增专题管理vo
     * @return ResponseEntity<TopicVo>
     */
    @Override
    public ResponseEntity<TopicVo> create(
            @Validated
            @NotNull(message = "TopicVo 不能为null")
            @RequestBody TopicVo vo) {
        Topic entity = mapper.voToEntity(vo);
        Topic result = service.create(entity);
        TopicVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新专题管理Vo
     * @param vo 专题管理vo
     * @param id 专题管理Id
     * @return ResponseEntity<TopicVo>
     */
    @Override
    public ResponseEntity<TopicVo> update(
            @Validated
            @NotNull(message = "TopicVo 不能为null")
            @RequestBody TopicVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        Topic entity = mapper.voToEntity(vo);
        Topic result = service.update(entity,id);
        TopicVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取单条专题管理信息Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<TopicVo>
     */
    @Override
    public ResponseEntity<TopicVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        Topic result = service.findOne(id);
        TopicVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id逻辑删除单条专题管理信息Vo
     * @param id 信息Id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取所以专题管理信息
     * @return ResponseEntity<TopicVo>
     */
    @Override
    public ResponseEntity<List<TopicVo>> findList() {
        return ResponseEntity.ok(mapper.entityListToVoList(service.findList()));
    }

}
