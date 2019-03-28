package com.taiji.base.sys.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>Title:UserProfileVo.java</p >
 * <p>Description: 用户详情Vo</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:34</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class UserProfileVo extends IdVo<String> {

    public UserProfileVo(){}

    /**
     * 姓名
     */
    @Getter
    @Setter
    @NotBlank
    @Length(max = 50,message = "姓名name字段最大长度50")
    private String name;

    /**
     * 头像 存储头像路径
     */
    @Getter
    @Setter
    @Length(max = 100,message = "头像路径avatar字段最大长度100")
    private String avatar;

    /**
     * 邮箱
     */
    @Getter
    @Setter
    @Length(max = 50,message = "邮箱email字段最大长度50")
    private String email;

    /**
     * 手机
     */
    @Getter
    @Setter
    @Length(max = 50,message = "手机mobile字段最大长度50")
    private String mobile;

    /**
     * 性别
     */
    @Getter
    @Setter
    @Length(max = 1,message = "性别sex字段最大长度1")
    private String sex;

    /**
     * 单位职务
     */
    @Getter
    @Setter
    @Length(max = 36,message = "单位职务position字段最大长度36")
    private String position;

    /**
     * 机构
     */
    @Getter
    @Setter
    @Length(max = 36,message = "机构主键orgId字段最大长度36")
    private String orgId;

    /**
     * 冗余机构名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "机构名称orgName字段最大长度100")
    private String orgName;

    /**
     * 冗余机构编码
     */
    @Getter
    @Setter
    @Length(max = 30,message = "机构编码orgCode字段最大长度30")
    private String orgCode;
}
