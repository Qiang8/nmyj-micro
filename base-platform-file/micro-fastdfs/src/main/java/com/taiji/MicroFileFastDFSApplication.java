package com.taiji;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * <p>Title:MicroFileFastDFSApplication.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/23 10:15</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@EnableDiscoveryClient
@SpringCloudApplication
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MicroFileFastDFSApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroFileFastDFSApplication.class, args);
    }
}
