package com.taiji.base.sys.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

public class UserVo extends BaseVo<String> {
    public UserVo() {
    }

    /**
     * 账号
     */
    @Length(max = 50,message = "账号account字段最大长度50")
    @NotBlank(message = "账号不能为空字符串")
    @Getter
    @Setter
    private String account;

    /**
     * 密码
     */
    @Length(max = 80,message = "密码password字段最大长度80")
    @NotBlank(message = "密码不能为空字符串")
    @Getter
    @Setter
    private String password;

    /**
     * 登录失败次数
     */
    @Getter
    @Setter
    private Integer faultNum;

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

    /**
     * 用户默认初始页面
     */
    @Getter
    @Setter
    private String homePage;

    /**
     * 用户详情
     */
    @Getter
    @Setter
    private UserProfileVo profile;

    @Getter
    @Setter
    private List<RoleVo> roleList;

    /**
     * 冗余用户角色名称，多个以分号隔开
     */
    @Getter
    @Setter
    private String roleName;
}
