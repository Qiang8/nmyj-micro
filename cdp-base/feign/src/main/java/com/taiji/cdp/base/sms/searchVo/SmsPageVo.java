package com.taiji.cdp.base.sms.searchVo;

import com.taiji.cdp.base.sms.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class SmsPageVo extends BasePageVo {
    @Getter@Setter
    private String content;

    @Getter@Setter
    private String createBy;

    @Getter@Setter
    private String buildOrgId;

    @Getter@Setter
    private String sendStatus;

    @Getter@Setter
    private LocalDateTime sendStartTime;

    @Getter@Setter
    private LocalDateTime sendEndTime;
}
