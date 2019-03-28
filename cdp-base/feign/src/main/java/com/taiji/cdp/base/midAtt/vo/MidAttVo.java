package com.taiji.cdp.base.midAtt.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 附件中间表
 * @author qizhijie-pc
 * @date 2019年1月7日14:25:35
 */
public class MidAttVo extends IdVo<String>{

    public MidAttVo(){}

    @Getter @Setter
    @Length(max = 36,message = "业务实体ID entityId字段最大长度36")
    private String entityId;

    @Getter @Setter
    @NotNull(message = "附件对象不能为null")
    private AttachmentVo attDoc;

    @Getter @Setter
    private String attId; //临时变量--附件id

}
