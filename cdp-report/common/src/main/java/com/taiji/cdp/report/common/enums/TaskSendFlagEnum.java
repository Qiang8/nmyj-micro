package com.taiji.cdp.report.common.enums;

import lombok.Getter;

/**
 * 任务管理发送枚举类
 */
public enum TaskSendFlagEnum {

    /**
     * 发送标识：0未下发1已下发
     */
    NOT_SEND("0", "未下发"),
    HAS_SEND("1", "已下发");

    @Getter
    private String code;
    @Getter
    private String desc;

    private TaskSendFlagEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TaskSendFlagEnum codeOf(String code) {
        switch (code) {
            case "0":
                return TaskSendFlagEnum.NOT_SEND;
            case "1":
                return TaskSendFlagEnum.HAS_SEND;
            default:
                return null;
        }
    }

    public static TaskSendFlagEnum descOf(String desc) {
        switch (desc) {
            case "未下发":
                return TaskSendFlagEnum.NOT_SEND;
            case "已下发":
                return TaskSendFlagEnum.HAS_SEND;
            default:
                return null;
        }
    }

}
