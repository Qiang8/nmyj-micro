package com.taiji.cdp.cmd.controller;

import com.taiji.cdp.cmd.entity.CommandEntity;
import com.taiji.cdp.cmd.feign.ICommandService;
import com.taiji.cdp.cmd.mapper.CommandMapper;
import com.taiji.cdp.cmd.service.CommandService;
import com.taiji.cdp.cmd.vo.CommandVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 舆情调控单接口实现controller
 *
 * @author xuweikai-pc
 * @date 2019年3月3日17:25:02
 */
@Slf4j
@RestController
@RequestMapping("/api/command")
@AllArgsConstructor
public class CommandController implements ICommandService {

    CommandService service;
    CommandMapper mapper;

    /**
     * 新增舆情调控单Vo
     *
     * @param vo CommandVo
     * @return ResponseEntity<CommandVo>
     */
    @Override
    public ResponseEntity<CommandVo> create(
            @Validated
            @NotNull(message = "CommandVo 不能为null")
            @RequestBody CommandVo vo) {
        CommandEntity entity = mapper.voToEntity(vo);
        CommandEntity result = service.create(entity);
        CommandVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新舆情调控单Vo
     *
     * @param vo 研判信息vo
     * @param id 信息Id
     * @return ResponseEntity<CommandVo>
     */
    @Override
    public ResponseEntity<CommandVo> update(
            @Validated
            @NotNull(message = "ExeorgVo 不能为null")
            @RequestBody CommandVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        CommandEntity entity = mapper.voToEntity(vo);
        CommandEntity result = service.update(entity, id);
        CommandVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取单条舆情调控单Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<CommandVo>
     */
    @Override
    public ResponseEntity<CommandVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        CommandEntity result = service.findOne(id);
        CommandVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据参数获取分页CommandVo多条记录。
     * params参数key为account（可选），name（可选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<CommandVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<CommandVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<CommandEntity> result = service.findPage(params, pageable);
        RestPageImpl<CommandVo> voPage = mapper.entityPageToVoPage(result, pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 根据参数获取分页CommandVo多条记录。查询收到的信息
     * params参数key为account（可选），name（可选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<CommandVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<CommandVo>> findRecivePage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<CommandEntity> result = service.findRecivePage(params, pageable);
        RestPageImpl<CommandVo> voPage = mapper.entityPageToVoPage(result, pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 根据id发布一条记录。
     *
     * @param cdId 舆情调控单id
     * @return ResponseEntity<CommandVo>
     */
    @Override
    public ResponseEntity<CommandVo> updateStatus(@NotEmpty(message = "cdId不能为空") @RequestParam(value = "cdId") String cdId,
                                                  @NotEmpty(message = "status不能为空") @RequestParam(value = "status") String status
    ) {
        service.updateStatus(cdId, status);
        return ResponseEntity.ok().build();
    }
}
