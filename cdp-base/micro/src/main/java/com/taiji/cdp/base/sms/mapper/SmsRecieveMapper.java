package com.taiji.cdp.base.sms.mapper;

import com.taiji.cdp.base.midAtt.mapper.BaseMapper;
import com.taiji.cdp.base.sms.entity.SmsRecieve;
import com.taiji.cdp.base.sms.vo.SmsRecieveVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SmsRecieveMapper extends BaseMapper<SmsRecieve, SmsRecieveVo> {
}
