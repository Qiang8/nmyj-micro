package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.entity.ConsInfo;
import com.taiji.cdp.report.feign.IConsInfoRestService;
import com.taiji.cdp.report.mapper.ConsInfoMapper;
import com.taiji.cdp.report.service.ConsInfoService;
import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 舆情信息接口实现类controller
 * @author qizhijie-pc
 * @date 2019年1月8日17:25:02
 */
@Slf4j
@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor
public class ConsInfoController implements IConsInfoRestService{

    ConsInfoService service;
    ConsInfoMapper mapper;

    /**
     * 新增舆情信息Vo
     *
     * @param vo 舆情信息vo
     * @return ResponseEntity<ConsInfoVo>
     */
    @Override
    public ResponseEntity<ConsInfoVo> create(
            @Validated
            @NotNull(message = "ConsInfoVo 不能为null")
            @RequestBody ConsInfoVo vo) {
        ConsInfo entity = mapper.voToEntity(vo);
        ConsInfo result = service.create(entity);
        ConsInfoVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新舆情信息Vo
     *
     * @param vo 舆情信息vo
     * @param id 信息Id
     * @return ResponseEntity<ConsInfoVo>
     */
    @Override
    public ResponseEntity<ConsInfoVo> update(
            @Validated
            @NotNull(message = "ConsInfoVo 不能为null")
            @RequestBody ConsInfoVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        ConsInfo entity = mapper.voToEntity(vo);
        ConsInfo result = service.update(entity,id);
        ConsInfoVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取单条舆情信息Vo
     *
     * @param id 信息Id
     * @return ResponseEntity<ConsInfoVo>
     */
    @Override
    public ResponseEntity<ConsInfoVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        ConsInfo result = service.findOne(id);
        ConsInfoVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id逻辑删除单条舆情信息Vo
     *
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
     * 舆情信息分页查询-- 盟市使用
     *
     * @param params
     * 参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)、createTimeStart(创建时间开始)
     *     、createTimeEnd(创建时间结束)、statuses(舆情信息状态,string[])、orgId(创建部门orgId)
     * @return ResponseEntity<RestPageImpl < ConsInfoVo>>
     *     不带取证信息
     */
    @Override
    public ResponseEntity<RestPageImpl<ConsInfoVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<ConsInfo> pageResult = service.findPage(params,pageable);
        RestPageImpl<ConsInfoVo> pageVo = mapper.entityPageToVoPage(pageResult,pageable);
        return ResponseEntity.ok(pageVo);
    }

    /**
     * 舆情信息分页查询 -- 自治区使用
     *
     * @param params 参数;page,size
     *               参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)
     *               、statuses(舆情信息状态,string[])
     * @return ResponseEntity<RestPageImpl < ConsInfoVo>>
     * 带取证信息
     */
    @Override
    public ResponseEntity<RestPageImpl<ConsInfoVo>> findRecPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<ConsInfo> pageResult = service.findRecPage(params,pageable);
        RestPageImpl<ConsInfoVo> pageVo = mapper.entityPageToVoPage(pageResult,pageable);
        return ResponseEntity.ok(pageVo);
    }

    /**
     * 舆情信息不分页查询
     * @param params
     * 参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)、statuses(舆情信息状态,string[])
     * @return ResponseEntity<List<ConsInfoVo>>
     *     不带取证信息
     */
    @Override
    public ResponseEntity<List<ConsInfoVo>> findRecList(@RequestParam MultiValueMap<String, Object> params) {
        List<ConsInfo> result = service.findRecList(params);
        List<ConsInfoVo> resultVo = mapper.entityListToVoList(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据舆情ID数组查询舆情信息列表-不分页（交接班使用）
     *
     * @param infoIds 舆情ID数组
     * @return ResponseEntity<List < ConsInfoVo>>
     */
    @Override
    public ResponseEntity<List<ConsInfoVo>> findList(@RequestBody List<String> infoIds) {
        if(!CollectionUtils.isEmpty(infoIds)){
            List<ConsInfo> list = service.findList(infoIds);
            List<ConsInfoVo> result = mapper.entityListToVoList(list);
            return ResponseEntity.ok(result);
        }else{
            return null;
        }
    }

}
