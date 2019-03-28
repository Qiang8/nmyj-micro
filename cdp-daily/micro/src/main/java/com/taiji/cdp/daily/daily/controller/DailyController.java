package com.taiji.cdp.daily.daily.controller;

import com.taiji.cdp.daily.daily.entity.Daily;
import com.taiji.cdp.daily.daily.mapper.DailyMapper;
import com.taiji.cdp.daily.daily.service.DailyService;
import com.taiji.cdp.daily.feign.IDailyService;
import com.taiji.cdp.daily.issue.controller.BaseController;
import com.taiji.cdp.daily.searchVo.DailyPageVo;
import com.taiji.cdp.daily.vo.DailyVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 日报管理信息接口实现类controller
 * @author sunyi
 * @date 2019年1月18日
 */
@Slf4j
@RestController
@RequestMapping("/api/dailys")
@AllArgsConstructor
public class DailyController extends BaseController implements IDailyService {

    DailyService service;
    DailyMapper mapper;

    /**
     * 新增日报管理
     * @param vo 新增日报管理vo
     * @return ResponseEntity<DailyVo>
     */
    @Override
    public ResponseEntity<DailyVo> create(
            @Validated
            @NotNull(message = "DailyVo 不能为null")
            @RequestBody DailyVo vo) {
        Daily entity = mapper.voToEntity(vo);
        Daily result = service.create(entity);
        DailyVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新日报管理Vo
     * @param vo 日报管理vo
     * @param id 日报管理Id
     * @return ResponseEntity<DailyVo>
     */
    @Override
    public ResponseEntity<DailyVo> update(
            @Validated
            @NotNull(message = "DailyVo 不能为null")
            @RequestBody DailyVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        Daily entity = mapper.voToEntity(vo);
        Daily result = service.update(entity,id);
        DailyVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取单条日报管理信息Vo
     * @param id 信息Id
     * @return ResponseEntity<DailyVo>
     */
    @Override
    public ResponseEntity<DailyVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        Daily result = service.findOne(id);
        DailyVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id逻辑删除单条日报管理信息Vo
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
     * 根据参数获取 DailyVo 多条记录,分页信息
     * @param dailyPageVo
     * @return ResponseEntity<RestPageImpl <ContactVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<DailyVo>> findPage(
            @RequestBody DailyPageVo dailyPageVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        int page = dailyPageVo.getPage();
        int size = dailyPageVo.getSize();
        Assert.notNull(page,"page 不能为null或空字符串!");
        Assert.notNull(size,"size 不能为null或空字符串!");
        params.add("page",page);
        params.add("size",size);
        Pageable pageable = PageUtils.getPageable(params);
        Page<Daily> page1 = service.findPage(dailyPageVo, pageable);
        RestPageImpl<DailyVo> vos = mapper.entityPageToVoPage(page1, pageable);
        return ResponseEntity.ok(vos);
    }

    /**
     * 获取所以日报管理信息
     * @return ResponseEntity<DailyVo>
     */
    @Override
    public ResponseEntity<List<DailyVo>> findList() {
        return ResponseEntity.ok(mapper.entityListToVoList(service.findList()));
    }


    /**
     * 日报信息发布，后台将该日报状态置为已发布状态
     */
    @Override
    public ResponseEntity<Void> publish(@RequestParam String dailyId) {
        service.publish(dailyId);
        return ResponseEntity.ok().build();
    }


}
