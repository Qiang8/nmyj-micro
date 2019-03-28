package com.taiji.cdp.report.common.enums;

import lombok.Getter;

/**
 * @program: nmyj-micro
 * @Description:
 * @Author: WangJian(wangjiand @ mail.taiji.com.cn)
 * @Date: 2019/3/4 19:50
 **/
public enum ApprovalResultEnum {
    RETURN("1","退回"),
    AGREE("0","同意");

    ApprovalResultEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    @Getter
    private String status;
    @Getter
    private String desc;

    public static ApprovalResultEnum statusOf(String status) {
        switch (status) {
            case "0":
                return ApprovalResultEnum.AGREE;
            case "1":
                return ApprovalResultEnum.RETURN;
            default:
                return null;
        }
    }
}
