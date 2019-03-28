package com.taiji.cdp.daily.duty.mapper;

import com.taiji.cdp.daily.duty.entity.DutyShiftItemEntity;
import com.taiji.cdp.daily.issue.mapper.BaseMapper;
import com.taiji.cdp.daily.vo.DutyShiftItemVo;
import org.mapstruct.Mapper;

/**
 * <p>Title:DutyShiftMapper.java</p >
 * <p>Description: 交接班信息管理Mapper类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/21 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface DutyShiftItemMapper extends BaseMapper<DutyShiftItemEntity, DutyShiftItemVo> {
}