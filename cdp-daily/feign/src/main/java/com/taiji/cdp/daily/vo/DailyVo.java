package com.taiji.cdp.daily.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 日报管理实体对象Vo
 * @author sunyi
 * @date 2019年1月18日
 */
public class DailyVo extends BaseVo<String> {

    public DailyVo() {
    }
    /**
     * 日报标题
     */
    @Getter
    @Setter
    @Length(max = 200,message = "日报标题 title 最大长度不能超过200")
    @NotEmpty(message = "日报标题 title 不能为空字符串")
    private String title;

    /**
     * 日报状态,0为发布，1发布
     */
    @Getter
    @Setter
    @Length(max = 1,message = "日报状态 status 最大长度不能超过1")
    private String status;
}
