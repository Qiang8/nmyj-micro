package com.taiji;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;

/**
 * <p>Title:EurekaServerApplication.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年05月03</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@SpringCloudApplication
@EnableEurekaServer
public class ServerEurekaApplication {
    protected static final Logger log = LoggerFactory.getLogger(ServerEurekaApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ServerEurekaApplication.class, args);

        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            log.warn("EurekaServerApplication 使用profile为:{}" , profile);
        }
    }
}
