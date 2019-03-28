package com.taiji.cdp.report.service;

import com.taiji.cdp.report.common.global.TopicKeywordGlobal;
import com.taiji.cdp.report.feign.ConsInfoClient;
import com.taiji.cdp.report.feign.TopicClient;
import com.taiji.cdp.report.feign.TopicKeywordClient;
import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.cdp.report.vo.TopicKeywordVo;
import com.taiji.cdp.report.vo.TopicVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class TopicService extends BaseService{

    TopicClient client;
    ConsInfoClient consInfoclient;
    TopicKeywordClient topicKeywordClient;

    /**
     * 新增专题管理
     */
    public TopicVo create(TopicVo vo, OAuth2Authentication principal){
        LinkedHashMap<String,Object> userMap = getUserMap(principal);
        ResponseEntity<TopicVo> result = client.create(vo);
        TopicVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return resultVo;
    }

    /**修改专题管理*/
    public TopicVo update(TopicVo vo,String id,OAuth2Authentication principal){
        LinkedHashMap<String,Object> userMap = getUserMap(principal);
        LocalDateTime now = getCurrrentTime();
        String account = userMap.get("username").toString();
        vo.setUpdateBy(account);
        vo.setUpdateTime(now);
        ResponseEntity<TopicVo> result = client.update(vo,id);
        TopicVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return resultVo;
    }

    /**获取单条专题信息*/
    public TopicVo findOne(String id){
        ResponseEntity<TopicVo> result = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //删除专题信息
    public void delete(String id){
        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**查询所以专题信息*/
    public List<TopicVo> findList(){
        ResponseEntity<List<TopicVo>> result = client.findList();
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }
    /**查询所以符合条件的信息*/
    public List<TopicVo> findMatchList(String infoId){
        ConsInfoVo one = consInfoclient.findOne(infoId).getBody();
        List<TopicVo> topicList = new ArrayList<>();
        if(one!=null){
            String content = one.getContent();
            String title = one.getTitle();
            String voId = "";
            List<TopicKeywordVo> list = topicKeywordClient.findList(TopicKeywordGlobal.TOPIC_KEY_WORD_SELECT_ALL).getBody();
            for (TopicKeywordVo vo:list) {
                String keyword = vo.getKeyword();
                if(content.contains(keyword) || title.contains(keyword)){
                    if(!voId.equals(vo.getTopicId())) {
                        topicList.add(new TopicVo(vo.getTopicId(), vo.getTopicName()));
                        voId = vo.getTopicId();
                    }
                }
            }
        }
        return topicList;
    }

}
