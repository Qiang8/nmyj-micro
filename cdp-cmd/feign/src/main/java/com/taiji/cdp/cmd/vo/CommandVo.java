package com.taiji.cdp.cmd.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 舆情调控单VO
 *
 * @author xuweikai-pc
 * @date 2019年2月20日 14:56:25
 */
public class CommandVo extends BaseVo<String> {


    public CommandVo() {
    }

    /**
     * 舆情信息id
     */
    @Getter
    @Setter
    @Length(max = 36, message = "舆情信息ID infoId最大长度不能超过36")
    private String infoId;

    /**
     * 舆情信息标题
     */
    @Getter
    @Setter
    @Length(max = 200, message = "舆情信息标题 title最大长度不能超过200")
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
    @Length(max = 500, message = "首发网址 startWebsite最大长度不能超过500")
    private String startWebsite;

    /**
     * 传播情况
     */
    @Getter
    @Setter
    @Length(max = 500, message = "传播情况 transmission最大长度不能超过500")
    private String transmission;

    /**
     * 研判意见
     */
    @Getter
    @Setter
    @Length(max = 500, message = "研判意见 judgeOpinion最大长度不能超过500")
    private String judgeOpinion;

    /**
     * 监管中心意见
     */
    @Getter
    @Setter
    @Length(max = 500, message = "监管中心 centerOpinion最大长度不能超过500")
    private String centerOpinion;

    /**
     * 办领导批示
     */
    @Getter
    @Setter
    @Length(max = 500, message = "办领导批示 leaderOpinion最大长度不能超过500")
    private String leaderOpinion;

    /**
     * 承办意见
     */
    @Getter
    @Setter
    @Length(max = 500, message = "承办意见 transactOpinion最大长度不能超过500")
    private String transactOpinion;

    /**
     * 处置情况
     */
    @Getter
    @Setter
    @Length(max = 500, message = "处置情况 transactOpinion最大长度不能超过500")
    private String disposalSituation;

    /**
     * 处置类型
     */
    @Getter
    @Setter
    @Length(max = 500, message = "处置类型 handleTypeIds最大长度不能超过200")
    private String handleTypeIds;

    /**
     * 处置状态：0处置中 1已完成
     */
    @Getter
    @Setter
    @Length(max = 1, message = "处置状态 handleFlag最大长度不能超过1")
    private String handleFlag;

    /**
     * 创建部门id
     */
    @Getter
    @Setter
    @Length(max = 500, message = "创建部门id createOrgId最大长度不能超过36")
    private String createOrgId;

    @Getter
    @Setter
    @Length(max = 1, message = "删除标记（0已删除，1未删除）")
    private String delFlag;
}
