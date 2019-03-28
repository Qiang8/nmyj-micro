package com.taiji.base.contact.controller;

import com.taiji.base.contact.entity.ContactMember;
import com.taiji.base.contact.entity.ContactMid;
import com.taiji.base.contact.feign.IContactMidRestService;
import com.taiji.base.contact.mapper.ContactMemberMapper;
import com.taiji.base.contact.mapper.ContactMidMapper;
import com.taiji.base.contact.service.ContactMidService;
import com.taiji.base.contact.vo.ContactMemberVo;
import com.taiji.base.contact.vo.ContactMidVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:ContactMidController.java</p >
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
@RequestMapping("/api/contactMid")
public class ContactMidController extends BaseController implements IContactMidRestService {

    ContactMidService service;

    ContactMidMapper mapper;

    ContactMemberMapper memberMapper;

    /**
     * 根据参数获取ContactGroupVo多条记录。
     * <p>
     * params参数key
     * groupId 分组编码
     * content 姓名、电话、部门名称综合查询
     *
     * @param params 查询参数集合
     * @return ResponseEntity<List < ContactMemberVo>>
     */
    @Override
    public ResponseEntity<List<ContactMemberVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        String content = "";
        String groupId = "";

        if (params.containsKey("content")) {
            content = params.getFirst("content").toString();
        }

        if (params.containsKey("groupId")) {
            groupId = params.getFirst("groupId").toString();
        }

        List<ContactMember>   result = service.findAll(groupId,content);
        List<ContactMemberVo> voList = memberMapper.entityListToVoList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取分页ContactMemberVo多条记录。
     * <p>
     * params参数key
     * groupId 分组编码
     * orgCode 组织机构编码
     * letter 姓名首字母大写
     * content 姓名、电话、部门名称综合查询
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl < ContactMemberVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<ContactMemberVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);

        Page<ContactMember> result = service.findPage(params, pageable);
        RestPageImpl<ContactMemberVo> voPage = memberMapper.entityPageToVoPage(result, pageable);

        return ResponseEntity.ok(voPage);
    }

    /**
     * 保存分组人员信息表，先删除分组人员，再添加。
     *
     * @param voList  人员vo列表
     * @param groupId 分组编码
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> saveMid(
            @RequestBody List<ContactMidVo> voList,
            @NotEmpty(message = "groupId不能为空")
            @PathVariable("groupId") String groupId) {
        List<ContactMid> tempEntityList = mapper.voListToEntityList(voList);
        service.save(tempEntityList,groupId);
        return ResponseEntity.ok().build();
    }
}
