package com.taiji.cdp.cmd.service;

import com.taiji.cdp.base.midAtt.vo.MidAttSaveVo;
import com.taiji.cdp.cmd.feign.FeedbackClient;
import com.taiji.cdp.cmd.feign.MidAttClient;
import com.taiji.cdp.cmd.vo.FeedbackSaveVo;
import com.taiji.cdp.cmd.vo.FeedbackVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackService extends BaseService{

    FeedbackClient client;
    MidAttClient midAttClient;

    /**
     * 新增处置反馈
     */
    public void create(FeedbackSaveVo saveVo, OAuth2Authentication principal){
        FeedbackVo vo = saveVo.getFeedback();
        LinkedHashMap<String,Object> userMap = getUserMap(principal);
        String account = userMap.get("username").toString();
        String orgId = userMap.get("orgId").toString();
        vo.setOrgId(orgId);
        vo.setFeedbackBy(account);
        ResponseEntity<FeedbackVo> result = client.create(vo);
        FeedbackVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        saveMidAttSaveVo(resultVo.getId(),saveVo.getFileIds(),saveVo.getFileDeleteIds());

    }


    /**获取单条处置反馈信息*/
    public FeedbackVo findOne(String id){
        ResponseEntity<FeedbackVo> result = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }


    /**查询所以处置反馈信息*/
    public List<FeedbackVo> findList(){
        ResponseEntity<List<FeedbackVo>> result = client.findList();
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * @param id 实体主键
     * @param fileIds 附件id
     * @param deleteIds 被删除附件id
     */
    private void saveMidAttSaveVo(String id, List<String> fileIds, List<String> deleteIds){
        MidAttSaveVo midAttSaveVo = new MidAttSaveVo();
        midAttSaveVo.setEntityId(id);
        midAttSaveVo.setFileIds(fileIds);
        midAttSaveVo.setDeleteIds(deleteIds);
        midAttClient.saveMidAtts(midAttSaveVo);
    }
}
