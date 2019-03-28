package com.taiji.base.contact.mapper;

import com.taiji.base.contact.entity.ContactMember;
import com.taiji.base.contact.vo.ContactMemberVo;
import org.mapstruct.Mapper;

/**
 * <p>Title:ContactMemberMapper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/12/20 16:31</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface ContactMemberMapper extends BaseMapper<ContactMember, ContactMemberVo> {
}
