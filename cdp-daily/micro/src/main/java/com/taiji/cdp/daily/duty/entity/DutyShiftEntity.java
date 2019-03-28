package com.taiji.cdp.daily.duty.entity;

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
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * <p>Title:DutyShiftEntity.java</p >
 * <p>Description: 交接班实体类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/21 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "WB_DUTYSHIFT")
public class DutyShiftEntity extends IdEntity<String> {

    @Getter
    @Setter
    @NotBlank
    @Length(max = 100, message = "舆情标题")
    private String title;

    @Getter
    @Setter
    @NotBlank
    @Length(max = 36, message = "交班小组ID")
    private String fromTeamId;

    @Getter
    @Setter
    @Length(max = 100, message = "交班小组名称")
    private String fromTeamName;

    @Getter
    @Setter
    @NotBlank
    @Length(max = 36, message = "接班小组ID")
    private String toTeamId;

    @Getter
    @Setter
    @Length(max = 100, message = "接班小组名称")
    private String toTeamName;

    @Getter
    @Setter
    @Length(max = 500, message = "备注")
    private String notes;

    @Getter
    @Setter
    @Length(max = 36, message = "创建单位ID")
    private String createOrgId;

    /**
     * 创建人
     */
    @Getter
    @Setter
    @Length(max = 20, message = "创建人 createBy 最大长度不能超过20")
    private String createBy;

    /**
     * 创建时间
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
    @Getter
    @Setter
    private LocalDateTime createTime;


}