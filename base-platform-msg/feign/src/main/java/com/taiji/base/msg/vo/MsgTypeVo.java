package com.taiji.base.msg.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title:MsgTypeVo.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 9:49</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class MsgTypeVo extends IdVo<String> {
    public MsgTypeVo(){}

    /**
     * 类别
     */
    @Getter
    @Setter
    private String type;

    /**
     * 模块名称
     */
    @Getter
    @Setter
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
    @Getter
    @Setter
    private String path;

    /**
     * 图标
     */
    @Getter
    @Setter
    private String icon;
}
