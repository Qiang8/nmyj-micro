package com.taiji.base.contact.controller;

import com.taiji.base.contact.entity.ContactGroup;
import com.taiji.base.contact.feign.IContactGroupRestService;
import com.taiji.base.contact.mapper.ContactGroupMapper;
import com.taiji.base.contact.mapper.ContactMidMapper;
import com.taiji.base.contact.service.ContactGroupService;
import com.taiji.base.contact.vo.ContactGroupVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>Title:ContactGroupController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/12/20 16:24</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/contactGroup")
public class ContactGroupController extends BaseController implements IContactGroupRestService {

    ContactGroupService service;

    ContactGroupMapper mapper;

    ContactMidMapper midMapper;

    /**
     * 根据ContactGroupVo id获取一条记录。
     *
     * @param id 通讯录分组id
     * @return ResponseEntity<ContactGroupVo>
     */
    @Override
    public ResponseEntity<ContactGroupVo> find(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id) {
        ContactGroup entity = service.findOne(id);
        ContactGroupVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据参数获取ContactGroupVo多条记录。
     * <p>
     * @params userId 查找个人分组
     *
     * @return ResponseEntity<List < ContactGroupVo>>
     */
    @Override
    public ResponseEntity<List<ContactGroupVo>> findList(@RequestParam(name = "userId")
                                                         @NotEmpty(message = "userId不能为空")
                                                         String userId) {

        List<ContactGroup>   result = service.findAll(userId);
        List<ContactGroupVo> voList = mapper.entityListToVoList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 新增ContactGroupVo，ContactGroupVo不能为空。
     *
     * @param vo 通讯录分组vo
     * @return ResponseEntity<ContactGroupVo>
     */
    @Override
    public ResponseEntity<ContactGroupVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody ContactGroupVo vo) {
        ContactGroup   tempEntity = mapper.voToEntity(vo);
        ContactGroup   entity     = service.create(tempEntity);
        ContactGroupVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 更新ContactGroupVo，ContactGroupVo不能为空。
     *
     * @param vo 通讯录分组vo
     * @param id 更新ContactGroupVo Id
     * @return ResponseEntity<ContactGroupVo>
     */
    @Override
    public ResponseEntity<ContactGroupVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody ContactGroupVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {

        ContactGroup   tempEntity = mapper.voToEntity(vo);
        ContactGroup   entity     = service.update(tempEntity, id);
        ContactGroupVo tempVo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id 通讯录分组id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {

        service.deleteLogic(id, DelFlagEnum.DELETE);

        return ResponseEntity.ok().build();
    }
}
