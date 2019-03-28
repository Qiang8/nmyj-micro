package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
@EnableDiscoveryClient
@SpringCloudApplication
@EnableJpaAuditing
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MicroBaseContactApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroBaseContactApplication.class, args);
    }
}
