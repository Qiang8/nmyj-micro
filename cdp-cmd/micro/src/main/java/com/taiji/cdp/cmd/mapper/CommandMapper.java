package com.taiji.cdp.cmd.mapper;

import com.taiji.cdp.cmd.entity.CommandEntity;
import com.taiji.cdp.cmd.vo.CommandVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandMapper extends BaseMapper<CommandEntity, CommandVo> {
}
