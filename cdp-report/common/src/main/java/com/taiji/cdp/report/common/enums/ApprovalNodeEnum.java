package com.taiji.cdp.report.common.enums;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-19 18:39
 **/
public enum ApprovalNodeEnum {

    CENTER_AUDIT("center_audit","监管中心领导/处领导审批"),
    LEADER_AUDIT("leader_audit","办领导审批");

    private String status;
    private String desc;

    ApprovalNodeEnum(String status, String desc) {
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
    }}
