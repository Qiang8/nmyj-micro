package com.taiji.auth.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import com.taiji.micro.common.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统用户实体类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Entity
@Table(name = "SYS_USER")
public class User extends BaseEntity<String> implements DelFlag {

    public User() {
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
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;

    /**
     * 禁用标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "禁用标识status字段最大长度1")
    private String status;


    /**
     * 是否超级用户
     */
    @Getter
    @Setter
    private Boolean isSuper;

    /**
     * 用户详情
     */
    @Getter
    @Setter
    @OneToOne(targetEntity = UserProfile.class)
    @JoinColumn(name = "PROFILE_ID", referencedColumnName = "ID")
    private UserProfile profile;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "SYS_USER_ROLES", joinColumns = {@JoinColumn(name = "USER_ID", updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", updatable = false)})
    @OrderBy(value = "orders asc")
    private List<Role> roleList;

    /**
     * 用户角色名称，多个以分号隔开，前台显示用。
     */
    @Setter
    @Transient
    private String roleName;

    public String getRoleName() {
        String tempRoleName = "";

        List<Role> roleList = this.getRoleList();
        if(!CollectionUtils.isEmpty(roleList))
        {
            List<String> roleNameList = roleList.stream().filter(role -> StatusEnum.ENABLE.getCode().equals(role.getStatus())).map(Role::getRoleName).collect(Collectors.toList());
            tempRoleName = StringUtils.join(roleNameList, ",");
        }

        return tempRoleName;
    }
}
