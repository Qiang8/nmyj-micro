package com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WebSocketBaseMsgApplication extends SpringBootServletInitializer {

    /**
     * RequestContextListener 必须
     * org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'scopedTarget.oauth2ClientContext': Scope 'session' is not active for the current thread; consider defining a scoped proxy for this bean if you intend to refer to it from a singleton; nested exception is java.lang.IllegalStateException: No thread-bound request found: Are you referring to request attributes outside of an actual web request, or processing a request outside of the originally receiving thread? If you are actually operating within a web request and still receive this message, your code is probably running outside of DispatcherServlet/DispatcherPortlet: In this case, use RequestContextListener or RequestContextFilter to expose the current request.
     */
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(WebSocketBaseMsgApplication.class, args);
    }
}
