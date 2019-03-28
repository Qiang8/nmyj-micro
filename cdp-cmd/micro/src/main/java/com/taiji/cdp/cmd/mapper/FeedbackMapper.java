package com.taiji.cdp.cmd.mapper;

import com.taiji.cdp.cmd.entity.Feedback;
import com.taiji.cdp.cmd.vo.FeedbackVo;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface FeedbackMapper extends BaseMapper<Feedback, FeedbackVo>{

}
