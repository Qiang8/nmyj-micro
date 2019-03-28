package com.taiji.base.sys.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * <p>Title:DicGroupVo.java</p >
 * <p>Description: 字典项Vo</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:27</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class DicGroupVo extends BaseVo<String> {
    public DicGroupVo(){}

    /**
     * 类别名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "类别名称dicName字段最大长度50")
    private String dicName;

    /**
     * 类别编码
     */
    @Getter
    @Setter
    @Length(max = 50,message = "类别名称dicCode字段最大长度50")
    private String dicCode;

    /**
     * 类型 0:列表，1:树型
     */
    @Getter
    @Setter
    @Length(max = 1,message = "类型type字段最大长度1")
    private String type;

    /**
     * 排序
     */
    @Getter
    @Setter
    private Integer orders;

    /**
     * 禁用标志 0:禁用,1:启用
     */
    @Getter
    @Setter
    @Length(max = 1,message = "禁用标识status字段最大长度1")
    private String status;
}
