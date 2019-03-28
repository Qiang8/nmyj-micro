package com.taiji.cdp.report.mapper;

import com.taiji.cdp.report.entity.TaskExeOrg;
import com.taiji.cdp.report.vo.TaskExeOrgVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskExeOrgMapper extends BaseMapper<TaskExeOrg,TaskExeOrgVo>{
}
