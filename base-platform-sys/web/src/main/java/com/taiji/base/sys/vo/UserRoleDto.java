package com.taiji.base.sys.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * <p>Title:UserRoleDto.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 16:35</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
public class UserRoleDto {

    @Getter@Setter
    private List<String> belong = new ArrayList<>();

    @Getter@Setter
    private List<RoleMapDto> all = new ArrayList<>();
}
