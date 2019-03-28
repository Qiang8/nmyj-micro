package com.taiji.cdp.daily.duty.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 值班查询今日领导信息返回实体
 */
public class LeaderUserVo {

    /**
     * 值班小组Id
     */
    @Getter
    @Setter
    private String teamId;

    /**
     * 值班小组名称
     */
    @Getter
    @Setter
    private String teamName;

    /**
     * 值班岗位类别
     */
    @Getter
    @Setter
    private String dutyCode;

    /**
     * 值班人id
     */
    @Getter
    @Setter
    private String userId;

    /**
     * 值班人姓名
     */
    @Getter
    @Setter
    private String userName;

    /**
     * 登陆名
     */
    @Getter
    @Setter
    private String account;

    /**
     * 值班人性别
     */
    @Getter
    @Setter
    private String sex;

    /**
     * 值班人电话
     */
    @Getter
    @Setter
    private String mobile;

    /**
     * 班次类型，1、白班 2、夜班
     */
    @Getter
    @Setter
    private String dutyType;
}
