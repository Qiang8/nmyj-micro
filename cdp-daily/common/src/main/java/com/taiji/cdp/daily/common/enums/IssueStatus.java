package com.taiji.cdp.daily.common.enums;

/**
 * @program: nmyj-micro
 * @description: 专刊状态枚举类
 * @author: TaiJi.WangJian
 * @create: 2019-01-08 20:35
 **/
public enum IssueStatus {
    RELEASE_STATUS("1","状态为发布"),
    NOT_RELEASE_STATUS("0","状态为未发布");
    private String status;
    private String desc;

    IssueStatus(String status, String desc) {
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
