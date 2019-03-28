package com.taiji.base.sys.repository;

import com.taiji.base.sys.entity.UserProfile;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统用户详情Repository类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Repository
public class UserProfileRepository extends BaseJpaRepository<UserProfile,String>{
}
