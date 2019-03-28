package com.taiji.cdp.report.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 舆情信息按新增舆情和重点舆情分类进行统计（近七日）
 * @author sunyi
 * @date 2019年1月23日
 */
public class StatTimeTypeVo {

    public StatTimeTypeVo(){}
    @Getter@Setter
    private List<String> statTime;
    @Getter@Setter
    private List<Integer> newTotalNum;
    @Getter@Setter
    private List<Integer> foucsTotalNum;

    public StatTimeTypeVo(List<String> statTime, List<Integer> newTotalNum,List<Integer> foucsTotalNum){
        this.statTime = statTime;
        this.newTotalNum = newTotalNum;
        this.foucsTotalNum = foucsTotalNum;

    }


}
