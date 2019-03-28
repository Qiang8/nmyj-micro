package com.taiji.cdp.cmd.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FeedbackSaveVo {

    public FeedbackSaveVo(){}

    /**
     * 处置反馈对象
     */
    @Getter @Setter
    @NotNull(message = "feedback 不能为空")
    private FeedbackVo feedback;

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
