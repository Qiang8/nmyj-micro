package com.taiji.base.sys.controller;

import com.taiji.base.sys.entity.RoleTypeMenu;
import com.taiji.base.sys.feign.IRoleTypeMenuRestService;
import com.taiji.base.sys.mapper.RoleTypeMenuMapper;
import com.taiji.base.sys.service.RoleTypeMenuService;
import com.taiji.base.sys.vo.RoleTypeMenuVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:RoleTypeMenuController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/30 13:51</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/roleTypeMenu")
public class RoleTypeMenuController extends BaseController implements IRoleTypeMenuRestService {

    RoleTypeMenuService service;

    RoleTypeMenuMapper mapper;

    /**
     * 根据参数获取RoleTypeMenuVo多条记录。
     * <p>
     * params参数key为roleType（必选）。
     *
     * @param params 查询参数集合
     *
     * @return ResponseEntity<List < RoleTypeMenuVo>>
     */
    @Override
    public ResponseEntity<List<RoleTypeMenuVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        String roleType = "";

        if (params.containsKey("roleType")) {
            roleType = params.getFirst("roleType").toString();
        }

        List<RoleTypeMenu> result = service.findAll(roleType);

        List<RoleTypeMenuVo> voList = mapper.entityListToVoList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 保存多个角色类型资源vo,保存前会先根据roleType删除数据库相应的记录再保存voList。
     *
     * @param voList   角色类型资源vo
     * @param roleType 角色类型
     *
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> save(
            @RequestBody List<RoleTypeMenuVo> voList,
            @NotEmpty(message = "roleType不能为空")
            @PathVariable("roleType") String roleType) {
        List<RoleTypeMenu> tempEntityList = mapper.voListToEntityList(voList);
       service.save(tempEntityList,roleType);
        return ResponseEntity.ok().build();
    }
}
