package com.taiji.cdp.base.sms.mapper;

import com.taiji.cdp.base.midAtt.mapper.BaseMapper;
import com.taiji.cdp.base.sms.entity.SmsTemp;
import com.taiji.cdp.base.sms.vo.SmsTempVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SmsTempMapper extends BaseMapper<SmsTemp,SmsTempVo>{
}
