package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * <p>Title:MicroBaseMsgApplication.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/23 10:15</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringCloudApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@IntegrationComponentScan
@EnableIntegration
public class MicroFileFtpApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroFileFtpApplication.class, args);
    }
}
