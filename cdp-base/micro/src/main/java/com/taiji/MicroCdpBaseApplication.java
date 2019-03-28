package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableDiscoveryClient
@SpringCloudApplication
@EnableJpaAuditing
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MicroCdpBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroCdpBaseApplication.class, args);
    }
}
