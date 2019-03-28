package com.taiji.base.sys.service;

import com.taiji.base.sys.entity.Role;
import com.taiji.base.sys.repository.RoleMenuRepository;
import com.taiji.base.sys.repository.RoleRepository;
import com.taiji.base.sys.repository.UserRoleRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>Title:RoleService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/28 10:11</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class RoleService extends BaseService<Role,String> {

    RoleRepository repository;

    UserRoleRepository userRoleRepository;

    RoleMenuRepository roleMenuRepository;

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return User
     */
    public Role findOne(String id) {
        Assert.hasText(id, "id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 根据参数获取RoleVo多条记录。
     * <p>
     *
     * @param roleName （可选）
     * @return List<Role>
     */
    public List<Role> findAll(String roleName) {
        return repository.findAll(roleName);
    }

    /**
     * 根据参数获取分页RoleVo多条记录。
     * <p>
     *
     * @param roleName （可选）
     * @return RestPageImpl<Role>
     */
    public Page<Role> findPage(String roleName,Pageable pageable) {
        return repository.findPage(roleName,pageable);
    }

    /**
     * 新增Role，Role不能为空。
     *
     * @param entity
     * @return Role
     */
    public Role create(Role entity) {
        Assert.notNull(entity,"entity不能为null!");

        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        entity.setStatus(StatusEnum.ENABLE.getCode());

        return repository.save(entity);
    }

    /**
     * 更新Role，Role不能为空。
     *
     * @param entity
     * @param id 更新Role Id
     * @return Role
     */
    public Role update(Role entity, String id) {
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
        Role entity = repository.findOne(id);
        repository.deleteLogic(entity, delFlagEnum);

        userRoleRepository.deleteUserRoleByRoleId(id);

        roleMenuRepository.deleteRoleMenuByRoleId(id);
    }

    /**
     * 根据参数判断角色编号是否被占用。
     *
     * @param id      （可选）
     * @param roleCode （必选）
     * @return Boolean
     */
    public Boolean checkUnique(String id,String roleCode) {
        Assert.hasText(roleCode,"roleCode不能为null或空字符串!");

        return repository.checkUnique(id,roleCode);
    }
}
