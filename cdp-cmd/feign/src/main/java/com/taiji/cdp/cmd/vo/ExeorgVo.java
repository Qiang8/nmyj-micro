package com.taiji.cdp.cmd.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 处置单位和舆情调控单对应VO
 *
 * @author xuweikai-pc
 * @date 2019年2月20日 14:56:25
 */
public class ExeorgVo extends IdVo<String> {


    public ExeorgVo() {
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
    private LocalDateTime sendTime;

    /**
     * 处置部门id
     */
    @Getter
    @Setter
    @Length(max = 36, message = "处置部门id orgId最大长度不能超过36")
    private String orgId;

}
