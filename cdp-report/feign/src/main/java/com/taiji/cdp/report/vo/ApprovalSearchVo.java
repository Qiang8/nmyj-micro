package com.taiji.cdp.report.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-20 02:41
 **/
public class ApprovalSearchVo {

    @Getter
    @Setter
    @Length(max = 200, message = "舆情标题 最大长度不能超过200")
    private String title;
    /**
     * 查询标签标志：00个人待审批 列表，01个人已审批列表。
     * （后台操作：
     * tagFlag为00，则CJ_CONS_APPROVAL中approver_id为当前登录用户ID，且approval_result为空；
     * tagFlag为01，则CJ_CONS_APPROVAL中approver_id为当前登录用户ID，且approval_result为非空；
     * 获取INFO_ID集合后再根据查询条件查询CR_CONS_INFO和CJ_CONS_EVIDENCE表中对应的信息返回）
     */
    @Getter
    @Setter
    @Length(max = 2, message = "标志位 最大长度不能超过2")
    private String tagFlag;

    @Getter
    @Setter
    private String page;

    @Getter
    @Setter
    private String size;
}