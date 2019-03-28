package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @program: nmyj-micro
 * @description: 启动类
 * @author: TaiJi.WangJian
 * @create: 2019-01-03 15:50
 **/
@EnableDiscoveryClient
@SpringCloudApplication
@EnableJpaAuditing
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MicroCdpCmdApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroCdpCmdApplication.class, args);
    }
}