package com.taiji.cdp.base.sms.searchVo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class SmsListVo {
    @Getter
    @Setter
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
