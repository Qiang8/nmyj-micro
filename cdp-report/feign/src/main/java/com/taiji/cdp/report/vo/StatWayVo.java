package com.taiji.cdp.report.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author sunyi
 * @date 2019年1月23日
 */
public class StatWayVo {

    public StatWayVo(){}
    @Getter@Setter
    private List<String> reportWay;
    @Getter@Setter
    private List<Integer> infoTotalNum;

    public StatWayVo(List<String> reportWay, List<Integer> infoTotalNum){
        this.reportWay = reportWay;
        this.infoTotalNum = infoTotalNum;
    }


}
