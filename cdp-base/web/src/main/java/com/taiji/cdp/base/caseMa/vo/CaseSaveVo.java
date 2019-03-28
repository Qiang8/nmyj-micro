package com.taiji.cdp.base.caseMa.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CaseSaveVo {

    public CaseSaveVo(){}

    /**
     * 舆情信息对象
     */
    @Getter @Setter
    @JsonProperty("case")
    @NotNull(message = "case 不能为空")
    private CaseVo caseVo;

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
