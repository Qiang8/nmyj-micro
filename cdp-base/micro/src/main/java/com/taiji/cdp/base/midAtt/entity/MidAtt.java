package com.taiji.cdp.base.midAtt.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 附件中间表实体对象
 * @author qizhijie-pc
 * @date 2019年1月7日17:55:11
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "DOC_MID_ATT")
public class MidAtt extends IdEntity<String>{

    public MidAtt(){}

    @Getter
    @Setter
    @Length(max = 36,message = "业务实体ID entityId字段最大长度36")
    private String entityId;

    @Getter @Setter
    @NotNull(message = "附件对象不能为null")
    @OneToOne(targetEntity = Attachment.class)
    @JoinColumn(name = "att_id", referencedColumnName = "ID")
    private Attachment attDoc;

    @Getter @Setter
    @Transient
    private String attId; //临时变量--附件id

}
