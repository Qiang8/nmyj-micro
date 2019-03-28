package com.taiji.base.contact.service;

import com.taiji.base.contact.feign.ContactMemberClient;
import com.taiji.base.contact.vo.ContactMemberVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <p>Title:ContactMemberService.java</p >
 * <p>Description: 通讯录人员服务类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class ContactMemberService extends BaseService{

    ContactMemberClient client;

    /**
     * 根据id获取人员信息
     * @param id
     * @return
     */
    public ContactMemberVo findById(String id){
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<ContactMemberVo> result = client.find(id);
        ContactMemberVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 新增人员信息
     * @param vo
     * @return
     */
    public void create(ContactMemberVo vo){
        Assert.notNull(vo, "ContactMemberVo不能为null值。");

        ResponseEntity<ContactMemberVo> result = client.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 更新人员信息
     * @param vo
     * @param id
     */
    public void update( ContactMemberVo vo, String id){
        Assert.notNull(vo,"vo不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<ContactMemberVo> result = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 逻辑删除人员
     * @param id
     */
    public void delete(String id){
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<Void> result =  client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 查询分页列表
     * @param params
     * @return
     */
    public RestPageImpl<ContactMemberVo> findMembers(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<RestPageImpl<ContactMemberVo>> result = client
                .findPage(super.convertMap2MultiValueMap(params));
        RestPageImpl<ContactMemberVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 查询列表
     * @param params
     * @return
     */
    public List<ContactMemberVo> findMembersAll(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<ContactMemberVo>> result = client
                .findList(super.convertMap2MultiValueMap(params));
        List<ContactMemberVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }
}
