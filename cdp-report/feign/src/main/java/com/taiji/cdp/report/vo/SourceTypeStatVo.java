package com.taiji.cdp.report.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author sunyi
 * @date 2019年1月23日
 */
public class SourceTypeStatVo{

    public SourceTypeStatVo(){}

    /**
     * 来源类型
     */
    @Getter@Setter
    private List<String> sourceType;
    /**
     * 舆情总数量
     */
    @Getter@Setter
    private List<String> totalNum;

    public SourceTypeStatVo( List<String> sourceType,List<String> totalNum){
        this.sourceType = sourceType;
        this.totalNum = totalNum;
    }


}
