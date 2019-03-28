package com.taiji.cdp.report.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-18 17:21
 **/
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CJ_COMMON_ADVICE")
public class CommonAdvice extends IdEntity<String> {

    @Getter
    @Setter
    @Length(max = 36, message = "审批信息ID 最大长度不能超过36")
    @NotEmpty(message = "审批信息ID 不能为空字符串")
    private String approvalId;

    @Getter
    @Setter
    @Length(max = 36, message = "审批节点 最大长度不能超过36")
    private String approvalNode;

    @Getter
    @Setter
    @Length(max = 36, message = "常用审批项ID 最大长度不能超过36")
    @NotEmpty(message = "常用审批项ID 不能为空字符串")
    private String commonAdviceId;
}