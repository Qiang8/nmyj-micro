package com.taiji.cdp.base.midAtt.mapper;

import com.taiji.cdp.base.midAtt.entity.Attachment;
import com.taiji.cdp.base.midAtt.vo.AttachmentVo;
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
