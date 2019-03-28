package com.taiji.cdp.cmd.vo;/**
 * @program: nmyj-micro
 * @Description:
 * @Author: WangJian(wangjiand @ mail.taiji.com.cn)
 * @Date: 2019/3/5 11:37
 **/

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.BaseVo;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @program: nmyj-micro
 * @Description: 调控单办理情况
 * @Author: TAIJI.WangJian
 * @Date: 2019/3/5 11:37
 **/
public class TreatVo extends BaseVo<String> {
    /**
     * 调控单ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "调控单ID 最大长度不能超过36")
    @NotEmpty(message = "调控单ID 不能为空字符串")
    private String cdId;

    /**
     * 办理内容
     */
    @Getter
    @Setter
    @Length(max = 500,message = "办理内容 最大长度不能超过500")
    private String treatment;

    /**
     * 办理人
     */
    @Getter
    @Setter
    @Length(max = 20,message = "办理人 最大长度不能超过20")
    private String treatBy;

    /**
     * 办理时间
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
    private LocalDateTime treatTime;

    /**
     * 备注
     */
    @Getter
    @Setter
    @Length(max = 1000,message = "备注 最大长度不能超过500")
    private String notes;
}
