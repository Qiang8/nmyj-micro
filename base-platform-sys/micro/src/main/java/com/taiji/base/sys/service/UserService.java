package com.taiji.base.sys.service;

import com.taiji.base.sys.entity.User;
import com.taiji.base.sys.entity.UserProfile;
import com.taiji.base.sys.repository.UserProfileRepository;
import com.taiji.base.sys.repository.UserRepository;
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
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统用户Service类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService extends BaseService<User,String> {

    UserRepository repository;

    UserProfileRepository userProfileRepository;

    UserRoleRepository userRoleRepository;

    public User findOne(MultiValueMap<String, Object> params)
    {
        return repository.findOne(params);
    }

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return User
     */
    public User findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 根据参数获取User多条记录。
     * <p>
     *
     * @param account （可选）
     * @param name    （可选）
     * @return List<User>
     */
    public List<User> findAll(String account, String name) {
        return repository.findAll(account,name);
    }

    /**
     * 根据参数获取分页User多条记录。
     * <p>
     *
     * @param account （可选）
     * @param name    （可选）
     * @return RestPageImpl<User>
     */
    public Page<User> findPage(String account, String name, Pageable pageable) {
        return repository.findPage(account,name,pageable);
    }

    /**
     * 新增User，User不能为空。
     *
     * @param entity
     * @return User
     */
    public User create(User entity) {
        Assert.notNull(entity,"entity不能为null!");

        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        entity.setStatus(StatusEnum.ENABLE.getCode());

        UserProfile userProfile = entity.getProfile();
        if(null == userProfile || !StringUtils.hasText(userProfile.getId()))
        {
            userProfileRepository.save(userProfile);
        }

        return repository.save(entity);
    }

    /**
     * 更新User，User不能为空。
     *
     * @param entity
     * @param id 更新User Id
     * @return User
     */
    public User update(User entity, String id) {
        Assert.notNull(entity,"entity不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        UserProfile userProfile = entity.getProfile();
        if(null == userProfile || StringUtils.hasText(userProfile.getId()))
        {
            userProfileRepository.save(userProfile);
        }

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
        User entity = repository.findOne(id);
        repository.deleteLogic(entity, delFlagEnum);

        userRoleRepository.deleteUserRoleByUserId(id);
    }

    /**
     * 根据参数判断用户名是否被占用。
     *
     * @param id      （可选）
     * @param account （必选）
     * @return Boolean
     */
    public Boolean checkUnique(String id,String account) {
        Assert.hasText(account,"account不能为null或空字符串!");

        return repository.checkUnique(id,account);
    }

    /**
     * 根据orgId获取User对象(目前返回 id与account,profiles)
     * 消息提醒使用
     * @author qizhijie-pc
     * @date 2018年11月28日11:24:40
     * @param orgId
     * @return List<User>
     */
    public List<User> findListByOrgId(String orgId){
        Assert.hasText(orgId,"orgId不能为null或空字符串!");
        return repository.findListByOrgId(orgId);
    }

    /**
     * 根据orgIds获取User对象(目前返回 id与account,profiles)
     * 消息提醒使用
     * @author qizhijie-pc
     * @date 2018年12月18日14:47:16
     * @param orgIds
     * @return List<User>
     */
    public List<User> findListByOrgIds(List<String> orgIds){
        Assert.notNull(orgIds,"orgIds 不能为null");
        return repository.findListByOrgIds(orgIds);
    }
}
