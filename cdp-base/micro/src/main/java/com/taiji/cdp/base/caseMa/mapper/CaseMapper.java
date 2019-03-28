package com.taiji.cdp.base.caseMa.mapper;

import com.taiji.cdp.base.caseMa.vo.CaseVo;
import com.taiji.cdp.base.caseMa.entity.Case;
import com.taiji.cdp.base.midAtt.mapper.BaseMapper;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CaseMapper extends BaseMapper<Case,CaseVo> {
}
