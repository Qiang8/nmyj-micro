package com.taiji.base.file.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author sunyi
 * 大数据返回json串对应obj->hits的实体
 */
public class EsJsonHitsVo implements Serializable {

    public EsJsonHitsVo(){}

    @Getter
    @Setter
    private String total;
    @Getter
    @Setter
    private double max_score;

    /**
     * obj->hits->list<hits>的实体
     */
    @Getter
    @Setter
    private List<EsJsonHitsListVo> hits;



}
