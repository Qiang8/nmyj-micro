package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.entity.ConsJudgeInfo;
import com.taiji.cdp.report.feign.IConsJudgeRestService;
import com.taiji.cdp.report.mapper.ConsJudgeMapper;
import com.taiji.cdp.report.service.ConsJudgeService;
import com.taiji.cdp.report.vo.ConsJudgeVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 信息研判接口实现类controller
 *
 * @author xuweikai-pc
 * @date 2019年1月12日17:25:02
 */
@Slf4j
@RestController
@RequestMapping("/api/judge")
@AllArgsConstructor
public class ConsJudgeController implements IConsJudgeRestService {

    ConsJudgeService service;
    ConsJudgeMapper mapper;

    /**
     * 新增研判信息Vo
     *
     * @param vo 新增研判信息vo
     * @return ResponseEntity<ConsJudgeVo>
     */
    @Override
    public ResponseEntity<ConsJudgeVo> create(
            @Validated
            @NotNull(message = "ConsJudgeVo 不能为null")
            @RequestBody ConsJudgeVo vo) {
        ConsJudgeInfo entity = mapper.voToEntity(vo);
        ConsJudgeInfo result = service.create(entity);
        ConsJudgeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新研判信息Vo
     *
     * @param vo 研判信息vo
     * @param id 信息Id
     * @return ResponseEntity<ConsJudgeVo>
     */
    @Override
    public ResponseEntity<ConsJudgeVo> update(
            @Validated
            @NotNull(message = "ConsJudgeVo 不能为null")
            @RequestBody ConsJudgeVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        ConsJudgeInfo entity = mapper.voToEntity(vo);
        ConsJudgeInfo result = service.update(entity, id);
        ConsJudgeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取单条研判信息Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<ConsJudgeVo>
     */
    @Override
    public ResponseEntity<ConsJudgeVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        ConsJudgeInfo result = service.findOne(id);
        ConsJudgeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据舆情信息id获取单条研判信息Vo
     *
     * @param infoid 信息Id
     * @return ResponseEntity<List<ConsJudgeVo>>
     */
    @Override
    public ResponseEntity<List<ConsJudgeVo>> findByJudgeId(
            @NotEmpty(message = "infoid 不能为空字符串")
            @RequestParam("infoid") String infoid) {
        List<ConsJudgeInfo> result = service.findByJudgeId(infoid);
        List<ConsJudgeVo> resultVo = mapper.entityListToVoList(result);
        return ResponseEntity.ok(resultVo);
    }
}
