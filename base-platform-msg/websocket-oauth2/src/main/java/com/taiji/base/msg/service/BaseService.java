package com.taiji.base.msg.service;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:BaseService.java</p >
 * <p>Description: Map转换类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
public class BaseService {

    public MultiValueMap<String,Object> convertMap2MultiValueMap( Map<String,Object> sourceM ){
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

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        map.put("test",list);
        BaseService service = new BaseService();
        MultiValueMap<String,Object> multiValueMap = service.convertMap2MultiValueMap(map);

        System.out.println(multiValueMap);
    }
}
