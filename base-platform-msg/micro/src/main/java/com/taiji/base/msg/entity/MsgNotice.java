package com.taiji.base.msg.entity;

import com.taiji.micro.common.entity.BaseCreateEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

/**
 * <p>Title:MsgNotice.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/29 15:43</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "MSG_NOTICE")
public class MsgNotice extends BaseCreateEntity<String> implements DelFlag {

    /**
     * 标题
     */
    @Length(max = 200,message = "标题 title字段最大长度200")
    @Getter
    @Setter
    private String title;

    /**
     * 发送机构
     */
    @Length(max = 36,message = "发送机构 sendOrg字段最大长度36")
    @Getter
    @Setter
    private String sendOrg;

    /**
     * 机构名称
     */
    @Length(max = 100,message = "机构名称 orgName字段最大长度100")
    @Getter
    @Setter
    private String orgName;

    /**
     * 内容
     */
    @Length(max = 4000,message = "内容 msgContent字段最大长度4000")
    @Getter
    @Setter
    private String msgContent;

    /**
     * 消息类型
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = MsgType.class)
    @JoinColumn(name = "MSG_TYPE", referencedColumnName = "id")
    private MsgType msgType;

    /**
     * 实体Id
     */
    @Length(max = 36,message = "实体Id entityId字段最大长度36")
    @NotBlank(message = "实体Id不能为空字符串")
    @Getter
    @Setter
    private String entityId;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1, message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 附加信息
     */
    @Length(max = 4000,message = "附加信息 extra字段最大长度4000")
    @Getter
    @Setter
    private String extra;


    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, targetEntity = MsgNoticeRecord.class,mappedBy = "msgNotice")
    private List<MsgNoticeRecord> msgNoticeRecordList;
}
