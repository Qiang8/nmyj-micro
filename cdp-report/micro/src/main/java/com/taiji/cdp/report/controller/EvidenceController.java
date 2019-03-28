package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.entity.EvidenceInfo;
import com.taiji.cdp.report.feign.IEvidenceRestService;
import com.taiji.cdp.report.mapper.EvidenceMapper;
import com.taiji.cdp.report.service.EvidenceService;
import com.taiji.cdp.report.vo.EvidenceVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 电子取证接口实现类controller
 *
 * @author xuweikai-pc
 * @date 2019年1月12日17:25:02
 */
@Slf4j
@RestController
@RequestMapping("/api/evidence")
@AllArgsConstructor
public class EvidenceController implements IEvidenceRestService {

    EvidenceService service;
    EvidenceMapper mapper;

    /**
     * 新增电子取证Vo
     *
     * @param vo 新增电子取证vo
     * @return ResponseEntity<EvidenceVo>
     */
    @Override
    public ResponseEntity<EvidenceVo> create(
            @Validated
            @NotNull(message = "EvidenceVo 不能为null")
            @RequestBody EvidenceVo vo) {
        EvidenceInfo entity = mapper.voToEntity(vo);
        EvidenceInfo result = service.create(entity);
        EvidenceVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取单条电子取证信息Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<EvidenceVo>
     */
    @Override
    public ResponseEntity<EvidenceVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        EvidenceInfo result = service.findOne(id);
        EvidenceVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据舆情信息id获取单条电子取证信息Vo
     *
     * @param infoId 信息Id
     * @return ResponseEntity<List<EvidenceVo>>
     */
    @Override
    public ResponseEntity<List<EvidenceVo>> findByJudgeId(
            @NotEmpty(message = "id 不能为空字符串")
            @RequestParam("infoId") String infoId) {
        List<EvidenceInfo> result = service.findByJudgeId(infoId);
        List<EvidenceVo> resultVo = mapper.entityListToVoList(result);
        return ResponseEntity.ok(resultVo);
    }
}
