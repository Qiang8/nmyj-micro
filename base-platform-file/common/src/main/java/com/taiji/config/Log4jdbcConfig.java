package com.taiji.config;

import net.sf.log4jdbc.DataSourceSpyInterceptor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>Title:Log4jdbcConfig.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/22 20:26</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Configuration
public class Log4jdbcConfig {

    /**
     * log4jdbc sql日志打印过滤器
     */
    @Bean
    public DataSourceSpyInterceptor log4jdbcInterceptor() {
        return new DataSourceSpyInterceptor();
    }

    /**
     * log4jdbc sql日志打印过滤器
     */
    @Bean
    public BeanNameAutoProxyCreator dataSourceLog4jdbcAutoProxyCreator() {
        BeanNameAutoProxyCreator  beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        beanNameAutoProxyCreator.setBeanNames("dataSource");

        beanNameAutoProxyCreator.setInterceptorNames("log4jdbcInterceptor");
        return beanNameAutoProxyCreator;
    }
}
