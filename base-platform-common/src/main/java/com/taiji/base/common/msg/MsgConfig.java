package com.taiji.base.common.msg;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息配置表
 */
public class MsgConfig {

    public MsgConfig(){}

    /**
     * 模块名称
     */
    @Getter@Setter
    private String moduleName;
    /**
     * 编码
     */
    @Getter
    @Setter
    private String code;
    /**
     * 关联菜单路径
     */
    @Getter@Setter
    private String path;
    /**
     * 图标
     */
    @Getter@Setter
    private String icon;

}
