package com.taiji.base.contact.service;

import com.taiji.base.contact.feign.ContactMidClient;
import com.taiji.base.contact.vo.ContactMemberVo;
import com.taiji.base.contact.vo.ContactMidVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <p>Title:ContactMidService.java</p >
 * <p>Description: 通讯录人员关联表服务类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class ContactMidService extends BaseService{

    ContactMidClient client;

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

    /**
     * 保存通讯录分组信息
     * @param list
     * @param groupId
     */
    public  void saveContactMids(List<ContactMidVo> list , String groupId){
        Assert.hasText(groupId,"groupId不能为null或空字符串!");

        ResponseEntity<Void> result = client.saveMid(list,groupId);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }
}
