package com.taiji.auth.entity;

import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统用户详情实体类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Entity
@Table(name = "SYS_USER_PROFILE")
public class UserProfile extends IdEntity<String> {

    public UserProfile(){}

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
    @Length(max = 36,message = "机构编码orgID字段最大长度36")
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
