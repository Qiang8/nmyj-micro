package com.taiji.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.Assert;

/**
 * <p>Title:OAuth2RequestInterceptor.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/14 16:03</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class OAuth2RequestInterceptor implements RequestInterceptor {
    private final        Logger LOGGER               = LoggerFactory.getLogger(getClass());
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final OAuth2RestTemplate oAuth2RestTemplate;


    public OAuth2RequestInterceptor(OAuth2RestTemplate oAuth2RestTemplate) {
        Assert.notNull(oAuth2RestTemplate, "Context can not be null");
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @Override
    public void apply(RequestTemplate template) {
        LOGGER.debug("Constructing Header {} for Token {}", AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE);
        template.header(AUTHORIZATION_HEADER,
                        String.format("%s %s",
                                      BEARER_TOKEN_TYPE,
                                      oAuth2RestTemplate.getAccessToken().toString()));

    }
}
