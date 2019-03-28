package com.taiji.file.common.mapper;

import com.taiji.base.file.vo.AttachmentVo;
import com.taiji.file.common.entity.Attachment;
import org.mapstruct.Mapper;

/**
 * <p>Title:AttachmentMapper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/23 10:11</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends BaseMapper<Attachment, AttachmentVo>{
}
