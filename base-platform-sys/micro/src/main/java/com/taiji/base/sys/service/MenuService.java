package com.taiji.base.sys.service;

import com.taiji.base.sys.entity.Menu;
import com.taiji.base.sys.entity.RoleTypeMenu;
import com.taiji.base.sys.repository.MenuRepository;
import com.taiji.base.sys.repository.RoleMenuRepository;
import com.taiji.base.sys.repository.RoleTypeMenuRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统资源菜单Service类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Slf4j
@Service
@AllArgsConstructor
public class MenuService extends BaseService<Menu,String> {

    MenuRepository repository;

    RoleTypeMenuRepository  roleTypeMenuRepository;

    RoleMenuRepository roleMenuRepository;

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return User
     */
    public Menu findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 根据参数获取Menu多条记录。
     * <p>
     *
     * @param parentId （可选,二选一）
     * @param roleType （可选,二选一）
     *
     * @return List<Menu>
     */
    public List<Menu> findAll(String parentId, String roleType) {
        List<String> menuIdList = Collections.EMPTY_LIST;

        if(StringUtils.hasText(roleType))
        {
            List<RoleTypeMenu> roleTypeMenuList = roleTypeMenuRepository.findAllByRoleType(roleType);

            if(!CollectionUtils.isEmpty(roleTypeMenuList))
            {
                menuIdList = roleTypeMenuList.stream().map(RoleTypeMenu::getMenuId).collect(Collectors.toList());
            }
        }

        return repository.findAll(parentId, menuIdList);
    }

    /**
     * 新增Menu，Menu不能为空。
     *
     * @param entity
     * @return Menu
     */
    public Menu create(Menu entity) {
        Assert.notNull(entity, "entity不能为null!");

        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());

        return repository.save(entity);
    }

    /**
     * 更新Menu，Menu不能为空。
     *
     * @param entity
     * @param id 更新Menu Id
     * @return Menu
     */
    public Menu update(Menu entity, String id) {
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
        Menu entity = repository.findOne(id);
        repository.deleteLogic(entity, delFlagEnum);

        roleMenuRepository.deleteRoleMenuByRoleId(id);

        roleTypeMenuRepository.deleteByMenuId(id);
    }
}
