package com.taiji.cdp.daily.monthly.mapper;

import com.taiji.cdp.daily.issue.mapper.BaseMapper;
import com.taiji.cdp.daily.monthly.entity.Monthly;
import com.taiji.cdp.daily.vo.MonthlyVo;
import org.mapstruct.Mapper;

/**
 * <p>Title:MonthlyMapper.java</p >
 * <p>Description: 月报管理Mapper类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface MonthlyMapper extends BaseMapper<Monthly, MonthlyVo> {
}