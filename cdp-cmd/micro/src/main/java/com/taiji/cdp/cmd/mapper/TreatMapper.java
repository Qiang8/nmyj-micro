package com.taiji.cdp.cmd.mapper;

import com.taiji.cdp.cmd.entity.Treat;
import com.taiji.cdp.cmd.vo.TreatVo;
import org.mapstruct.Mapper;

/**
 * @program: nmyj-micro
 * @Description:
 * @Author: WangJian(wangjiand @ mail.taiji.com.cn)
 * @Date: 2019/3/5 11:36
 **/
@Mapper(componentModel = "spring")
public interface TreatMapper extends BaseMapper<Treat, TreatVo>{
}
