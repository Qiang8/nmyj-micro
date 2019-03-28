package com.taiji.base.sample.controller;

import com.taiji.base.sample.vo.WebSocketMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

    protected static final Logger log = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    SimpUserRegistry userRegistry;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/broadcast")
    @SendTo("/topic/broadcast")
    public WebSocketMessage helloBroadcast(WebSocketMessage message, StompHeaderAccessor accessor) throws Exception {

        log.debug("UserCount {}", userRegistry.getUserCount());

        return message;
    }

    @MessageMapping("/to-user")
    @SendToUser("/queue/to-user")
    public void helloToUser(WebSocketMessage message, StompHeaderAccessor accessor)  {
        String msg = message.getMessage();
        message.setMessage("HelloBroadcast, "+ msg + "。From：" + message.getFromUser() + "!");

        String toUser = message.getToUser();
        if(StringUtils.isBlank(toUser))
        {
            messagingTemplate.convertAndSendToUser(message.getToUser(),"/queue/to-user",message);
        }
    }
}
