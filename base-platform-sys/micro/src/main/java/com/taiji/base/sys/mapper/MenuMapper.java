package com.taiji.base.sys.mapper;

import com.taiji.base.sys.entity.Menu;
import com.taiji.base.sys.vo.MenuVo;
import org.mapstruct.Mapper;

/**
 * <p>Title:MenuMapper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/24 11:13</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface MenuMapper extends BaseMapper<Menu, MenuVo>{

}
