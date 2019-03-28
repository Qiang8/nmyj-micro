package com.taiji.cdp.daily.issue.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.cdp.base.midAtt.vo.MidAttSaveVo;
import com.taiji.cdp.daily.daily.feign.DicItemClient;
import com.taiji.cdp.daily.monthly.feign.MidAttClient;
import com.taiji.micro.common.feign.UtilsFeignClient;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    UtilsFeignClient utilsFeignClient;
    @Autowired
    DicItemClient dicItemClient;
    @Autowired
    MidAttClient midAttClient;

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

    /**
     * @param id 实体主键
     * @param fileIds 附件id
     * @param deleteIds 被删除附件id
     */
    public void saveMidAttSaveVo(String id, List<String> fileIds, List<String> deleteIds){
        MidAttSaveVo midAttSaveVo = new MidAttSaveVo();
        midAttSaveVo.setEntityId(id);
        midAttSaveVo.setFileIds(fileIds);
        midAttSaveVo.setDeleteIds(deleteIds);
        midAttClient.saveMidAtts(midAttSaveVo);
    }
}
