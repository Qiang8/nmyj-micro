package com.taiji.config;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

/**
 * <p>Title:FeignAuthConfig.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年06月14</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Configuration
public class FeignAuthConfig {

    @Value("${feign.security.username}")
    private String feignSecurityUsername;

    @Value("${feign.security.password}")
    private String feignSecurityPassword;

//    @Bean
//    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor(feignSecurityUsername,feignSecurityPassword);
//    }

    @Bean
    public OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory factory) {
        return factory.getUserInfoRestTemplate();
    }

    @Bean
    public OAuth2RequestInterceptor auth2RequestInterceptor(OAuth2RestTemplate oauth2RestTemplate) {
        return new OAuth2RequestInterceptor(oauth2RestTemplate);
    }
}
