package com.taiji.base.msg.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>Title:MsgNoticeRecordVo.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 9:48</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class MsgNoticeRecordVo extends IdVo<String> {
    public MsgNoticeRecordVo() {
    }

    /**
     * msgNotice.title
     * 标题
     */
    @Getter
    @Setter
    private String title;

    /**
     * msgNotice.sendOrg
     * 发送机构
     */
    @Getter
    @Setter
    private String sendOrg;

    /**
     * msgNotice.orgName
     * 机构名称
     */
    @Getter
    @Setter
    private String orgName;

    /**
     * msgNotice.msgContent
     * 内容
     */
    @Getter
    @Setter
    private String msgContent;

    /**
     * msgNotice.extra
     *  附加信息
     */
    @Getter
    @Setter
    private String extra;

    /**
     * msgNotice.msgType.type
     */
    @Getter
    @Setter
    private String msgType;

    /**
     * msgNotice.msgType.moduleName
     */
    @Getter
    @Setter
    private String typeName;

    /**
     * msgNotice.msgType.icon
     */
    @Getter
    @Setter
    private String icon;

    /**
     * msgNotice.msgType.path
     */
    @Getter
    @Setter
    private String path;

    /**
     * 接收者
     */
    @Getter
    @Setter
    private String receiverId;

    /**
     * 接收者名称
     */
    @Getter
    @Setter
    private String receiverName;

    /**
     * 已读标志 0 未读 1 已读
     */
    @Getter
    @Setter
    private String readFlag;

    /**
     * 读取时间
     */
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @CreatedDate
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @Getter
    @Setter
    private LocalDateTime readTime;

    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @CreatedDate
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @Getter
    @Setter
    private LocalDateTime createTime;

    @Getter
    @Setter
    private String createBy;

    @Getter
    @Setter
    private String entityId;
}
