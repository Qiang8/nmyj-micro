package com.taiji.cdp.report.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 舆情信息研判VO
 *
 * @author xuweikai-pc
 * @date 2019年1月11日16:37:25
 */
public class ConsJudgeSaveVo extends BaseVo<String> {

    public ConsJudgeSaveVo() {
    }

    /**
     * 舆情信息id
     */
    @Getter
    @Setter
    private String infoId;

    /**
     * 是否涉区
     */
    @Getter
    @Setter
    private String isInvolve;

    /**
     * 舆情内容
     * 舆情标签类型：0新增舆情 1重点舆情
     */
    @Getter
    @Setter
    private String tagType;

    /**
     * 重点舆情专题ID
     */
    @Getter
    @Setter
    private String topicId;

    /**
     * 重点舆情专题名称
     */
    @Getter
    @Setter
    private String topicName;


    /**
     * 信息分类IDs
     */
    @Getter
    @Setter
    private List<String> infoTypeIds;

    /**
     * 研判意见
     */
    @Getter
    @Setter
    private String opinion;

    @Getter
    @Setter
    private String delFlag;
}
