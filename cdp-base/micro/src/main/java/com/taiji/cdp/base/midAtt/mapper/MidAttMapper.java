package com.taiji.cdp.base.midAtt.mapper;

import com.taiji.cdp.base.midAtt.entity.MidAtt;
import com.taiji.cdp.base.midAtt.vo.MidAttVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MidAttMapper extends BaseMapper<MidAtt,MidAttVo>{
}
