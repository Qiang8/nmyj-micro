package com.taiji.base.sys.vo;

;
import com.taiji.micro.common.utils.ITreeNode;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>Title:MenuVo.java</p >
 * <p>Description: 菜单Vo</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:32</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class MenuVo extends BaseVo<String> implements ITreeNode<MenuVo> {
    public MenuVo(){}

    /**
     * 父节点ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "父节点编码parentId字段最大长度36")
    private String parentId;

    /**
     * 菜单名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "菜单名称menuName字段最大长度50")
    private String menuName;

    /**
     * 权限标识
     */
    @Getter
    @Setter
    @Length(max = 50,message = "权限标识permission字段最大长度50")
    private String permission;

    /**
     * 路径
     */
    @Getter
    @Setter
    @Length(max = 100,message = "路径path字段最大长度100")
    private String path;

    /**
     * 路由名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "路由名称routerName字段最大长度100")
    private String routerName;

    /**
     * 重定向
     */
    @Getter
    @Setter
    @Length(max = 100,message = "重定向路径redirect字段最大长度100")
    private String redirect;

    /**
     * 图标
     */
    @Getter
    @Setter
    @Length(max = 50,message = "图标icon字段最大长度50")
    private String icon;

    /**
     * 组件
     */
    @Getter
    @Setter
    @Length(max = 100,message = "组件component字段最大长度100")
    private String component;

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
     * 菜单类型 0:菜单,1:按钮
     */
    @Getter
    @Setter
    @Length(max = 1,message = "类型标识type字段最大长度1")
    private String type;

    /**
     * 是否隐藏，默认false
     */
    @Getter
    @Setter
    @NotNull
    private Boolean hidden;

    /**
     * 是否显示，默认false
     */
    @Getter
    @Setter
    @NotNull
    private Boolean alwaysShow;

    @Getter
    @Setter
    private List<MenuVo> children;
}
