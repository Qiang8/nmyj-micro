package com.taiji.cdp.base.common.enums;

import lombok.Getter;

/**
 * 短信发送枚举类
 */
public enum SmsSendStatusEnum {

    /**
     * 0:未发送，1:已发送
     */
    NOT_SEND("0","未发送"),
    HAS_SEND("1","已发送");

    @Getter
    private String code;
    @Getter
    private String desc;

    private SmsSendStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SmsSendStatusEnum codeOf(String code) {
        switch (code) {
            case "0":
                return SmsSendStatusEnum.NOT_SEND;
            case "1":
                return SmsSendStatusEnum.HAS_SEND;
            default:
                return null;
        }
    }

    public static SmsSendStatusEnum descOf(String desc) {
        switch (desc) {
            case "未发送":
                return SmsSendStatusEnum.NOT_SEND;
            case "已发送":
                return SmsSendStatusEnum.HAS_SEND;
            default:
                return null;
        }
    }

}
