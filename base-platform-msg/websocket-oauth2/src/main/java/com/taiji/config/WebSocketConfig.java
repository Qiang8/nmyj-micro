package com.taiji.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * <p>Title:WebSocketConfig.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/12 10:34</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.relay-host}")
    String relayHost;
    @Value("${spring.rabbitmq.relay-port}")
    int relayPort;
    @Value("${spring.rabbitmq.client-login}")
    String clientLogin;
    @Value("${spring.rabbitmq.client-passcode}")
    String clientPasscode;
    @Value("${spring.rabbitmq.system-login}")
    String systemLogin;
    @Value("${spring.rabbitmq.system-passcode}")
    String systemPasscode;

    @Autowired
    UserInfoTokenServices userInfoTokenServices;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

//        config.enableSimpleBroker("/topic", "/queue" ,"/user");
        config.setApplicationDestinationPrefixes("/app");
//        config.setUserDestinationPrefix("/user");

        config.enableStompBrokerRelay("/topic", "/queue").
                setRelayHost(relayHost)
                .setRelayPort(relayPort)
                .setClientLogin(clientLogin)
                .setClientPasscode(clientPasscode)
                .setSystemLogin(systemLogin)
                .setSystemPasscode(systemPasscode);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptorAdapter() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {

                    String jwtToken = accessor.getFirstNativeHeader("Auth-Token");
                    log.debug("webSocket token is {}", jwtToken);

                    OAuth2Authentication oAuth2Authentication = userInfoTokenServices.loadAuthentication(jwtToken);

                    if (StringUtils.isNotEmpty(jwtToken) && oAuth2Authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(oAuth2Authentication);
                        accessor.setUser(oAuth2Authentication);
                    }
                }

                return message;
            }
        });
    }
}
