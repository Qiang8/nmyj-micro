package com.taiji.cdp.cmd.controller;

import com.taiji.cdp.cmd.entity.ExeorgEntity;
import com.taiji.cdp.cmd.feign.IExeorgService;
import com.taiji.cdp.cmd.mapper.ExeorgMapper;
import com.taiji.cdp.cmd.service.ExeorgService;
import com.taiji.cdp.cmd.vo.ExeorgVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 舆情调控单关联表接口实现类
 *
 * @author qizhijie-pc
 * @date 2019年1月8日17:25:02
 */
@Slf4j
@RestController
@RequestMapping("/api/exeorg")
@AllArgsConstructor
public class ExeorgController implements IExeorgService {

    ExeorgService service;
    ExeorgMapper mapper;

    /**
     * 新增关联关系Vo
     *
     * @param vo ExeorgVo
     * @return ResponseEntity<ExeorgVo>
     */
    @Override
    public ResponseEntity<ExeorgVo> create(
            @Validated
            @NotNull(message = "ExeorgVo 不能为null")
            @RequestBody ExeorgVo vo) {
        ExeorgEntity entity = mapper.voToEntity(vo);
        ExeorgEntity result = service.create(entity);
        ExeorgVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 新增关联关系List<Vo>
     *
     * @param vos List<ExeorgVo>
     * @return ResponseEntity<ExeorgVo>
     */
    @Override
    public ResponseEntity<List<ExeorgVo>> createList(
            @Validated
            @NotNull(message = "vos 不能为null")
            @RequestBody List<ExeorgVo> vos) {
        List<ExeorgEntity> entities = mapper.voListToEntityList(vos);
        List<ExeorgEntity> list = service.createList(entities);
        List<ExeorgVo> resultVos = mapper.entityListToVoList(list);
        return ResponseEntity.ok(resultVos);
    }

    /**
     * 更新联关系Vo
     *
     * @param vo ExeorgVo
     * @return ResponseEntity<ExeorgVo>
     */
    @Override
    public ResponseEntity<ExeorgVo> update(
            @Validated
            @NotNull(message = "ExeorgVo 不能为null")
            @RequestBody ExeorgVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        ExeorgEntity entity = mapper.voToEntity(vo);
        ExeorgEntity result = service.update(entity, id);
        ExeorgVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据舆情调控单id获取关联关系
     *
     * @param cdId cdId
     * @return ResponseEntity<EvidenceVo>
     */
    @Override
    public ResponseEntity<List<ExeorgVo>> findByCmdId(@RequestParam("cdId") String cdId) {
        List<ExeorgEntity> result = service.findByCmdId(cdId);
        List<ExeorgVo> resultVo = mapper.entityListToVoList(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据组织机构id查询舆情调控单id
     *
     * @param orgId orgId
     * @return ResponseEntity<String>
     */
    @Override
    public ResponseEntity<List<ExeorgVo>> findcmdIdByOrgId(@RequestParam("orgId") String orgId) {
        List<ExeorgEntity> result = service.findcmdIdByOrgId(orgId);
        List<ExeorgVo> resultVo = mapper.entityListToVoList(result);
        return ResponseEntity.ok(resultVo);
    }
}
