package com.taiji.cdp.report.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ConsInfoSaveVo {

    public ConsInfoSaveVo(){}

    /**
     * 舆情信息对象
     */
    @Getter @Setter
    @NotNull(message = "consInfo 不能为空")
    private ConsInfoVo consInfo;

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
