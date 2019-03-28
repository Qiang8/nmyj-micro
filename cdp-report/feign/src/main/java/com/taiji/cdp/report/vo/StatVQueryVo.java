package com.taiji.cdp.report.vo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author sunyi
 * @date 2019年1月23日
 */
public class StatVQueryVo {

    public StatVQueryVo(){}
    @Getter@Setter
    private LocalDateTime startLocalDate;
    @Getter@Setter
    private LocalDateTime endLocalDate;
    @Getter@Setter
    private List<String> status;
    @Getter@Setter
    private String orgId;
    @Getter@Setter
    private String statFlag;
    @Getter@Setter
    private String timeCycle;
    @Getter@Setter
    private String isOwnOrg;


}
