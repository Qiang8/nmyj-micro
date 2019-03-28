package com.taiji.base.sys.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * <p>Title:RoleVo.java</p >
 * <p>Description: 角色Vo</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:33</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class RoleVo extends BaseVo<String> {
    public RoleVo(){}

    /**
     * 角色名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "角色名称roleName字段最大长度50")
    private String  roleName;

    /**
     * 角色编码
     */
    @Getter
    @Setter
    @Length(max = 50,message = "角色编码roleCode字段最大长度50")
    private String roleCode;

    /**
     * 角色类型
     */
    @Getter
    @Setter
    @Length(max = 36,message = "角色类型roleType字段最大长度36")
    private String roleType;

    /**
     * 角色默认页面
     */
    @Getter
    @Setter
    @Length(max = 100,message = "角色默认页面homePage字段最大长度100")
    private String homePage;

    /**
     * 角色描述
     */
    @Getter
    @Setter
    @Length(max = 500,message = "角色描述description字段最大长度500")
    private String description;

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
     * 禁用标志 0:禁用,1:启用
     */
    @Getter
    @Setter
    @Length(max = 1,message = "禁用标识status字段最大长度1")
    private String status;

    @Getter
    @Setter
    private Boolean isSuper;

    @Getter
    @Setter
    private List<MenuVo> menuList;
}
