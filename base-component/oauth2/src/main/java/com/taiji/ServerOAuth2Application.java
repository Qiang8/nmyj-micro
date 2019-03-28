package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * <p>Title:RevokeTokenEndpoint.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/6 17:59</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringCloudApplication
public class ServerOAuth2Application extends SpringBootServletInitializer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(OAuth2ServerApplication.class);
//    }
    public static void main(String[] args) {
        SpringApplication.run(ServerOAuth2Application.class);
    }
}