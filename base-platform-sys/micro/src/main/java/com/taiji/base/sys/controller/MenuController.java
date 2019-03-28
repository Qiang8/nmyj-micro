package com.taiji.base.sys.controller;

import com.taiji.base.sys.entity.Menu;
import com.taiji.base.sys.feign.IMenuRestService;
import com.taiji.base.sys.mapper.MenuMapper;
import com.taiji.base.sys.service.MenuService;
import com.taiji.base.sys.vo.MenuVo;
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
 * <p>Title:MenuController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 14:53</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/menu")
public class MenuController extends BaseController implements IMenuRestService {

    MenuService service;

    MenuMapper mapper;

    /**
     * 根据参数获取MenuVo多条记录。
     * <p>
     * params参数key为parentId（可选），roleType（可选）。
     *
     * @param params
     * @return ResponseEntity<MenuVo>
     */
    @Override
    public ResponseEntity<List<MenuVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        String parentId = "";
        String roleType    = "";

        if (params.containsKey("parentId")) {
            parentId = params.getFirst("parentId").toString();
        }
        if (params.containsKey("roleType")) {
            roleType = params.getFirst("roleType").toString();
        }

        List<Menu> list = service.findAll(parentId,roleType);
        List<MenuVo> voList  = mapper.entityListToVoList(list);

        return ResponseEntity.ok(voList);
    }

    /**
     * 根据MenuVo id获取一条记录。
     *
     * @param id
     * @return ResponseEntity<MenuVo>
     */
    @Override
    public ResponseEntity<MenuVo> find(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Menu   entity = service.findOne(id);
        MenuVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 新增MenuVo，MenuVo不能为空。
     *
     * @param vo
     * @return ResponseEntity<MenuVo>
     */
    @Override
    public ResponseEntity<MenuVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody MenuVo vo) {
        Menu   tempEntity = mapper.voToEntity(vo);
        Menu   entity     = service.create(tempEntity);
        MenuVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 更新MenuVo，MenuVo不能为空。
     *
     * @param vo
     * @param id 更新MenuVo Id
     * @return ResponseEntity<MenuVo>
     */
    @Override
    public ResponseEntity<MenuVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody MenuVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        Menu   tempEntity = mapper.voToEntity(vo);
        Menu   entity     = service.update(tempEntity, id);
        MenuVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return ResponseEntity.ok().build();
    }
}
