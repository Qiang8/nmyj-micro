package com.taiji.base.msg.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>Title:MsgType.java</p >
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
@Table(name = "MSG_CONFIG")
public class MsgType extends IdEntity<String> {

    /**
     * 类别 0：通知，1：待办
     */
    @Getter
    @Setter
    @Length(max = 1, message = "类别 type字段最大长度1")
    private String type;

    /**
     * 模块名称
     */
    @Getter
    @Setter
    @Length(max = 50, message = "类型名称 name字段最大长度50")
    private String moduleName;

    /**
     * 编码
     */
    @Getter
    @Setter
    @Length(max = 50, message = "模块编码 code字段最大长度50")
    private String code;

    /**
     * 关联菜单路径
     */
    @Getter
    @Setter
    @Length(max = 100, message = "关联菜单 path字段最大长度100")
    private String path;

    /**
     * 图标
     */
    @Length(max = 50, message = "图标 icon字段最大长度50")
    @Getter
    @Setter
    private String icon;
}
