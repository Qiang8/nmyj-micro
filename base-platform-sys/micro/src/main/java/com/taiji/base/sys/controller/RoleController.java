package com.taiji.base.sys.controller;

import com.taiji.base.sys.entity.Role;
import com.taiji.base.sys.feign.IRoleRestSevice;
import com.taiji.base.sys.mapper.RoleMapper;
import com.taiji.base.sys.service.RoleService;
import com.taiji.base.sys.vo.RoleVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>Title:RoleController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/28 10:09</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController implements IRoleRestSevice {

    RoleService service;

    RoleMapper mapper;

    /**
     * 根据RoleVo id获取一条记录。
     *
     * @param id
     * @return ResponseEntity<RoleVo>
     */
    @Override
    public ResponseEntity<RoleVo> find(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id) {

        Role   entity = service.findOne(id);
        RoleVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据参数获取RoleVo多条记录。
     * <p>
     * params参数key为roleName（可选）。
     *
     * @param params
     * @return ResponseEntity<List                                                               <                                                               RoleVo>>
     */
    @Override
    public ResponseEntity<List<RoleVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        String roleName = "";

        if (params.containsKey("roleName")) {
            roleName = params.getFirst("roleName").toString();
        }

        List<Role>   result = service.findAll(roleName);
        List<RoleVo> voList = mapper.entityListToVoList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取分页RoleVo多条记录。
     * <p>
     * params参数key为roleName（可选）。
     *
     * @param params
     * @return ResponseEntity<RestPageImpl                                                               <                                                               RoleVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<RoleVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);

        String roleName = "";

        if (params.containsKey("roleName")) {
            roleName = params.getFirst("roleName").toString();
        }

        Page<Role>           result = service.findPage(roleName, pageable);
        RestPageImpl<RoleVo> voPage = mapper.entityPageToVoPage(result, pageable);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 新增RoleVo，RoleVo不能为空。
     *
     * @param vo
     * @return ResponseEntity<RoleVo>
     */
    @Override
    public ResponseEntity<RoleVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody RoleVo vo) {
        Role   tempEntity = mapper.voToEntity(vo);
        Role   entity     = service.create(tempEntity);
        RoleVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 更新RoleVo，RoleVo不能为空。
     *
     * @param vo
     * @param id 更新RoleVo Id
     * @return ResponseEntity<RoleVo>
     */
    @Override
    public ResponseEntity<RoleVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody RoleVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Role   tempEntity = mapper.voToEntity(vo);
        Role   entity     = service.update(tempEntity, id);
        RoleVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
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
     * 根据参数判断用户名是否被占用。
     *
     * @param id       （可选）
     * @param roleCode （必选）
     * @return ResponseEntity<Boolean>
     */
    @Override
    public ResponseEntity<Boolean> checkUnique(
            @RequestParam(name = "id") String id,
            @NotEmpty(message = "roleCode不能为空")
            @RequestParam(name = "roleCode") String roleCode) {
        Boolean result = service.checkUnique(id, roleCode);
        return ResponseEntity.ok(result);
    }
}
