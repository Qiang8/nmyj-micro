package com.taiji.cdp.base.sms.mapper;

import com.taiji.cdp.base.midAtt.mapper.BaseMapper;
import com.taiji.cdp.base.sms.entity.Sms;
import com.taiji.cdp.base.sms.vo.SmsVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SmsMapper extends BaseMapper<Sms,SmsVo> {
}
