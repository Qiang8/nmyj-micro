package com.taiji.cdp.report.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * 专题管理实体对象Vo
 *
 * @author sunyi
 * @date 2019年1月17日
 */
public class TopicVo extends BaseVo<String> {

    public TopicVo() {
    }
    @Getter
    @Setter
    @Length(max = 50,message = "专题管理名称 name 最大长度不能超过50")
    @NotEmpty(message = "专题管理名称 name 不能为空字符串")
    private String name;


    public TopicVo(String Id ,String name) {
        this.name = name;
        this.setId(Id);
    }
}
