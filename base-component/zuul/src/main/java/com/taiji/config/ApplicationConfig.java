package com.taiji.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.taiji.filter.ZuulDebugFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title:ApplicationConfig.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/30 16:58</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Configuration
public class ApplicationConfig {

    @Autowired
    ObjectMapper objectMapper;

    @ConditionalOnProperty(prefix = "zuul",name = {"debug-filter","debugFilter"},havingValue = "true")
    @Bean
    public ZuulFilter zuulFilter() {
        return new ZuulDebugFilter(objectMapper);
    }
}
