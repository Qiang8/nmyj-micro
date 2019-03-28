package com.taiji.cdp.report.common.enums;

import lombok.Getter;

/**
 * 任务管理读取枚举类
 */
public enum TaskReadFlagEnum {

    /**
     * 已读标识：0否1是
     */
    NOT_READ("0", "否"),
    HAS_READ("1", "是");

    @Getter
    private String code;
    @Getter
    private String desc;

    private TaskReadFlagEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TaskReadFlagEnum codeOf(String code) {
        switch (code) {
            case "0":
                return TaskReadFlagEnum.NOT_READ;
            case "1":
                return TaskReadFlagEnum.HAS_READ;
            default:
                return null;
        }
    }

    public static TaskReadFlagEnum descOf(String desc) {
        switch (desc) {
            case "否":
                return TaskReadFlagEnum.NOT_READ;
            case "是":
                return TaskReadFlagEnum.HAS_READ;
            default:
                return null;
        }
    }

}
