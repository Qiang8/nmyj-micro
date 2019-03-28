package com.taiji.cdp.report.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 舆情信息研判VO
 *
 * @author xuweikai-pc
 * @date 2019年1月11日16:37:25
 */
public class ConsJudgeVo extends BaseVo<String> {

    public ConsJudgeVo() {
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

    @Getter
    @Setter
    @Length(max = 1, message = "删除标记（0已删除，1未删除）")
    private String delFlag;
}
