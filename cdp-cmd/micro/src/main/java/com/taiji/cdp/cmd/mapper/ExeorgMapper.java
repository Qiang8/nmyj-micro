package com.taiji.cdp.cmd.mapper;

import com.taiji.cdp.cmd.entity.ExeorgEntity;
import com.taiji.cdp.cmd.vo.ExeorgVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExeorgMapper extends BaseMapper<ExeorgEntity, ExeorgVo> {
}
