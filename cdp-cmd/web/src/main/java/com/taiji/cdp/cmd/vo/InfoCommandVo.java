package com.taiji.cdp.cmd.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

//舆情信息调控单包装类
public class InfoCommandVo {

    public InfoCommandVo(){}

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startTime;

    @Getter
    @Setter
    private String startWebsite;

    @Getter
    @Setter
    private String transmission;

    @Getter
    @Setter
    private String judgeOpinion;

    @Getter
    @Setter
    private String centerOpinion;

    @Getter
    @Setter
    private String leaderOpinion;

    @Getter
    @Setter
    private String transactOpinion;

    @Getter
    @Setter
    private String disposalSituation;

}
