package com.taiji.base.contact.service;

import com.taiji.base.contact.entity.ContactMember;
import com.taiji.base.contact.entity.ContactMid;
import com.taiji.base.contact.repository.ContactMidRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * <p>Title:ContactMidService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/12/20 16:34</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class ContactMidService extends BaseService<ContactMid,String> {

    ContactMidRepository repository;

    /**
     * 根据参数获取ContactMember多条记录。
     * <p>
     *,
     * @param groupId （可选）
     * @param content    （可选）
     * @return List<ContactMember>
     */
    public List<ContactMember> findAll(String groupId, String content) {

        return repository.findAll(groupId,content);
    }

    /**
     * 根据参数获取分页ContactMember多条记录。
     * <p>
     *
     * @param params
     *        groupId 分组编码
     *        orgCode 组织机构编码
     *       letter 姓名首字母大写
     *       content 姓名、电话、部门名称综合查询
     * @return RestPageImpl<ContactMember>
     */
    public Page<ContactMember> findPage(MultiValueMap<String, Object> params, Pageable pageable) {
        return repository.findPage(params,pageable);
    }

    /**
     *
     * @param entityList 通讯录人员信息entityList
     * @param groupId 分组编码
     */
    public void save(List<ContactMid> entityList, String groupId) {
        Assert.hasText(groupId, "groupId不能为null或空字符串!");

        repository.save(entityList,groupId);
    }
}
