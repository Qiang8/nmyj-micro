package com.taiji.base.contact.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * <p>
 * <p>Title:ContactMemberVo.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2019/1/15 0:04</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
public class ContactMemberVo extends BaseVo<String> {
    public ContactMemberVo()
    {}

    /**
     * 姓名
     */
    @Length(max = 50,message = "姓名name字段最大长度50")
    @Getter
    @Setter
    private String name;

    /**
     * 头像地址
     */
    @Length(max = 500,message = "头像avatar字段最大长度500")
    @Getter
    @Setter
    private String avatar;

    /**
     * 机构编码
     */
    @Length(max = 36,message = "机构主键orgId字段最大长度36")
    @Getter
    @Setter
    private String orgId;

    /**
     * 机构名称
     */
    @Length(max = 100,message = "机构名称orgName字段最大长度100")
    @Getter
    @Setter
    private String orgName;

    /**
     * 冗余机构编码
     */
    @Getter
    @Setter
    @Length(max = 30,message = "机构编码orgCode字段最大长度30")
    private String orgCode;

    /**
     * 职务编码
     */
    @Length(max = 36,message = "职务编码dutyTypeId字段最大长度36")
    @Getter
    @Setter
    private String dutyTypeId;

    /**
     * 职务名称
     */
    @Length(max = 100,message = "职务名称dutyTypeName字段最大长度100")
    @Getter
    @Setter
    private String dutyTypeName;

    /**
     * 职称编码
     */
    @Length(max = 36,message = "职务编码orgName字段最大长度36")
    @Getter
    @Setter
    private String titleId;

    /**
     * 职称名称
     */
    @Length(max = 100,message = "职称名称orgName字段最大长度100")
    @Getter
    @Setter
    private String titleName;

    /**
     * 性别
     */
    @Length(max = 1,message = "性别sex字段最大长度1")
    @Getter
    @Setter
    private String sex;

    /**
     * 出生年月
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Getter
    @Setter
    private LocalDate birthday;

    /**
     * 邮箱
     */
    @Length(max = 50,message = "邮箱email字段最大长度50")
    @Getter
    @Setter
    private String email;

    /**
     * 联系地址
     */
    @Length(max = 500,message = "联系地址address字段最大长度500")
    @Getter
    @Setter
    private String address;

    /**
     * 移动电话
     */
    @Length(max = 16,message = "移动电话mobile字段最大长度16")
    @Getter
    @Setter
    private String mobile;

    /**
     * 办公电话
     */
    @Length(max = 16,message = "移动电话telephone字段最大长度16")
    @Getter
    @Setter
    private String telephone;

    /**
     * 其他联系方式
     */
    @Length(max = 16,message = "其他联系方式otherWay字段最大长度16")
    @Getter
    @Setter
    private String otherWay;

    /**
     * 顺序
     */
    @Getter
    @Setter
    private Integer orders;

    /**
     * 传真
     */
    @Length(max = 16,message = "传真fax字段最大长度16")
    @Getter
    @Setter
    private String fax;

    /**
     * 备注
     */
    @Length(max = 4000,message = "备注notes字段最大长度4000")
    @Getter
    @Setter
    private String notes;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1, message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}

