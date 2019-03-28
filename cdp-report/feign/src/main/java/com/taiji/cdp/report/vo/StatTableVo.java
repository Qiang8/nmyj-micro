package com.taiji.cdp.report.vo;

import lombok.Getter;
import lombok.Setter;


/**
 * @author sunyi
 * @date 2019年2月27日
 */
public class StatTableVo {

    public StatTableVo(){}
    @Getter@Setter
    private Integer tagFlag;
    @Getter@Setter
    private Integer totalNum;

    public StatTableVo(Integer tagFlag, Integer totalNum){
        this.tagFlag = tagFlag;
        this.totalNum = totalNum;
    }


}
