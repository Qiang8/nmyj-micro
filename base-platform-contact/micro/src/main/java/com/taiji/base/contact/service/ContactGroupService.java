package com.taiji.base.contact.service;

import com.taiji.base.contact.entity.ContactGroup;
import com.taiji.base.contact.repository.ContactGroupRepository;
import com.taiji.base.contact.repository.ContactMidRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>Title:ContactGroupService.java</p >
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
public class ContactGroupService extends BaseService<ContactGroup,String> {

    ContactGroupRepository repository;

    ContactMidRepository midRepository;

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return ContactGroup
     */
    public ContactGroup findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 根据参数获取ContactGroup多条记录。
     * <p>
     *
     * @param userId
     * @return List<ContactGroup>
     */
    public List<ContactGroup> findAll(String userId) {

        return repository.findAll(userId);
    }

    /**
     * 新增ContactGroup不能为空。
     *
     * @param entity
     * @return ContactGroup
     */
    public ContactGroup create(ContactGroup entity) {
        Assert.notNull(entity,"entity不能为null!");

        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());

        return repository.save(entity);
    }

    /**
     * 更新ContactGroup不能为空。
     *
     * @param entity
     * @param id 更新ContactGroup Id
     * @return ContactGroup
     */
    public ContactGroup update(ContactGroup entity, String id) {
        Assert.notNull(entity,"entity不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        return repository.save(entity);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return String
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为null或空字符串!");
        ContactGroup entity = repository.findOne(id);
        repository.deleteLogic(entity, delFlagEnum);

        midRepository.deleteMemberByGroupId(id);
    }
}
