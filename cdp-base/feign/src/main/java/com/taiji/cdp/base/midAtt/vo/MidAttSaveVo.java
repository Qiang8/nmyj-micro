package com.taiji.cdp.base.midAtt.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 用于接收编辑保存之后的附件中间表
 */
public class MidAttSaveVo {

    public MidAttSaveVo(){}

    public MidAttSaveVo(String entityId,List<String> fileIds,List<String> deleteIds){
        this.entityId = entityId;
        this.fileIds = fileIds;
        this.deleteIds = deleteIds;
    }

    /**
     * 业务id
     */
    @Getter @Setter
    @NotEmpty(message = "业务Id entityId 不能为空")
    private String entityId;

    /**
     * 待加入附件对象
     */
    @Getter @Setter
    private List<String> fileIds;

    /**
     * 待删除的附件对象
     */
    @Getter @Setter
    private List<String> deleteIds;

}
