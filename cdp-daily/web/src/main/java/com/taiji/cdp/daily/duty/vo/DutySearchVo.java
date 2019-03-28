package com.taiji.cdp.daily.duty.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询值班小组参数VO
 */
public class DutySearchVo {
    /**
     * 区分 主班还是副班
     */
    @Getter
    @Setter
    private String isBak;

    /**
     * 岗位代号:1、带班办领导 2、带班处长 3、舆情岗 4、网信通 5、机要岗 6、技术岗
     */
    @Getter
    @Setter
    private String duty;

    /**
     * 开始时间
     */
    @Getter
    @Setter
    private String startDate;
    /**
     * 结束时间
     */
    @Getter
    @Setter
    private String endDate;
}
