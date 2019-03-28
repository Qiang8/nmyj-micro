package com.taiji.cdp.report.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * 专题管理关键字VO
 * @author sunyi
 * @date 2019年1月17日
 */
public class TopicKeywordVo extends BaseVo<String> {

    public TopicKeywordVo() {
    }
    /**
     * 专题ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "专题Id topicId 最大长度不能超过36")
    private String topicId;

    /**
     * 专题名
     */
    @Getter
    @Setter
    private String topicName;

    /**
     * 专题关键词
     */
    @Getter
    @Setter
    @Length(max = 1000,message = "专题关键词 keyword 最大长度不能超过1000")
    @NotEmpty(message = "专题关键词 keyword 不能为空字符串")
    private String keyword;


}
