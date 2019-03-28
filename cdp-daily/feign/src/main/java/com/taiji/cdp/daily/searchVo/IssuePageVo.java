package com.taiji.cdp.daily.searchVo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: sunyi
 **/
public class IssuePageVo {

    public IssuePageVo(){}

    @Getter
    @Setter
    private Integer page;
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


}