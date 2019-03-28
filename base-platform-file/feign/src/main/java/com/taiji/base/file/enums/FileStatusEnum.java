package com.taiji.base.file.enums;

import lombok.Getter;

/**
 * <p>Title:FileStatusEnum.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/23 11:18</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public enum FileStatusEnum {

    /**
     * 已上传 0.
     */
    UPLOADED("0", "已上传"),
    FORM_SAVE("1", "表单关联保存"),
    DELETED("2","已删除");

    @Getter
    private String code;
    @Getter
    private String desc;

    private FileStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FileStatusEnum codeOf(String code) {
        switch (code) {
            case "0":
                return FileStatusEnum.UPLOADED;
            case "1":
                return FileStatusEnum.FORM_SAVE;
            case "2":
                return FileStatusEnum.DELETED;
            default:
                return null;
        }
    }

    public static FileStatusEnum descOf(String desc) {
        switch (desc) {
            case "已上传":
                return FileStatusEnum.UPLOADED;
            case "表单关联保存":
                return FileStatusEnum.FORM_SAVE;
            case "已删除":
                return FileStatusEnum.DELETED;
            default:
                return null;
        }
    }

}
