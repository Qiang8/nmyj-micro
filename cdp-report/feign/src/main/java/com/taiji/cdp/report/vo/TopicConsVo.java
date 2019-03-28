package com.taiji.cdp.report.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 专题与舆情信息中间表vo
 * @author qizhijie-pc
 * @date 2019年1月22日10:18:37
 */
public class TopicConsVo extends IdVo<String>{

    public TopicConsVo(){}

    public TopicConsVo(String id,String infoId,String topicId,String topicName){
        this.setId(id);
        this.infoId= infoId;
        this.topicId= topicId;
        this.topicName= topicName;
    }

    /**
     * 舆情ID
     */
    @Getter@Setter
    @NotEmpty(message = "舆情ID 不能为空")
    @Length(max = 36,message = "舆情ID infoId 最大长度不能超过36个字符")
    private String infoId;

    /**
     * 专题ID
     */
    @Getter@Setter
    @NotEmpty(message = "专题ID 不能为空")
    @Length(max = 36,message = "专题ID topicId 最大长度不能超过36个字符")
    private String topicId;

    /**
     * 专题名称
     */
    @Getter@Setter
    @Length(max = 200,message = "专题名称 topicName 最大长度不能超过36个字符")
    private String topicName;

}
