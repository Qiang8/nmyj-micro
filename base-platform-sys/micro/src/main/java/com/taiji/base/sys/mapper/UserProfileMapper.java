package com.taiji.base.sys.mapper;

import com.taiji.base.sys.entity.UserProfile;
import com.taiji.base.sys.vo.UserProfileVo;
import org.mapstruct.Mapper;

/**
 * <p>Title:UserProfileMapper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/24 11:14</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper extends BaseMapper<UserProfile, UserProfileVo> {
}
