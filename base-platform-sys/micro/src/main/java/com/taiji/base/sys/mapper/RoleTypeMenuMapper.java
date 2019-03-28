package com.taiji.base.sys.mapper;

import com.taiji.base.sys.entity.RoleTypeMenu;
import com.taiji.base.sys.vo.RoleTypeMenuVo;
import org.mapstruct.Mapper;

/**
 * <p>Title:RoleTypeMenuMapper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/30 11:56</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface RoleTypeMenuMapper extends BaseMapper<RoleTypeMenu, RoleTypeMenuVo>{
}
