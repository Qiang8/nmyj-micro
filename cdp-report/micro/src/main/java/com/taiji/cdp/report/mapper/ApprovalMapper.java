package com.taiji.cdp.report.mapper;

import com.taiji.cdp.report.entity.Approval;
import com.taiji.cdp.report.vo.ApprovalVo;
import org.mapstruct.Mapper;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-19 19:15
 **/
@Mapper(componentModel = "spring")
public interface ApprovalMapper extends BaseMapper<Approval, ApprovalVo>{
}