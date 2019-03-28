package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
@EnableDiscoveryClient
@SpringCloudApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MicroBaseMsgApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroBaseMsgApplication.class, args);
    }
}
