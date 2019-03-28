package com.taiji.base.contact.service;

import com.taiji.base.contact.feign.ContactGroupClient;
import com.taiji.base.contact.vo.ContactGroupVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>Title:ContactGroupService.java</p >
 * <p>Description: 通讯录分组服务类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class ContactGroupService extends BaseService{

    ContactGroupClient client;

    /**
     * 根据id获取分组信息
     * @param id
     * @return
     */
    public ContactGroupVo findById(String id){
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<ContactGroupVo> result = client.find(id);
        ContactGroupVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 新增分组信息
     * @param vo
     * @return
     */
    public ContactGroupVo create(ContactGroupVo vo){
        Assert.notNull(vo, "ContactGroupVo不能为null值。");

        ResponseEntity<ContactGroupVo> result = client.create(vo);
        ContactGroupVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 更新分组信息
     * @param vo
     * @param id
     */
    public void update( ContactGroupVo vo, String id){
        Assert.notNull(vo,"vo不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<ContactGroupVo> result = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 逻辑删除分组
     * @param id
     */
    public void delete(String id){
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<Void> result =  client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 查询列表
     * @param userId
     * @return
     */
    public List<ContactGroupVo> findGroupsAll(String userId) {
        Assert.hasText(userId, "userId不能为null或空字符串!");

        ResponseEntity<List<ContactGroupVo>> result = client
                .findList(userId);
        List<ContactGroupVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

}
