package com.taiji.cdp.daily.daily.mapper;

import com.taiji.cdp.daily.daily.entity.Daily;
import com.taiji.cdp.daily.issue.mapper.BaseMapper;
import com.taiji.cdp.daily.vo.DailyVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DailyMapper extends BaseMapper<Daily, DailyVo> {
}
