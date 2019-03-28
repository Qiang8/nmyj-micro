package com.taiji.base.msg.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>Title:Receiver.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/14 11:43</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class Receiver{
    @Setter
    @Getter
    private String id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String readFlag;

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
    private LocalDateTime readTime;

    @Setter
    @Getter
    private String deptId;

    @Setter
    @Getter
    private String deptName;

}
