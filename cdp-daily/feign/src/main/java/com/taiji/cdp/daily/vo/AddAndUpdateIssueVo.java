package com.taiji.cdp.daily.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 18:56
 **/
public class AddAndUpdateIssueVo {
    /**
     * 专刊Vo
     */
    @Getter
    @Setter
    private IssueVo issue;
    /**
     * 附件ids
     */
    @Getter
    @Setter
    private List<String> fileIds;
    /**
     * 被删除附件的ids
     */
    @Getter
    @Setter
    private List<String> fileDeleteIds;
}