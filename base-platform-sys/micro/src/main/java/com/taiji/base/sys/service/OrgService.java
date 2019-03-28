package com.taiji.base.sys.service;

import com.taiji.base.sys.entity.Org;
import com.taiji.base.sys.repository.OrgRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 系统组织机构Service类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrgService extends BaseService<Org,String> {

    OrgRepository repository;

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return Org
     */
    public Org findOne(String id) {
        Assert.hasText(id, "id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 根据参数获取Org多条记录。
     * <p>
     *
     * @param parentId （可选）
     * @param orgName    （可选）
     * @return List<Org>
     */
    public List<Org> findAll(String parentId, String orgName) {
        return repository.findAll(parentId,orgName);
    }

    /**
     * 新增Org，Org不能为空。
     *
     * @param entity
     * @return Org
     */
    public Org create(Org entity) {
        Assert.notNull(entity,"entity不能为null!");

        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());

        return repository.save(entity);
    }

    /**
     * 更新Org，Org不能为空。
     *
     * @param entity
     * @param id 更新User Id
     * @return User
     */
    public Org update(Org entity, String id) {
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
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为null或空字符串!");
        Org entity = repository.findOne(id);
        repository.deleteLogic(entity, delFlagEnum);
    }

    public List<Org> findOrgInfo(List<String> orgIds) {
        return repository.findOrgInfo(orgIds);
    }
}
