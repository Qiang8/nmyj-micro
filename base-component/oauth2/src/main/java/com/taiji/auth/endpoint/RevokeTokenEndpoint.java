package com.taiji.auth.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title:RevokeTokenEndpoint.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/6 17:59</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FrameworkEndpoint
public class RevokeTokenEndpoint {

    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;

    /**
     * 登出需要带 Authorization: Basic Y2xpZW50SWQ6c2VjcmV0
     * @param parameters
     */
    @DeleteMapping("/oauth/token")
    @ResponseBody
    public Map<String,Object> revokeToken(@RequestParam Map<String, String> parameters) {
        String accessToken = parameters.get("access_token");

        Map<String,Object> map = new HashMap<>();
        if (consumerTokenServices.revokeToken(accessToken)){
            map.put("result",true);
        }else{
            map.put("result",false);
        }

        return map;
    }
}
