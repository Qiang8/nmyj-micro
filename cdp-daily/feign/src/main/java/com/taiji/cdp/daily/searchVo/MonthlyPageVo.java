package com.taiji.cdp.daily.searchVo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: sunyi
 **/
public class MonthlyPageVo {

    public MonthlyPageVo(){}


    /**
     * 页码
     */
    @Getter
    @Setter
    private Integer page;
    /**
     * 长度
     */
    @Getter
    @Setter
    private Integer size;
    /**
     * 被检索的专刊名称
     */
    @Getter
    @Setter
    private String title;

    /**
     * IDS
     */
    @Getter
    @Setter
    private List<String> ids;

    /**
     * IDS
     */
    @Getter
    @Setter
    private String status;


    /**
     * IDS
     */
    @Getter
    @Setter
    private String createBy;


}