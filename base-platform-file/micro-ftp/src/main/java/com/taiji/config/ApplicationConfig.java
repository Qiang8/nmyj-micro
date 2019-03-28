package com.taiji.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <p>Title:ApplicationConfig.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/23 10:15</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Configuration
public class ApplicationConfig {

    @Value("${sftp.host}")
    private String sftpHost;

    @Value("${sftp.port}")
    private int sftpPort;

    @Value("${sftp.user}")
    private String sftpUser;

    @Value("${sftp.password}")
    private String sftpPasword;

    @Value("${sftp.remote.directory}")
    private String sftpRemoteDirectory;

    @Value("${sftp.remote.FileSeparator}")
    private String sftpRemoteFileSeparator;

    @Bean
    public DefaultFtpSessionFactory ftpSessionFactory() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost(sftpHost);
        sf.setPort(sftpPort);
        sf.setUsername(sftpUser);
        sf.setPassword(sftpPasword);
        return sf;
    }

    @Bean
    public FtpRemoteFileTemplate template(DefaultFtpSessionFactory sf) {
        FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(sf);
        template.setAutoCreateDirectory(true);
        template.setRemoteDirectoryExpression(new LiteralExpression(sftpRemoteDirectory));
        template.setUseTemporaryFileName(true);
        template.setRemoteFileSeparator(sftpRemoteFileSeparator);
        return template;
    }

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
}
