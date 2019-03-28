package com.taiji.cdp.daily.common.enums;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-09 15:53
 **/
public enum IssueDelFlag {
    ISSUE_EFFECTIVE("1","有效状态"),
    ISSUE_INVALID("0","无效状态");
    private String status;
    private String desc;

    IssueDelFlag(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
