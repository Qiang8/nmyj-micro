package com.taiji.cdp.report.common.enums;

import lombok.Getter;

public enum ConsInfoStatusEnum {

    /**
     * 舆情信息状态：0未上报 1已上报 2已研判 3短信上报 4已审批通过 5已退回 6处置中 7已完成 8已忽略
     */
    NOT_REPORT("0", "未上报"),
    REPORTED("1", "已上报"),
    JUDGED("2","已研判"),
    MSG_REPORTED("3", "短信上报"),
    AUDIT_PASS("4", "已审批通过"),
    AUDIT_RETURN("5","已退回"),
    HANDLING("6", "处置中"),
    COMPLETED("7", "已完成"),
    IGNORED("8","已忽略");

    @Getter
    private String code;
    @Getter
    private String desc;

    private ConsInfoStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ConsInfoStatusEnum codeOf(String code) {
        switch (code) {
            case "0":
                return ConsInfoStatusEnum.NOT_REPORT;
            case "1":
                return ConsInfoStatusEnum.REPORTED;
            case "2":
                return ConsInfoStatusEnum.JUDGED;
            case "3":
                return ConsInfoStatusEnum.MSG_REPORTED;
            case "4":
                return ConsInfoStatusEnum.AUDIT_PASS;
            case "5":
                return ConsInfoStatusEnum.AUDIT_RETURN;
            case "6":
                return ConsInfoStatusEnum.HANDLING;
            case "7":
                return ConsInfoStatusEnum.COMPLETED;
            case "8":
                return ConsInfoStatusEnum.IGNORED;
            default:
                return null;
        }
    }

    public static ConsInfoStatusEnum descOf(String desc) {
        switch (desc) {
            case "未上报":
                return ConsInfoStatusEnum.NOT_REPORT;
            case "已上报":
                return ConsInfoStatusEnum.REPORTED;
            case "已研判":
                return ConsInfoStatusEnum.JUDGED;
            case "短信上报":
                return ConsInfoStatusEnum.MSG_REPORTED;
            case "已审批通过":
                return ConsInfoStatusEnum.AUDIT_PASS;
            case "已退回":
                return ConsInfoStatusEnum.AUDIT_RETURN;
            case "处置中":
                return ConsInfoStatusEnum.HANDLING;
            case "已完成":
                return ConsInfoStatusEnum.COMPLETED;
            case "已忽略":
                return ConsInfoStatusEnum.IGNORED;
            default:
                return null;
        }
    }

}
