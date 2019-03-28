package com.taiji.base.sys.service;

import com.taiji.base.sys.entity.UserProfile;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统用户详情Service类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserProfileService extends BaseService<UserProfile,String> {
}
