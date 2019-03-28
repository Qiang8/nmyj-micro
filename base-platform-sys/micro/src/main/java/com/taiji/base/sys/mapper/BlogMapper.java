package com.taiji.base.sys.mapper;

import com.taiji.base.sys.entity.Blog;
import com.taiji.base.sys.vo.BlogVo;
import org.mapstruct.Mapper;

/**
 * <p>Title:BlogMapper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 19:40</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface BlogMapper  extends BaseMapper<Blog, BlogVo>{
}
