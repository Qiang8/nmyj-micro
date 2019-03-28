package com.taiji.base.sys.mapper;

import com.taiji.base.sys.entity.User;
import com.taiji.base.sys.vo.UserVo;
import org.mapstruct.Mapper;

/**
 * @author scl
 *
 * @date 2018-03-19
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User,UserVo> {
}
