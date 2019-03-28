package com.taiji.cdp.daily.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 舆情信息VO
 * @author qizhijie-pc
 * @date 2019年1月8日15:10:25
 */
public class ConsInfoVo extends BaseVo<String>{

    public ConsInfoVo(){}

    /**
     * 舆情标题
     */
    @Getter@Setter
    @Length(max = 200,message = "舆情标题 title 最大长度不能超过200")
    @NotEmpty(message = "舆情标题 title 不能为空字符串")
    private String title;

    /**
     * 舆情URL链接
     */
    @Getter@Setter
    @Length(max = 100,message = "舆情URL链接 infoUrl 最大长度不能超过100")
    @NotEmpty(message = "舆情URL链接 infoUrl 不能为空字符串")
    private String infoUrl;

    /**
     * 舆情内容
     */
    @Getter@Setter
    private String content;

    /**
     * 舆情来源类型ID -- 字典项
     */
    @Getter@Setter
    @Length(max = 36,message = "舆情来源类型ID sourceTypeId 最大长度不能超过36")
    private String sourceTypeId;

    /**
     * 舆情来源类型名称
     */
    @Getter@Setter
    @Length(max = 100,message = "舆情来源类型名称 sourceTypeName 最大长度不能超过100")
    private String sourceTypeName;

    /**
     * 来源网站名称
     */
    @Getter@Setter
    @Length(max = 50,message = "来源网站名称 websiteName 最大长度不能超过50")
    private String websiteName;

    /**
     * 上报人姓名
     */
    @Getter@Setter
    @Length(max = 50,message = "上报人姓名 reporter 最大长度不能超过50")
    private String reporter;

    /**
     * 上报人联系方式
     */
    @Getter@Setter
    @Length(max = 50,message = "上报人联系方式 reporterTel 最大长度不能超过50")
    private String reporterTel;

    /**
     * 上报原因
     */
    @Getter@Setter
    @Length(max = 200,message = "上报原因 reportReason 最大长度不能超过200")
    private String reportReason;

    /**
     * 上报时间 -- 上报时后台生成
     */
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @Getter@Setter
    private LocalDateTime reportTime;

    /**
     * 接收单位编码
     */
    @Getter@Setter
    @Length(max = 50,message = "接收单位编码 receiveOrgCode 最大长度不能超过50")
    private String receiveOrgCode;

    /**
     * 最近办理人
     */
    @Getter@Setter
    @Length(max = 20,message = "最近办理人 lastDealUser 最大长度不能超过20")
    private String lastDealUser;

    /**
     * 最近办理时间
     */
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @Getter@Setter
    private LocalDateTime lastDealTime;

    /**
     * 舆情信息状态：0未上报 1已上报 2已研判 3短信上报 4已审批通过 5已退回 6处置中 7已完成
     */
    @Getter@Setter
    @Length(max = 1,message = "舆情信息状态 status 最大长度不能超过1")
    private String status;

    /**
     * 上报部门ID
     */
    @Getter@Setter
    @Length(max = 36,message = "上报部门ID createOrgId 最大长度不能超过36")
    private String createOrgId;

    /**
     * 上报部门名称
     */
    @Getter@Setter
    @Length(max = 100,message = "上报部门名称 createOrgName 最大长度不能超过100")
    private String createOrgName;


}
