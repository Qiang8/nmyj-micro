package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.TaijiZuulProxyAutoConfiguration;
import org.springframework.cloud.netflix.zuul.ZuulProxyAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <p>Title:ZuulServerApplication.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年05月03</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@EnableZuulProxy
@ImportAutoConfiguration({TaijiZuulProxyAutoConfiguration.class})
@EnableDiscoveryClient
@SpringBootApplication(exclude={ZuulProxyAutoConfiguration.class})
@EnableOAuth2Sso
public class ServerZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerZuulApplication.class, args);
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.setMaxAge(18000L);
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}
