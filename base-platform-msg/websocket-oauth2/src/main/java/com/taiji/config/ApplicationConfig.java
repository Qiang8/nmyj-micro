package com.taiji.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <p>Title:ApplicationConfig.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/19 10:34</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Configuration
public class ApplicationConfig {
    @Bean
    public FilterRegistrationBean corsFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(corsConfigurationSource());
        filterRegistrationBean.setOrder(SecurityProperties.DEFAULT_FILTER_ORDER);
        return filterRegistrationBean;
    }

    @Bean
    public CorsFilter corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration               corsConfiguration               = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addExposedHeader("WWW-Authenticate");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    public OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory factory) {
        return factory.getUserInfoRestTemplate();
    }

    @Bean
    public OAuth2RequestInterceptor auth2RequestInterceptor(OAuth2RestTemplate oauth2RestTemplate) {
        return new OAuth2RequestInterceptor(oauth2RestTemplate);
    }
}
