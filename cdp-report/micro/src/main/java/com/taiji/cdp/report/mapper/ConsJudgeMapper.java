package com.taiji.cdp.report.mapper;

import com.taiji.cdp.report.entity.ConsJudgeInfo;
import com.taiji.cdp.report.vo.ConsJudgeVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsJudgeMapper extends BaseMapper<ConsJudgeInfo, ConsJudgeVo> {
}
