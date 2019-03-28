package com.taiji.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * <p>Title:RevokeTokenEndpoint.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/6 17:59</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("clientId")
            .secret("secret")
//            .accessTokenValiditySeconds(1800)
            .authorizedGrantTypes("authorization_code","client_credentials","implicit","password","refresh_token")
//            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT","ROLE_USER","ROLE_ACTUATOR")
            .scopes("user_info","read","write","server")
            .autoApprove(true);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                //jwt redis token store 必须设置reuseRefreshTokens为false，否则oauth/token refresh token后返回的refresh_token再次刷新出错 invalid refresh token。
                //https://github.com/spring-projects/spring-security-oauth/issues/1267
                .reuseRefreshTokens(false);

        endpoints.exceptionTranslator(webResponseExceptionTranslator());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("taiji");
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        RedisTokenStore redis = new RedisTokenStore(connectionFactory);
        return redis;
    }

    @Bean
    public ApprovalStore approvalStore() throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore());
        return store;
    }

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {

            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                OAuth2Exception body = responseEntity.getBody();
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());

                log.error(e.getMessage(),e);

                body.addAdditionalInformation("code","4002");
                body.addAdditionalInformation("msg","用户名或密码错误");

                return new ResponseEntity<>(body, headers, HttpStatus.OK);
            }
        };
    }
}
