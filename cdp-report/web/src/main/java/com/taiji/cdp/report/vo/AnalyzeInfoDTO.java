package com.taiji.cdp.report.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * url解析信息包装类
 */
public class AnalyzeInfoDTO {

    public AnalyzeInfoDTO(){}

    public AnalyzeInfoDTO(String title,String content){
        this.title = title;
        this.content = content;
    }

    @Getter@Setter
    private String title;

    @Getter@Setter
    private String content;

}
