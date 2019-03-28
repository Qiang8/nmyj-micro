package com.taiji.base.msg.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.base.msg.vo.WebSocketMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>Title:MsgSender.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/7 10:04</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class MsgSender {

    private RabbitTemplate rabbitTemplate;

    public void send() {
        for (int i =0;i<10;i++)
        {
            WebSocketMessage<String> message = new WebSocketMessage<>();
            message.setType("test" + i);
            message.setMessage("hello" + new Date());

            log.debug("message : {}", message);

            this.rabbitTemplate.convertAndSend("msgFanoutExchange", null, message);
        }
    }
}
