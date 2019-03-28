package com.taiji.base.file.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sunyi
 * 大数据返回json串对应obj->hits->list<hits>的实体
 */
public class EsJsonHitsListVo implements Serializable {

    public EsJsonHitsListVo(){}

    /**
     * 索引
     */
    @Getter
    @Setter
    private String _index;
    /**
     * 类型
     */
    @Getter
    @Setter
    private String _type;

    /**
     * 业务ID
     */
    @Getter
    @Setter
    private String _id;
    @Getter
    @Setter
    private double _score;
    @Getter
    @Setter
    private Object _source;
}
