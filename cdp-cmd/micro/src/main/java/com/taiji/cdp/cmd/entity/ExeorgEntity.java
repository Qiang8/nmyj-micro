package com.taiji.cdp.cmd.entity;

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
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 处置单位和舆情调控单对应entity
 *
 * @author xuweikai-pc
 * @date 2019年2月26日 17:37:57
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CD_EXEORG")
public class ExeorgEntity extends IdEntity<String> {

    public ExeorgEntity() {
    }

    /**
     * 舆情调控单ID
     */
    @Getter
    @Setter
    @Length(max = 36, message = "舆情调控单ID cdId最大长度不能超过36")
    private String cdId;

    /**
     * 舆情调控单下发时间
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
    private String sendTime;

    /**
     * 处置部门id
     */
    @Getter
    @Setter
    @Length(max = 36, message = "处置部门id orgId最大长度不能超过36")
    private String orgId;

}
