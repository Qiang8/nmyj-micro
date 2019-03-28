package com.taiji.cdp.daily.issue.mapper;

import com.taiji.cdp.daily.issue.entity.Issue;
import com.taiji.cdp.daily.vo.AddAndUpdateIssueVo;
import org.mapstruct.Mapper;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-08 18:17
 **/
@Mapper(componentModel = "spring")
public interface AddAndUpdateIssueMapper  extends BaseMapper<Issue, AddAndUpdateIssueVo> {
}