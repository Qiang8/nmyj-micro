package com.taiji.base.sample.job;

import com.taiji.base.sample.vo.WebSocketMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title:SystemDateJob.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/12 10:42</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Component
public class SystemDateJob {
    public static Logger logger = LoggerFactory.getLogger(SystemDateJob.class);

    private static       SimpleDateFormat sdf               = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String           TOPIC_SYSTEM_DATE = "/topic/system-date";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Scheduled(cron = "0/60 * * * * ?")  //每隔1秒执行一次定时任务
    public void systemDate() {
        try {
            WebSocketMessage message = new WebSocketMessage();
            message.setMessage(sdf.format(new Date()));
            messagingTemplate.convertAndSend(TOPIC_SYSTEM_DATE, message);
        } catch (Exception e) {
            logger.error("system date send exception" + e.getMessage(), e);
        }
    }
}
