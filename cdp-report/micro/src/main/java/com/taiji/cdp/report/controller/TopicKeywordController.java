package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.common.global.TopicKeywordGlobal;
import com.taiji.cdp.report.entity.TopicKeyword;
import com.taiji.cdp.report.feign.ITopicKeywordsService;
import com.taiji.cdp.report.mapper.TopicKeywordMapper;
import com.taiji.cdp.report.service.TopicKeywordService;
import com.taiji.cdp.report.vo.TopicKeywordVo;
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
 * 专题管理关键字信息接口实现类controller
 * @author sunyi
 * @date 2019年1月8日
 */
@Slf4j
@RestController
@RequestMapping("/api/topicKeywords")
@AllArgsConstructor
public class TopicKeywordController implements ITopicKeywordsService {

    TopicKeywordService service;
    TopicKeywordMapper mapper;

    /**
     * 新增专题管理关键字
     * @param vo 新增专题管理关键字vo
     * @return ResponseEntity<TopicKeywordVo>
     */
    @Override
    public ResponseEntity<TopicKeywordVo> create(
            @Validated
            @NotNull(message = "TopicKeywordVo 不能为null")
            @RequestBody TopicKeywordVo vo) {
        TopicKeyword entity = mapper.voToEntity(vo);
        TopicKeyword result = service.create(entity);
        TopicKeywordVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新专题管理关键字Vo
     * @param vo 专题管理关键字vo
     * @param id 专题管理关键字Id
     * @return ResponseEntity<TopicKeywordVo>
     */
    @Override
    public ResponseEntity<TopicKeywordVo> update(
            @Validated
            @NotNull(message = "TopicKeywordVo 不能为null")
            @RequestBody TopicKeywordVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        TopicKeyword entity = mapper.voToEntity(vo);
        TopicKeyword result = service.update(entity,id);
        TopicKeywordVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取单条专题管理关键字信息Vo
     * @param id 信息Id
     * @return ResponseEntity<TopicKeywordVo>
     */
    @Override
    public ResponseEntity<TopicKeywordVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        TopicKeyword result = service.findOne(id);
        TopicKeywordVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id逻辑删除单条专题管理信息关键字Vo
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
     *  根据topicId 获取所以专题管理关键字信息
     * @return ResponseEntity<TopicKeywordVo>
     */
    @Override
    public ResponseEntity<List<TopicKeywordVo>> findList(@RequestParam("topicId")String topicId) {
        TopicKeywordVo vo = new TopicKeywordVo();
        if(!TopicKeywordGlobal.TOPIC_KEY_WORD_SELECT_ALL.equals(topicId)){
            vo.setTopicId(topicId);
        }
        List<TopicKeywordVo> topicKeywordVos = mapper.entityListToVoList(service.findList(vo));
        return ResponseEntity.ok(topicKeywordVos);
    }

}
