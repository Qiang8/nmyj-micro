package com.taiji.cdp.base.midAtt.service;

import com.taiji.cdp.base.midAtt.feign.MidAttClient;
import com.taiji.cdp.base.midAtt.vo.AttachmentVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MidAttService extends BaseService{

    MidAttClient client;

    /**
     * 根据业务实体ID获取附件对象列表
     */
    public List<AttachmentVo> getAttsByEntityId(String entityId){
        ResponseEntity<List<AttachmentVo>> result = client.findAttsByEntityId(entityId);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

}
