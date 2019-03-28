package com.taiji.cdp.daily.daily.vo;

import com.taiji.cdp.daily.vo.DailyVo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class DailySaveVo {

    public DailySaveVo(){}

    /**
     * 日报信息对象
     */
    @Getter @Setter
    @NotNull(message = "daily 不能为空")
    private DailyVo daily;

    /**
     * 待添加附件信息
     */
    @Getter @Setter
    private List<String> fileIds;

    /**
     * 待删除附件信息
     */
    @Getter @Setter
    private List<String> fileDeleteIds;

}
