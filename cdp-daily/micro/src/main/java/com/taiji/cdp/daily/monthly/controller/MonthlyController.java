package com.taiji.cdp.daily.monthly.controller;

import com.taiji.cdp.daily.feign.IMonthlyRestService;
import com.taiji.cdp.daily.issue.controller.BaseController;
import com.taiji.cdp.daily.monthly.entity.Monthly;
import com.taiji.cdp.daily.monthly.mapper.MonthlyMapper;
import com.taiji.cdp.daily.monthly.service.MonthlyService;
import com.taiji.cdp.daily.searchVo.MonthlyPageVo;
import com.taiji.cdp.daily.vo.MonthlyVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
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

/**
 * <p>Title:MonthlyController.java</p >
 * <p>Description: 月报管理micro实现</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/monthly")
public class MonthlyController extends BaseController implements IMonthlyRestService {
    MonthlyService monthlyService;

    MonthlyMapper monthlyMapper;

    /**
     * 新增月报信息，MonthlyVo不能为空。
     *
     * @param vo 月报vo
     * @return ResponseEntity<MonthlyVo>
     */
    @Override
    public ResponseEntity<MonthlyVo> create(@Validated
                                            @NotNull(message = "vo不能为null")
                                            @RequestBody MonthlyVo vo) {
        Monthly tempEntity = monthlyMapper.voToEntity(vo);
        Monthly entity = monthlyService.addMonthly(tempEntity);
        MonthlyVo tempVo = monthlyMapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据参数获取分页MonthlyVo多条记录。
     *
     * @param pageVo 查询参数集合
     * @return ResponseEntity<RestPageImpl<MonthlyVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<MonthlyVo>> findPage(@RequestBody MonthlyPageVo pageVo) {

        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        int page = pageVo.getPage();
        int size = pageVo.getSize();
        Assert.notNull(page,"page 不能为null或空字符串!");
        Assert.notNull(size,"size 不能为null或空字符串!");
        params.add("page",page);
        params.add("size",size);
        Pageable pageable = PageUtils.getPageable(params);
        Page<Monthly> result = monthlyService.findPage(pageVo, pageable);
        RestPageImpl<MonthlyVo> voPage = monthlyMapper.entityPageToVoPage(result, pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return ResponseEntity<MonthlyVo>
     */
    @Override
    public ResponseEntity<MonthlyVo> find(@NotEmpty(message = "id不能为空")
                                          @PathVariable("id") String id) {
        Monthly entity = monthlyService.findOne(id);
        MonthlyVo vo = monthlyMapper.entityToVo(entity);
        return ResponseEntity.ok(vo);
    }

    /**
     * 更新MonthlyVo，MonthlyVo不能为空。
     *
     * @param vo
     * @param id 更新MonthlyVo Id
     * @return ResponseEntity<MonthlyVo>
     */
    @Override
    public ResponseEntity<MonthlyVo> update(@Validated
                                            @NotNull(message = "vo不能为null")
                                            @RequestBody MonthlyVo vo,
                                            @NotEmpty(message = "id不能为空")
                                            @PathVariable(value = "id") String id) {
        Monthly tempEntity = monthlyMapper.voToEntity(vo);
        Monthly entity = monthlyService.update(tempEntity, id);
        MonthlyVo tempVo = monthlyMapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据id删除一条记录。
     *
     * @param id 用户id
     * @return ResponseEntity<MonthlyVo>
     */
    @Override
    public ResponseEntity<MonthlyVo> deleteLogic(@NotEmpty(message = "id不能为空") @PathVariable(value = "id") String id) {
        monthlyService.deleteLogic(id, DelFlagEnum.DELETE);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据id发布一条记录。
     *
     * @param id 用户id
     * @return ResponseEntity<MonthlyVo>
     */
    @Override
    public ResponseEntity<MonthlyVo> publishInfo(@NotEmpty(message = "id不能为空") @RequestParam(value = "id") String id) {
        monthlyService.publishInfo(id, StatusEnum.ENABLE);
        return ResponseEntity.ok().build();
    }
}