package com.taiji.auth.util;

import com.taiji.auth.entity.Role;
import com.taiji.auth.entity.User;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>Title:UserDetailsImpl.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/12 19:09</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class UserDetailsImpl implements UserDetails {

    private String       username;

    private String       password;

    @Getter
    private String       id;
    @Getter
    private String       status;
    @Getter
    private String       delFlag;
    @Getter
    private String       orgId;
    @Getter
    private String       orgName;
    @Setter
    private List<Role> roleList;

    public UserDetailsImpl(User userVo)
    {
        this.id = userVo.getId();
        this.username = userVo.getAccount();
        this.password = userVo.getPassword();
        this.status = userVo.getStatus();
        this.delFlag = userVo.getDelFlag();
        this.orgId = userVo.getProfile().getOrgId();
        this.orgName = userVo.getProfile().getOrgName();
        roleList = userVo.getRoleList();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (Role role : roleList) {
            authorityList.add(new SimpleGrantedAuthority(role.getRoleCode()));
        }
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return StringUtils.equals(DelFlagEnum.NORMAL.getCode(), delFlag) ? true : false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return StringUtils.equals(StatusEnum.ENABLE.getCode(), status) ? true : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(StatusEnum.ENABLE.getCode(), status) ? true : false;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
