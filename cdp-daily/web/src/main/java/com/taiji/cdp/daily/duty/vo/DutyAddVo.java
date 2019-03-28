package com.taiji.cdp.daily.duty.vo;

import com.taiji.cdp.daily.vo.DutyShiftVo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DutyAddVo {

    /**
     * 交接班vo实体
     */
    @Getter
    @Setter
    @NotNull(message = "月报对象不能为null")
    private DutyShiftVo dutyShift;

    /**
     * 交接班舆情信息id集合
     */
    @Getter
    @Setter
    private List<String> infoIds;


}
