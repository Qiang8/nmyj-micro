package com.taiji.cdp.report.service;

import com.taiji.cdp.report.feign.TopicKeywordClient;
import com.taiji.cdp.report.vo.TopicKeywordVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class TopicKeywordService extends BaseService{

    TopicKeywordClient client;


    /**
     * 新增专题管理关键字
     */
    public TopicKeywordVo careat(TopicKeywordVo vo){
        ResponseEntity<TopicKeywordVo> result = client.create(vo);
        TopicKeywordVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return resultVo;

    }

    /**修改专题管理关键字*/
    public TopicKeywordVo update(TopicKeywordVo vo,String id){
        ResponseEntity<TopicKeywordVo> result = client.update(vo,id);
        TopicKeywordVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return resultVo;
    }

    /**获取单条专题信息关键字*/
    public TopicKeywordVo findOne(String id){
        ResponseEntity<TopicKeywordVo> result = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //删除专题信息关键字信息
    public void delete(String id){
        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**查询所以专题关键字信息*/
    public List<TopicKeywordVo> findListByTopicId(String topicId){
        ResponseEntity<List<TopicKeywordVo>> result = client.findList(topicId);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

}
