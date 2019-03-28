package com.taiji.base.sys.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>Title:RoleTypeMenuVo.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/30 11:56</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class RoleTypeMenuVo extends IdVo<String> {


    @Length(max = 36, message = "菜单Id字段最大长度36")
    @NotBlank(message = "菜单Id不能为空字符串")
    @Getter
    @Setter
    private String menuId;

    @Length(max = 36, message = "角色类型menuId字段最大长度36")
    @NotBlank(message = "角色类型menuId不能为空字符串")
    @Getter
    @Setter
    private String roleType;
}
