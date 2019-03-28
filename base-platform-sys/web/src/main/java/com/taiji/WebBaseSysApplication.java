package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebBaseSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebBaseSysApplication.class, args);
    }
}
