package com.taiji.cdp.daily.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @program: nmyj-micro
 * @description: 交接班事项信息Vo
 * @author: TaiJi.XuWeiKai
 * @create: 2019-01-21 17:49
 **/
public class DutyShiftItemVo extends IdVo<String> {

    @Getter
    @Setter
    @NotBlank
    @Length(max = 36, message = "交接班表id")
    private String dutyId;

    @Getter
    @Setter
    @NotBlank
    @Length(max = 36, message = "舆情信息id")
    private String infoId;


}
