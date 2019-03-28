package com.taiji.base.sys.vo;

import com.taiji.micro.common.utils.ITreeNode;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * <p>Title:DicGroupItemVo.java</p >
 * <p>Description: 数据字典Vo</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:29</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class DicGroupItemVo extends BaseVo<String> implements ITreeNode<DicGroupItemVo>{
    public DicGroupItemVo(){}

    /**
     * 字典名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "字典名称itemName字段最大长度100")
    private String itemName;

    /**
     * 字典编码
     */
    @Getter
    @Setter
    @Length(max = 100,message = "字典编码itemCode字段最大长度100")
    private String itemCode;

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
     * 删除标志 0:正常,1:删除
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 父节点
     */
    @Getter
    @Setter
    @Length(max = 36,message = "父节点编码parentId字段最大长度36")
    private String parentId;

    @Getter
    @Setter
    private List<DicGroupItemVo> children;
}
