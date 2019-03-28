package com.taiji.cdp.report.mapper;

import com.taiji.cdp.report.entity.EvidenceInfo;
import com.taiji.cdp.report.vo.EvidenceVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvidenceMapper extends BaseMapper<EvidenceInfo, EvidenceVo> {
}
