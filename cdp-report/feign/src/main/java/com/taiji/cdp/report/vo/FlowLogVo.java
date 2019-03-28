package com.taiji.cdp.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-31 17:03
 **/
public class FlowLogVo extends IdVo<String> {

    @Getter
    @Setter
    @Length(max = 36, message = "业务实体id infoId最大长度不能超过36")
    @NotEmpty(message = "业务实体id 不能为空字符串")
    private String entityId;

    @Getter
    @Setter
    @Length(max = 20, message = "流程节点 flowNode最大长度不能超过20")
    @NotEmpty(message = "流程节点")
    private String flowNode;


    @Getter
    @Setter
    @Length(max = 36,message = "处理人员部门ID 字段最大长度36")
    private String dealOrgId;

    @Getter
    @Setter
    @Length(max = 100, message = "处理部门名称 dealOrgName最大长度不能超过100")
    @NotEmpty(message = "处理部门名称 不能为空字符串")
    private String dealOrgName;

    @Getter
    @Setter
    @Length(max = 36,message = "处理人员ID 字段最大长度36")
    private String dealUserId;

    @Getter
    @Setter
    @Length(max = 50, message = "处理人姓名 最大长度不能超过50")
    private String dealUserName;

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
    private LocalDateTime dealTime;

    @Getter
    @Setter
    @Length(max = 500, message = "处理内容 dealContent最大长度不能超过500")
    private String dealContent;
}