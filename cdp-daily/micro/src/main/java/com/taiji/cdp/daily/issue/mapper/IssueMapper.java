package com.taiji.cdp.daily.issue.mapper;

import com.taiji.cdp.daily.issue.entity.Issue;
import com.taiji.cdp.daily.vo.IssueVo;
import org.mapstruct.Mapper;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 16:01
 **/
@Mapper(componentModel = "spring")
public interface IssueMapper extends BaseMapper<Issue, IssueVo> {
}