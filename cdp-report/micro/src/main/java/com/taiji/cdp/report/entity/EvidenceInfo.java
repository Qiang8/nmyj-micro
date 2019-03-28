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
 * 信息取证实体对象
 *
 * @author xuweikai-pc
 * @date 2019年1月12日17:37:57
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "CJ_CONS_EVIDENCE")
public class EvidenceInfo extends IdEntity<String> {

    public EvidenceInfo() {
    }

    /**
     * 舆情信息id
     */
    @Getter
    @Setter
    @Length(max = 36, message = "舆情信息ID infoId最大长度不能超过36")
    @NotEmpty(message = "舆情信息id 不能为空字符串")
    private String infoId;

    /**
     * 舆情URL链接
     */
    @Getter
    @Setter
    @Length(max = 500, message = "舆情URL链接 infoUrl 最大长度不能超过500")
    @NotEmpty(message = "舆情URL链接 infoUrl 不能为空字符串")
    private String infoUrl;

    /**
     * 截图本地存储地址
     */
    @Getter
    @Setter
    @Length(max = 200, message = "截图存储地址 photoLocation最大长度不能超过200")
    private String photoLocation;

    /**
     * 截图第三方存储地址
     */
    @Getter
    @Setter
    @Length(max = 200, message = "截图存储地址 photoLocation最大长度不能超过200")
    private String photoServerLocation;


    /**
     * 取证人id
     */
    @Getter
    @Setter
    @Length(max = 200, message = "截图存储地址 photoLocation最大长度不能超过200")
    private String edUserId;

    /**
     * 取证人姓名
     */
    @Getter
    @Setter
    @Length(max = 200, message = "截图存储地址 photoLocation最大长度不能超过200")
    private String edUserName;

    /**
     * 取证时间
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
    private LocalDateTime edTime;
}
