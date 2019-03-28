package com.taiji.base.common.msg;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于消息组件，msgType配置类，与数据库对应
 * */
public class MsgSendGlobal {

    public final static Map<String,MsgConfig> msgTypeMap = new HashMap<>();


    /**
     * 任务管理
     */
    public final static String MSG_TYPE_TASK_NEWS_ID = "0001";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("任务管理");
        msgConfig.setCode("emg_taskmags_news");
        msgConfig.setPath("emg_taskmags");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_TASK_NEWS_ID,msgConfig);
    }

    /**
     * 舆情任务信息标题
     */
    public final static String MSG_INFO_TASK_TITLE = "舆情任务";

}
