package com.taiji.cdp.report.mapper;

import com.taiji.cdp.report.entity.TaskReceive;
import com.taiji.cdp.report.vo.TaskReceiveVo;
import com.taiji.cdp.report.vo.TaskVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskRecMapper extends BaseMapper<TaskReceive,TaskReceiveVo>{
}
