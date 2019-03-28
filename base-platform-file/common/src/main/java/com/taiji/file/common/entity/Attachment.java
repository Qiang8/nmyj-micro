package com.taiji.file.common.entity;

import com.taiji.micro.common.entity.BaseCreateEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>Title:Attachment.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/23 9:31</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "DOC_ATTACHMENT")
public class Attachment extends BaseCreateEntity<String> {

    /**
     * 文件原始文件名称
     */
    @Getter
    @Setter
    @Length(max = 200, message = "文件原始文件名称 originalName字段最大长度200")
    private String originalName;

    /**
     * 自定义文件名称
     */
    @Length(max = 200, message = "自定义文件名称 fileName字段最大长度200")
    @Getter
    @Setter
    private String fileName;

    /**
     * 文件状态
     */
    @Length(max = 1, message = "文件状态 fileStatus字段最大长度1")
    @Getter
    @Setter
    private String fileStatus;

    /**
     * 文件类型
     */
    @Length(max = 50, message = "文件类型 fileType字段最大长度50")
    @Getter
    @Setter
    private String fileType;

    /**
     * 文件后缀
     */
    @Length(max = 50, message = "文件后缀 fileSuffix字段最大长度20")
    @Getter
    @Setter
    private String fileSuffix;

    /**
     * 文件相对路径
     */
    @Length(max = 500, message = "文件相对路径 location字段最大长度500")
    @Getter
    @Setter
    private String location;

}
