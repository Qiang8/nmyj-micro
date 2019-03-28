package com.taiji.cdp.daily.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @program: nmyj-micro
 * @description: 月报信息VO
 * @author: TaiJi.XuWeiKai
 * @create: 2019-01-07 17:49
 **/
public class MonthlyVo extends BaseVo<String> {

    @Getter
    @Setter
    @NotBlank
    @Length(max = 200,message = "月报标题")
    private String title;

    @Getter
    @Setter
    @NotBlank
    @Length(max = 1,message = "月报状态（0未发布，1已发布）")
    private String status;

    @Getter
    @Setter
    @Length(max = 1,message = "月报删除标记（0已删除，1未删除）")
    private String delFlag;
}