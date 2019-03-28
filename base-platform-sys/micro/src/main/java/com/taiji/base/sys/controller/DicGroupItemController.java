package com.taiji.base.sys.controller;

import com.taiji.base.sys.entity.DicGroupItem;
import com.taiji.base.sys.feign.IDicGroupItemRestService;
import com.taiji.base.sys.mapper.DicGroupItemMapper;
import com.taiji.base.sys.service.DicGroupItemService;
import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>Title:DicGroupItemController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/30 10:58</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/dic/groupItem")
@AllArgsConstructor
public class DicGroupItemController extends BaseController implements IDicGroupItemRestService {

    DicGroupItemService service;

    DicGroupItemMapper mapper;


    /**
     * 根据DicGroupItemVo获取一条记录。
     *
     * @param id 字典项id
     *
     * @return ResponseEntity<DicGroupItemVo>
     */
    @Override
    public ResponseEntity<DicGroupItemVo> find(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id) {
        DicGroupItem   entity = service.findOne(id);
        DicGroupItemVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据参数获取DicGroupItemVo多条记录。
     * <p>
     * params参数key为dicCode（可选）。
     *
     * @param params 查询参数集合
     *
     * @return ResponseEntity<List<DicGroupItemVo>>
     */
    @Override
    public ResponseEntity<List<DicGroupItemVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        String dicCode = "";

        if (params.containsKey("dicCode")) {
            dicCode = params.getFirst("dicCode").toString();
        }

        List<DicGroupItem>   result = service.findAll(dicCode);
        List<DicGroupItemVo> voList = mapper.entityListToVoList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 新增DicGroupItemVo，DicGroupItemVo不能为空。
     *
     * @param vo 字典项vo
     *
     * @return ResponseEntity<DicGroupItemVo>
     */
    @Override
    public ResponseEntity<DicGroupItemVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DicGroupItemVo vo) {
        DicGroupItem   tempEntity = mapper.voToEntity(vo);
        DicGroupItem   entity     = service.create(tempEntity);
        DicGroupItemVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 更新DicGroupItemVo，DicGroupItemVo不能为空。
     *
     * @param vo 字典项vo
     * @param id 更新DicGroupItemVo Id
     *
     * @return ResponseEntity<DicGroupItemVo>
     */
    @Override
    public ResponseEntity<DicGroupItemVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DicGroupItemVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DicGroupItem   tempEntity = mapper.voToEntity(vo);
        DicGroupItem   entity     = service.update(tempEntity, id);
        DicGroupItemVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id 字典项id
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
     * 根据字典项id获取名称
     *
     * @param id 字典项id
     * @return ResponseEntity<String> -- 字典名
     */
    @Override
    public ResponseEntity<String> findItemNameById(
            @NotEmpty
            @RequestParam("id") String id) {
        String result = service.getItemNameById(id);
        return ResponseEntity.ok(result);
    }

    /**
     * 根据字典项id List获取名称字符串(英文逗号连接)
     *
     * @param itemIds 字典项id
     * @return ResponseEntity<String> -- 字典名
     */
    @Override
    public ResponseEntity<String> findItemNamesByIds(@RequestBody List<String> itemIds) {
        String result = service.getNamesByItemIds(itemIds);
        return ResponseEntity.ok(result);
    }
}
