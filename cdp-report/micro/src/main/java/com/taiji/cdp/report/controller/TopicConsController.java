package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.entity.ConsInfo;
import com.taiji.cdp.report.entity.Topic;
import com.taiji.cdp.report.entity.TopicCons;
import com.taiji.cdp.report.feign.ITopicConsRestService;
import com.taiji.cdp.report.mapper.ConsInfoMapper;
import com.taiji.cdp.report.mapper.TopicMapper;
import com.taiji.cdp.report.service.TopicConsService;
import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.cdp.report.vo.TopicConsVo;
import com.taiji.cdp.report.vo.TopicVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 舆情信息接口实现类controller
 * @author qizhijie-pc
 * @date 2019年1月8日17:25:02
 */
@Slf4j
@RestController
@RequestMapping("/api/topic/cons")
@AllArgsConstructor
public class TopicConsController implements ITopicConsRestService{

    TopicConsService service;
    ConsInfoMapper consInfoMapper;
    TopicMapper topicMapper;

    /**
     * 新增舆情、专题中间表记录
     *
     * @param vo 中间表记录vo
     * @return ResponseEntity<TopicConsVo>
     */
    @Override
    public ResponseEntity<TopicConsVo> createOne(
            @Validated
            @NotNull(message = "TopicConsVo 不能为null")
            @RequestBody TopicConsVo vo) {
        TopicCons result = service.createOne(vo);
        Topic topic = result.getTopic();
        TopicConsVo temp = new TopicConsVo(result.getId(),result.getConsInfo().getId(),topic.getId(),topic.getName());
        return ResponseEntity.ok(temp);
    }

    /**
     * 批量新增舆情、专题中间表记录
     *
     * @param vos 中间表记录vo数组
     * @return ResponseEntity<List < TopicConsVo>>
     */
    @Override
    public ResponseEntity<List<TopicConsVo>> createList(
            @NotNull(message = "vos 不能为null")
            @RequestBody List<TopicConsVo> vos) {
        List<TopicConsVo> result = service.createList(vos);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据重点专题ID查询舆情信息列表-分页（重点专题使用）
     *
     * @param params 参数：topicId,page,size
     */
    @Override
    public ResponseEntity<RestPageImpl<ConsInfoVo>> findPageByTopic(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<TopicCons> pageResult = service.findPageByTopic(params,pageable);
        RestPageImpl<ConsInfoVo> pageVo = consInfoMapper.topicConsPageToConsInfoVoPage(pageResult,pageable); //新增方法
        return ResponseEntity.ok(pageVo);
    }

    /**
     * 根据舆情信息查询重点专题List-不分页
     *
     * @param params 参数：infoId
     */
    @Override
    public ResponseEntity<List<TopicVo>> findListByInfo(@RequestParam MultiValueMap<String, Object> params) {
        List<TopicCons> listResult = service.findListByInfo(params);
        List<TopicVo> voList= topicMapper.topicConsListToVoList(listResult);
        return ResponseEntity.ok(voList);
    }

}
