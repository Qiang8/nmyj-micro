package com.taiji.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.MultipartConfigElement;

@Component
public class TomcatTmpDir {

    @Value("${server.tomcat.basedir}")
    private String tomcatTmp;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(tomcatTmp);
        return factory.createMultipartConfig();
    }

}
