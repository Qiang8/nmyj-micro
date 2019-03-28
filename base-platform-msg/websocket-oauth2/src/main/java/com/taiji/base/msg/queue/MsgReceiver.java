package com.taiji.base.msg.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import com.taiji.base.msg.vo.WebSocketMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

/**
 * <p>Title:MsgReceiver.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/8 10:03</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Component
@RabbitListener(queues = "#{websocketMessage.name}")
@AllArgsConstructor
public class MsgReceiver {

    ObjectMapper objectMapper;

    SimpUserRegistry userRegistry;

    SimpMessagingTemplate messagingTemplate;

    @RabbitHandler
    public void process(MsgNoticeRecordVo msgNoticeRecordVo) {
        try {
            log.debug("receiver : {}",objectMapper.writeValueAsString(msgNoticeRecordVo));

            String toUser = msgNoticeRecordVo.getReceiverName();
            WebSocketMessage message = new WebSocketMessage<>();
            message.setToUser(toUser);
            message.setMessage(msgNoticeRecordVo);
            message.setType("0");

            messagingTemplate.convertAndSendToUser(toUser,"/queue/to-user",message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
