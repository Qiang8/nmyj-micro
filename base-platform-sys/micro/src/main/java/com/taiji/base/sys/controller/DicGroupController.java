package com.taiji.base.sys.controller;

import com.taiji.base.sys.entity.DicGroup;
import com.taiji.base.sys.feign.IDicGroupRestService;
import com.taiji.base.sys.mapper.DicGroupMapper;
import com.taiji.base.sys.service.DicGroupService;
import com.taiji.base.sys.vo.DicGroupVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>Title:DicGroupController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/30 8:25</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/dic/group")
@AllArgsConstructor
public class DicGroupController extends BaseController implements IDicGroupRestService {

    DicGroupService service;

    DicGroupMapper mapper;

    /**
     * 根据参数获取DicGroupVo多条记录。
     * <p>
     * params参数key为dicName（可选），status（可选）。
     *
     * @param params
     *
     * @return ResponseEntity<List<DicGroupVo>>
     */
    @Override
    public ResponseEntity<List<DicGroupVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        String dicName = "";
        String status  = "";

        if (params.containsKey("dicName")) {
            dicName = params.getFirst("dicName").toString();
        }

        if (params.containsKey("status")) {
            status = params.getFirst("status").toString();
        }
        List<DicGroup>   result = service.findAll(dicName, status);
        List<DicGroupVo> voList = mapper.entityListToVoList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取DicGroupVo多条记录。
     * <p>
     * params参数key为dicName（可选  模糊查询），status（可选）。
     *
     * @param params
     *
     * @return ResponseEntity<RestPageImpl<DicGroupVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<DicGroupVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);

        String dicName = "";
        String status  = "";

        if (params.containsKey("dicName")) {
            dicName = params.getFirst("dicName").toString();
        }

        if (params.containsKey("status")) {
            status = params.getFirst("status").toString();
        }

        Page<DicGroup>           result = service.findPage(dicName, status, pageable);
        RestPageImpl<DicGroupVo> voPage = mapper.entityPageToVoPage(result, pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 根据DicGroupVo获取一条记录。
     *
     * @param id
     *
     * @return ResponseEntity<DicGroupVo>
     */
    @Override
    public ResponseEntity<DicGroupVo> find(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id) {
        DicGroup   entity = service.findOne(id);
        DicGroupVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据DicGroupVo获取一条记录。
     *
     * @param params 参数key为dicCode（必选）
     *
     * @return ResponseEntity<DicGroupVo>
     */
    @Override
    public ResponseEntity<DicGroupVo> findOne(@RequestParam MultiValueMap<String, Object> params) {

        String dicCode = "";

        if (params.containsKey("dicCode")) {
            dicCode = params.getFirst("dicCode").toString();
        }

        Assert.hasText(dicCode, "dicCode must not be empty string!");

        DicGroup   entity = service.findOneByDicCode(dicCode);
        DicGroupVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 新增DicGroupVo，DicGroupVo不能为空。
     *
     * @param vo
     *
     * @return ResponseEntity<DicGroupVo>
     */
    @Override
    public ResponseEntity<DicGroupVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DicGroupVo vo) {
        DicGroup   tempEntity = mapper.voToEntity(vo);
        DicGroup   entity     = service.create(tempEntity);
        DicGroupVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 更新DicGroupVo，DicGroupVo不能为空。
     *
     * @param vo
     * @param id 更新DicGroupVo Id
     *
     * @return ResponseEntity<DicGroupVo>
     */
    @Override
    public ResponseEntity<DicGroupVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DicGroupVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DicGroup   tempEntity = mapper.voToEntity(vo);
        DicGroup   entity     = service.update(tempEntity, id);
        DicGroupVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     *
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);

        return ResponseEntity.ok().build();
    }

    /**
     * 根据参数判断字典标识是否被占用。
     *
     * @param id      （可选）
     * @param dicCode （必选）
     *
     * @return ResponseEntity<Boolean>
     */
    @Override
    public ResponseEntity<Boolean> checkUnique(
            @RequestParam(name = "id")
                    String id,
            @RequestParam(name = "dicCode")
            @NotEmpty(message = "dicCode不能为空") String dicCode) {
        Boolean result = service.checkUnique(id, dicCode);
        return ResponseEntity.ok(result);
    }
}
