package com.taiji.cdp.daily.duty.controller;

import com.taiji.cdp.daily.duty.entity.DutyShiftEntity;
import com.taiji.cdp.daily.duty.entity.DutyShiftItemEntity;
import com.taiji.cdp.daily.duty.mapper.DutyShiftItemMapper;
import com.taiji.cdp.daily.duty.mapper.DutyShiftMapper;
import com.taiji.cdp.daily.duty.service.DutyShiftItemService;
import com.taiji.cdp.daily.duty.service.DutyShiftService;
import com.taiji.cdp.daily.feign.IDutyShiftRestService;
import com.taiji.cdp.daily.issue.controller.BaseController;
import com.taiji.cdp.daily.vo.DutyShiftItemVo;
import com.taiji.cdp.daily.vo.DutyShiftVo;
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
import java.util.List;

/**
 * <p>Title:DutyShiftController.java</p >
 * <p>Description: 交接班管理实现类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/21 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/duty")
public class DutyShiftController extends BaseController implements IDutyShiftRestService {

    DutyShiftService dutyService;

    DutyShiftItemService itemService;

    DutyShiftMapper dutyMapper;

    DutyShiftItemMapper itemMapper;

    /**
     * 1.新增交接班信息，DutyShiftVo。
     *
     * @param vo 交接班vo
     * @return ResponseEntity<DutyShiftVo>
     */
    @Override
    public ResponseEntity<DutyShiftVo> createDuty(@Validated
                                                  @NotNull(message = "vo不能为null")
                                                  @RequestBody DutyShiftVo vo) {
        DutyShiftEntity tempEntity = dutyMapper.voToEntity(vo);
        DutyShiftEntity entity = dutyService.addDuty(tempEntity);
        DutyShiftVo tempVo = dutyMapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 2.新增事项信息，DutyShiftItemVo。
     *
     * @param vo 交接班事项vo
     * @return ResponseEntity<DutyShiftItemVo>
     */
    @Override
    public ResponseEntity<DutyShiftItemVo> createDutyItem(@Validated
                                                          @NotNull(message = "vo不能为null")
                                                          @RequestBody DutyShiftItemVo vo) {
        DutyShiftItemEntity tempEntity = itemMapper.voToEntity(vo);
        DutyShiftItemEntity entity = itemService.addDutyItem(tempEntity);
        DutyShiftItemVo tempVo = itemMapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 3.根据id获取一条交接班信息。
     *
     * @param id
     * @return ResponseEntity<DutyShiftVo>
     */
    @Override
    public ResponseEntity<DutyShiftVo> findDuty(@NotEmpty(message = "id不能为空")
                                                @PathVariable("id") String id) {
        DutyShiftEntity entity = dutyService.findOne(id);
        DutyShiftVo vo = dutyMapper.entityToVo(entity);
        return ResponseEntity.ok(vo);
    }

    /**
     * 4.根据 dutyId 获取交接班事项信息。
     *
     * @param id
     * @return ResponseEntity<DutyShiftItemVo>
     */
    @Override
    public ResponseEntity<List<DutyShiftItemVo>> findDutyItem(
            @NotEmpty(message = "dutyId 不能为空字符串")
            @RequestParam("id") String id) {
        List<DutyShiftItemEntity> result = itemService.findDutyItem(id);
        List<DutyShiftItemVo> resultVo = itemMapper.entityListToVoList(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 5.根据参数获取分页DutyShiftVo多条记录。
     * params参数key为toTeamId（可选），fromTeamId（可选），title（可选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<DutyShiftVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<DutyShiftVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<DutyShiftEntity> result = dutyService.findPage(params, pageable);
        RestPageImpl<DutyShiftVo> voPage = dutyMapper.entityPageToVoPage(result, pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 6.获取最新一条交接班信息
     *
     * @return ResponseEntity<DutyShiftItemVo>
     */
    @Override
    public ResponseEntity<DutyShiftVo> findNewDuty() {
        DutyShiftEntity entity = dutyService.findNewDuty();
        DutyShiftVo vo = dutyMapper.entityToVo(entity);
        return ResponseEntity.ok(vo);
    }
}