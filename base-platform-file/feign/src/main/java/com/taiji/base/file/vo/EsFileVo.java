package com.taiji.base.file.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunyi
 */
public class EsFileVo {

    public EsFileVo(){}


    /**
     * 业务实体ID
     */
    @Getter
    @Setter
    private String id;
    /**
     * 索引名称，英文小写
     */
    @Getter
    @Setter
    private String indexName = "neimengyqrb";

    /**
     * 类型，一般与索引同名
     */
    @Getter
    @Setter
    private String typeName = "neimengyqrb";

    /**
     * 创建人
     */
    @Getter
    @Setter
    private String account;

    /**
     * 时间
     */
    @Getter
    @Setter
    private String createTime;
    /**
     * 标题
     */
    @Getter
    @Setter
    private String title;

    /**
     * 内容
     */
    @Getter
    @Setter
    private String content;
    /**
     * 分页
     */
    @Getter
    @Setter
    private int page;
    /**
     * 分页
     */
    @Getter
    @Setter
    private int size;

}
