package com.taiji.cdp.base.sms.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "BM_SMS_TEMPLATE")
public class SmsTemp extends BaseEntity<String> implements DelFlag{

    public SmsTemp(){}

    /**
     * 模板名称
     */
    @Getter @Setter
    @Length(max=100,message = "模板名称 name 最大字符长度不能超过100")
    private String name;

    /**
     * 模板编码
     */
    @Getter @Setter
    @Length(max=50,message = "模板编码 code 最大字符长度不能超过50")
    private String code;

    /**
     * 模板内容
     */
    @Length(max=500,message = "模板内容 content 最大字符长度不能超过500")
    @Getter @Setter
    private String content;

    /**
     * 备注
     */
    @Length(max=1000,message = "备注 notes 最大字符长度不能超过1000")
    @Getter @Setter
    private String notes;

    @Getter
    @Setter
    @Column(name = "DEL_FLAG")
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
