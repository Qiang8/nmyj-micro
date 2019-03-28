package com.taiji.cdp.report.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-19 23:53
 **/
public class PreCmdsVo {
    @Getter
    @Setter
    @Length(max = 36, message = "舆情id 最大长度不能超过36")
    private String id;

    @Getter
    @Setter
    @Length(max = 1000, message = "研判意见 最大长度不能超过1000")
    private String judgeOpinion;

    @Getter
    @Setter
    @Length(max = 1000, message = "监管中心领导/处领导 最大长度不能超过1000")
    private String centerOpinion;

    @Getter
    @Setter
    @Length(max = 1000, message = "办领导 最大长度不能超过1000")
    private String leaderOpinion;
}