package com.taiji.base.msg.enums;

import lombok.Getter;

/**
 * <p>Title:ReadFlagEnum.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 10:39</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public enum ReadFlagEnum {
    /**
     * 消息未读
     */
    UNREAD("0", "未读"),
    /**
     * 消息已读
     */
    READ("1", "已读");

    @Getter
    private String code;

    @Getter
    private String desc;

    ReadFlagEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ReadFlagEnum codeOf(String code) {
        switch (code) {
            case "0":
                return ReadFlagEnum.UNREAD;
            case "1":
                return ReadFlagEnum.READ;
            default:
                return null;
        }
    }

    public static ReadFlagEnum descOf(String desc) {
        switch (desc) {
            case "未读":
                return ReadFlagEnum.UNREAD;
            case "已读":
                return ReadFlagEnum.READ;
            default:
                return null;
        }
    }
}
