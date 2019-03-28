package com.taiji.cdp.base.sms.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 短信模板vo
 */
public class SmsTempVo extends BaseVo<String>{

    public SmsTempVo(){}

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

}
