package com.taiji.cdp.report.mapper;

import com.taiji.cdp.report.entity.FlowLog;
import com.taiji.cdp.report.vo.FlowLogVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlowLogMapper extends BaseMapper<FlowLog, FlowLogVo> {
}
