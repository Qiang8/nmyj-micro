package com.taiji.cdp.cmd.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 页面传过来的参数
 */
public class CommandParamVo extends BaseVo<String>{

    public CommandParamVo() {
    }

    /**
     * 舆情信息id
     */
    @Getter
    @Setter
    private String infoId;

    /**
     * 舆情信息标题
     */
    @Getter
    @Setter
    private String title;

    /**
     * 舆情首发时间
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
    private LocalDateTime startTime;

    /**
     * 首发网址
     */
    @Getter
    @Setter
    private String startWebsite;

    /**
     * 传播情况
     */
    @Getter
    @Setter
    private String transmission;

    /**
     * 研判意见
     */
    @Getter
    @Setter
    private String judgeOpinion;

    /**
     * 监管中心意见
     */
    @Getter
    @Setter
    private String centerOpinion;

    /**
     * 办领导批示
     */
    @Getter
    @Setter
    private String leaderOpinion;

    /**
     * 承办意见
     */
    @Getter
    @Setter
    private String transactOpinion;

    /**
     * 处置情况
     */
    @Getter
    @Setter
    private String disposalSituation;

    /**
     * 处置类型
     */
    @Getter
    @Setter
    private List<String> handleTypeIds;

    /**
     * 处置状态：0处置中 1已完成
     */
    @Getter
    @Setter
    private String handleFlag;

    /**
     * 创建部门id
     */
    @Getter
    @Setter
    private String createOrgId;

}
