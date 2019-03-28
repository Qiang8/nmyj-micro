package com.taiji.cdp.report.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 研判信息实体对象
 *
 * @author xuweikai-pc
 * @date 2019年1月8日17:37:57
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CJ_CONS_JUDGE")
public class ConsJudgeInfo extends BaseEntity<String> implements DelFlag {

    public ConsJudgeInfo() {
    }

    /**
     * 舆情信息id
     */
    @Getter
    @Setter
    @Length(max = 36, message = "舆情信息ID infoId最大长度不能超过36")
    private String infoId;

    /**
     * 是否涉区
     */
    @Getter
    @Setter
    @Length(max = 1, message = "是否涉区 isInvolve最大长度不能超过1")
    private String isInvolve;

    /**
     * 舆情内容
     * 舆情标签类型：0新增舆情 1重点舆情
     */
    @Getter
    @Setter
    @Length(max = 1, message = "tagType 最大长度不能超过1")
    private String tagType;

    /**
     * 信息分类IDs
     */
    @Getter
    @Setter
    @Length(max = 500, message = "信息分类ID infoTypeIds 最大长度不能超过500")
    private String infoTypeIds;

    /**
     * 研判意见
     */
    @Getter
    @Setter
    @Length(max = 200, message = "研判意见 opinion 最大长度不能超过200")
    private String opinion;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1, message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
