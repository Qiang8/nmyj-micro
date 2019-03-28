package com.taiji.cdp.report.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.cdp.report.feign.DicItemClient;
import com.taiji.cdp.report.feign.UtilsFeignClient;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseService {

    @Autowired
    UtilsFeignClient utilsFeignClient;
    @Autowired
    DicItemClient dicItemClient;

    public MultiValueMap<String,Object> convertMap2MultiValueMap(Map<String,Object> sourceM ){
        MultiValueMap<String,Object> multiValueMap = new LinkedMultiValueMap<>();
        for (String key : sourceM.keySet()) {
            Object obj = sourceM.get(key);
            if( obj instanceof List){
                for (Object o : (List)obj) {
                    multiValueMap.add(key,o);
                }
            }else{
                multiValueMap.add(key,sourceM.get(key));
            }
        }
        return multiValueMap;
    }

    //获取当前时间 -- 数据库时间
    public LocalDateTime getCurrrentTime(){
        ResponseEntity<LocalDateTime> now = utilsFeignClient.now();
        return ResponseEntityUtils.achieveResponseEntityBody(now);
    }

    //获取字典项
    public String getDicItemNameById(String itemId){
        ResponseEntity<String> itemNameResult = dicItemClient.findItemNameById(itemId);
        return ResponseEntityUtils.achieveResponseEntityBody(itemNameResult);
    }

    //获取用户信息
    //map包括：  id,username,password,status,delFlag,orgId,orgName,roleList;
    public LinkedHashMap<String,Object> getUserMap(OAuth2Authentication principal){
        return SecurityUtils.getPrincipalMap(principal);
    }

}
