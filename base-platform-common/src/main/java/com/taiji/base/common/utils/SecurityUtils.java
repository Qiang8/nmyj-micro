package com.taiji.base.common.utils;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.LinkedHashMap;

/**
 * <p>
 * <p>Title:SecurityUtils.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/31 9:55</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
public class SecurityUtils {

    public static LinkedHashMap<String,Object> getPrincipalMap(OAuth2Authentication principal){

        try {
            LinkedHashMap<String,Object> object = (LinkedHashMap<String, Object>) principal
                    .getUserAuthentication().getDetails();

            return (LinkedHashMap<String, Object>)object.get("principal");
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }
}
