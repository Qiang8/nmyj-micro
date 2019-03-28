package com.taiji.cdp.report.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @program: activitisi
 * @description: 日志
 * @author: TaiJi.WangJian
 * @create: 2019-01-15 18:05
 **/
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "BM_FLOW_LOG")
public class FlowLog extends IdEntity<String> {
    @Getter
    @Setter
    @Length(max = 36,message = "申请记录ID 字段最大长度36")
    private String applyId;

    @Getter
    @Setter
    @Length(max = 36,message = "业务实体ID 字段最大长度36")
    @NotEmpty(message = "业务实体id 不能为空字符串")
    private String entityId;

    @Getter
    @Setter
    @Length(max = 20,message = "流程节点 字段最大长度20")
    private String flowNode;

    @Getter
    @Setter
    @Length(max = 36,message = "处理人员ID 字段最大长度36")
    private String dealUserId;

    @Getter
    @Setter
    @Length(max = 50,message = "处理人员名称 字段最大长度50")
    private String dealUserName;

    @Getter
    @Setter
    @Length(max = 36,message = "处理人员部门ID 字段最大长度36")
    private String dealOrgId;

    @Getter
    @Setter
    @Length(max = 100,message = "处理人员部门名称 字段最大长度100")
    private String dealOrgName;
    /**
     * 处理时间
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
    private LocalDateTime dealTime;

    @Getter
    @Setter
    @Length(max = 500,message = "处理内容 字段最大长度500")
    private String dealContent;

    @Getter
    @Setter
    @Length(max = 36,message = "下一节点接收人ID 字段最大长度36")
    private String receiverId;
}