package com.taiji.cdp.cmd.controller;

import com.taiji.cdp.cmd.entity.Feedback;
import com.taiji.cdp.cmd.feign.IFeedbackService;
import com.taiji.cdp.cmd.mapper.FeedbackMapper;
import com.taiji.cdp.cmd.service.FeedbackService;
import com.taiji.cdp.cmd.vo.FeedbackVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 处置反馈息接口实现类controller
 * @author sunyi
 * @date 2019年2月20日
 */
@Slf4j
@RestController
@RequestMapping("/api/feedback")
@AllArgsConstructor
public class FeedbackController implements IFeedbackService {

    FeedbackService service;
    FeedbackMapper mapper;

    /**
     * 新增处置反馈
     * @param vo 新增处置反馈vo
     * @return ResponseEntity<FeedbackVo>
     */
    @Override
    public ResponseEntity<FeedbackVo> create(
            @Validated
            @NotNull(message = "FeedbackVo 不能为null")
            @RequestBody FeedbackVo vo) {
        Feedback entity = mapper.voToEntity(vo);
        Feedback result = service.create(entity);
        FeedbackVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }


    /**
     * 根据id获取单条处置反馈信息Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<FeedbackVo>
     */
    @Override
    public ResponseEntity<FeedbackVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        Feedback result = service.findOne(id);
        FeedbackVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }


    /**
     * 获取所以处置反馈信息
     * @return ResponseEntity<FeedbackVo>
     */
    @Override
    public ResponseEntity<List<FeedbackVo>> findList() {
        return ResponseEntity.ok(mapper.entityListToVoList(service.findList()));
    }

}
