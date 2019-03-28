package com.taiji.cdp.daily.monthly.vo;

import com.taiji.cdp.daily.vo.MonthlyVo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MonthlyInfoVo {
    @Getter
    @Setter
    @NotNull(message = "月报对象不能为null")
    private MonthlyVo monthly;

    /**
     * 待加入附件对象
     */
    @Getter
    @Setter
    private List<String> fileIds;

    /**
     * 待删除的附件对象
     */
    @Getter
    @Setter
    private List<String> fileDeleteIds;

}
