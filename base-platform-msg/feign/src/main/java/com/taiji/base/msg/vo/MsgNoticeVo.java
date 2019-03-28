package com.taiji.base.msg.vo;

import com.taiji.micro.common.vo.BaseCreateVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>Title:MsgNoticeVo.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 9:48</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class MsgNoticeVo extends BaseCreateVo<String> {
    public MsgNoticeVo(){}

    /**
     * 标题
     */
    @Getter
    @Setter
    private String title;

    /**
     * 发送机构
     */
    @Getter
    @Setter
    private String sendOrg;

    /**
     * 机构名称
     */
    @Getter
    @Setter
    private String orgName;

    /**
     * 内容
     */
    @Getter
    @Setter
    private String msgContent;

    /**
     * msgType.type
     */
    @Getter
    @Setter
    private String msgType;

    /**
     * msgType.moduleName
     */
    @Getter
    @Setter
    private String typeName;

    /**
     * msgType.icon
     */
    @Getter
    @Setter
    private String icon;

    /**
     * msgType.path
     */
    @Getter
    @Setter
    private String path;

    /**
     * 实体Id
     */
    @Getter
    @Setter
    private String entityId;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    private String delFlag;

    /**
     * 额外附加字段
     */
    @Getter
    @Setter
    private String extra;


    /**
     * 通知接收人记录
     */
    @Getter
    @Setter
    private List<Receiver> receivers;
}