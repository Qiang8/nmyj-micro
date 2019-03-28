package com.taiji.base.contact.service;

import com.taiji.base.common.utils.PinYinUtils;
import com.taiji.base.contact.entity.ContactMember;
import com.taiji.base.contact.repository.ContactMemberRepository;
import com.taiji.base.contact.repository.ContactMidRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * <p>Title:ContactMemberService.java</p >
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
public class ContactMemberService extends BaseService<ContactMember,String> {

    ContactMemberRepository repository;

    ContactMidRepository midRepository;

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return ContactMember
     */
    public ContactMember findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 根据参数获取ContactMember多条记录。
     * <p>
     * @param content    （可选）
     * @return List<ContactMember>
     */
    public List<ContactMember> findAll(String content) {

        return repository.findAll(content);
    }

    /**
     * 根据参数获取分页ContactMember多条记录。
     * <p>
     *
     * @param params
     *        orgCode 组织机构编码
     *       letter 姓名首字母大写
     *       content 姓名、电话、部门名称综合查询
     *       name   姓名  与content不能同时使用
     * @return RestPageImpl<ContactMember>
     */
    public Page<ContactMember> findPage(MultiValueMap<String, Object> params, Pageable pageable) {
        return repository.findPage(params,pageable);
    }

    /**
     * 新增ContactMember不能为空。
     *
     * @param entity
     * @return ContactMember
     */
    public ContactMember create(ContactMember entity) {
        Assert.notNull(entity,"entity不能为null!");

        String pinyin = PinYinUtils.converterToFirstSpell(entity.getName());
        entity.setPinyin(pinyin);
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());

        return repository.save(entity);
    }

    /**
     * 更新ContactMember不能为空。
     *
     * @param entity
     * @param id 更新ContactMember Id
     * @return ContactMember
     */
    public ContactMember update(ContactMember entity, String id) {
        Assert.notNull(entity,"entity不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        String pinyin = PinYinUtils.converterToFirstSpell(entity.getName());
        entity.setPinyin(pinyin);
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
        ContactMember entity = repository.findOne(id);
        repository.deleteLogic(entity, delFlagEnum);

        midRepository.deleteMemberByMemberId(id);
    }

}
