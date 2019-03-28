package com.taiji.cdp.base.sms.service;

import com.taiji.cdp.base.midAtt.service.BaseService;
import com.taiji.cdp.base.sms.feign.SmsTempClient;
import com.taiji.cdp.base.sms.vo.SmsTempVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsTempService extends BaseService {

    @Autowired
    private SmsTempClient client;

    //新增短信模板
    public void create(SmsTempVo smsTempVo, OAuth2Authentication principal){
        String account = principal.getName();
        smsTempVo.setCreateBy(account);
        smsTempVo.setUpdateBy(account);
        ResponseEntity<SmsTempVo> result = client.create(smsTempVo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //修改短信模板
    public void update(SmsTempVo smsTempVo,String id, OAuth2Authentication principal){
        smsTempVo.setUpdateBy(principal.getName());
        ResponseEntity<SmsTempVo> result = client.update(smsTempVo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //获取单条短信模板
    public SmsTempVo findOne(String id){
        ResponseEntity<SmsTempVo> result = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //删除短信模板
    public void deleteLogic(String id){
        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //短信模板 --分页查询
    public RestPageImpl<SmsTempVo> findPage(Map<String,Object> map){
        ResponseEntity<RestPageImpl<SmsTempVo>> result = client.findPage(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

}
