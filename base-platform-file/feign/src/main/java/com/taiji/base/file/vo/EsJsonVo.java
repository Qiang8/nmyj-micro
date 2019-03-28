package com.taiji.base.file.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunyi
 * 大数据返回json串对应obj中的实体
 */
public class EsJsonVo implements Serializable {

    public EsJsonVo(){}

    @Getter
    @Setter
    private String took;
    @Getter
    @Setter
    private Boolean timed_out;
    @Getter
    @Setter
    private Object _shards;
    /**
     * obj中hits对象
     */
    @Getter
    @Setter
    private EsJsonHitsVo hits;


}
