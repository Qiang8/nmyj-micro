package com.taiji.cdp.report.common.global;


/**
 * 微服务全局变量类
 */
public class StatGlobal {

    /**
     * 状态查询
     */
    public static final String[] STAT_CONS_INFO_REPORT = {"1","2","3","4","5","6","7","8"};//已上报
    public static final String[] STAT_CONS_INFO_ACTION = {"4","5","6","7","8"};//已处理
    public static final String[] STAT_CONS_INFO_ONE = {"1","2","3","4","5","6","7"};//状态1-7
    public static final String[] STAT_CONS_INFO_TWO = {"1","2","3","4","6","7"};//状态1-,不包含5
    public static final String[] STAT_CONS_INFO_TWO_NOT = {"2","3"};//未处理的
    public static final String[] STAT_CONS_INFO_REPORT_NOT = {"0"};//待上报
    /**
     * 统计数量使用
     */
    public static final String[] STAT_CONS_INFO_TO_BE_PROCESSED = {"1","2","3"};//待处理的
    public static final String[] STAT_CONS_INFO_DISPOSED_OF = {"4","6","7"};//已处理的
    public static final String[] STAT_CONS_INFO_BOUNCE = {"5"};//已退回
    public static final String STAT_TAG_FLAG_NOT = "0";//待上

    /**
     * 前端传参状态位
     */
    public static final String STAT_TYPE_LING = "0";
    public static final String STAT_TYPE_YI = "1";
    public static final String STAT_TYPE_ER = "2";
    public static final String STAT_TYPE_SAN = "3";
    public static final String STAT_TYPE_SI = "4";

    public static final String STAT_TYPE_LING_YI = "01";
    public static final String STAT_TYPE_LING_LING = "00";

    public static final String STAT_ORG_ID = "woYaoChaSuoYou";



}
