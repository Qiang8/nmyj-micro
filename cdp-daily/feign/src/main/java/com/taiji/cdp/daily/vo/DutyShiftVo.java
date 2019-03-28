package com.taiji.cdp.daily.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @program: nmyj-micro
 * @description: 交接班信息Vo
 * @author: TaiJi.XuWeiKai
 * @create: 2019-01-21 17:49
 **/
public class DutyShiftVo extends IdVo<String> {

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
