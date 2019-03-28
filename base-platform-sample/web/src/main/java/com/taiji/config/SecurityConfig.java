package com.taiji.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <p>Title:SecurityConfig.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年07月18</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@EnableOAuth2Sso
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }
}
