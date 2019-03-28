package com.taiji.base.contact.controller;

import com.taiji.base.contact.entity.ContactMember;
import com.taiji.base.contact.feign.IContactMemberRestService;
import com.taiji.base.contact.mapper.ContactMemberMapper;
import com.taiji.base.contact.service.ContactMemberService;
import com.taiji.base.contact.vo.ContactMemberVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>Title:ContactMemberController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/12/20 16:25</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/contactMember")
public class ContactMemberController extends BaseController implements IContactMemberRestService  {

    ContactMemberService service;

    ContactMemberMapper mapper;

    /**
     * 根据ContactMemberVo id获取一条记录。
     *
     * @param id 通讯录id
     * @return ResponseEntity<ContactMemberVo>
     */
    @Override
    public ResponseEntity<ContactMemberVo> find(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id) {
        ContactMember entity = service.findOne(id);
        ContactMemberVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据参数获取ContactMemberVo多条记录。
     * <p>
     * params参数key
     *       content 姓名、电话、部门名称综合查询
     *
     * @param params 查询参数集合
     * @return ResponseEntity<List < ContactMemberVo>>
     */
    @Override
    public ResponseEntity<List<ContactMemberVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        String content    = "";

        if (params.containsKey("content")) {
            content = params.getFirst("content").toString();
        }
        List<ContactMember>   result = service.findAll(content);
        List<ContactMemberVo> voList = mapper.entityListToVoList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取分页ContactMemberVo多条记录。
     * <p>
     * params参数key
     *        orgCode 组织机构编码
     *       letter 姓名首字母大写
     *       content 姓名、电话、部门名称综合查询
     *       name   姓名  与content不能同时使用
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl < ContactMemberVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<ContactMemberVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageUtils.getPageable(params,sort);

        Page<ContactMember> result = service.findPage(params, pageable);
        RestPageImpl<ContactMemberVo> voPage = mapper.entityPageToVoPage(result, pageable);

        return ResponseEntity.ok(voPage);
    }

    /**
     * 新增ContactMemberVo，ContactMemberVo不能为空。
     *
     * @param vo 通讯录vo
     * @return ResponseEntity<ContactMemberVo>
     */
    @Override
    public ResponseEntity<ContactMemberVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody ContactMemberVo vo) {

        ContactMember   tempEntity = mapper.voToEntity(vo);
        ContactMember   entity     = service.create(tempEntity);
        ContactMemberVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 更新ContactMemberVo，ContactMemberVo不能为空。
     *
     * @param vo 通讯录vo
     * @param id 更新ContactMemberVo Id
     * @return ResponseEntity<ContactMemberVo>
     */
    @Override
    public ResponseEntity<ContactMemberVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody ContactMemberVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {

        ContactMember   tempEntity = mapper.voToEntity(vo);
        ContactMember   entity     = service.update(tempEntity, id);
        ContactMemberVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id 通讯录id
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
