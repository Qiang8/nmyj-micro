package com.taiji.base.msg.mapper;

import com.taiji.base.msg.entity.MsgType;
import com.taiji.base.msg.vo.MsgTypeVo;
import org.mapstruct.Mapper;

/**
 * <p>Title:MsgTypeMapper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 18:44</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface MsgTypeMapper extends BaseMapper<MsgType, MsgTypeVo>{
}
