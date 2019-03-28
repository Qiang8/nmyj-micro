package com.taiji.base.sys.service;

import com.taiji.base.sys.entity.RoleTypeMenu;
import com.taiji.base.sys.repository.RoleTypeMenuRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>Title:RoleTypeMenuService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/30 13:49</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class RoleTypeMenuService extends BaseService<RoleTypeMenu,String> {

    RoleTypeMenuRepository repository;

    /**
     * 根据参数获取RoleTypeMenu多条记录。
     * <p>
     *
     * @param roleType 角色类型（必选）
     *
     * @return List<RoleTypeMenu>
     */
    public List<RoleTypeMenu> findAll(String roleType) {
        Assert.hasText(roleType, "roleType不能为null或空字符串!");

        return repository.findAllByRoleType(roleType);
    }

    /**
     * 保存多个角色类型资源entity,保存前会先根据roleType删除数据库相应的记录再保存entityList。
     *
     * @param entityList 角色类型资源entityList
     * @param roleType 角色类型
     */
    public void save(List<RoleTypeMenu> entityList, String roleType) {
        Assert.hasText(roleType, "roleType不能为null或空字符串!");

        repository.save(entityList,roleType);
    }
}
