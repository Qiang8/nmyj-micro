package com.taiji.base.contact.mapper;

import com.taiji.base.contact.entity.ContactGroup;
import com.taiji.base.contact.vo.ContactGroupVo;
import org.mapstruct.Mapper;

/**
 * <p>Title:ContactGroupMapper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/12/20 16:30</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface ContactGroupMapper extends BaseMapper<ContactGroup, ContactGroupVo> {
}
