package com.taiji.cdp.report.service;

import com.taiji.cdp.report.common.enums.FlowLogNodeEnum;
import com.taiji.cdp.report.feign.ConsJudgeClient;
import com.taiji.cdp.report.feign.FlowLogClient;
import com.taiji.cdp.report.feign.TopicConsClient;
import com.taiji.cdp.report.vo.*;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class ConsJudgeService extends BaseService {

    ConsJudgeClient client;
    TopicConsClient topClien;
    /**
     * 新增研判信息Vo
     *
     * @param saveVo 新增研判信息vo
     * @return ConsJudgeSaveVo
     */
    public ConsJudgeSaveVo addConsJudge(ConsJudgeSaveVo saveVo, OAuth2Authentication principal) {
        Assert.notNull(saveVo, "ConsJudgeSaveVo 不能为null");
        ConsJudgeVo vo = getConsJudgeVo(saveVo);
        LinkedHashMap<String, Object> userMap = getUserMap(principal);
        String account = userMap.get("username").toString();
        vo.setCreateBy(account); //研判人
        vo.setUpdateBy(account);
        vo.setCreateTime(LocalDateTime.now());//研判时间
        vo.setUpdateTime(LocalDateTime.now());
        ResponseEntity<ConsJudgeVo> result = client.create(vo);
        TopicConsVo topvo = null;
        //新增重点舆情中间表
        if (null != saveVo.getTagType() && !saveVo.getTagType().isEmpty() && saveVo.getTagType().equals("1")) {
            TopicConsVo topVo = new TopicConsVo();
            topVo.setInfoId(saveVo.getInfoId());
            topVo.setTopicId(saveVo.getTopicId());
            topVo.setTopicName(saveVo.getTopicName());
            ResponseEntity<TopicConsVo> topResult = topClien.createOne(topVo);
            topvo = ResponseEntityUtils.achieveResponseEntityBody(topResult);
        }
        ConsJudgeVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        ConsJudgeSaveVo resultSave = getConsJudgeSaveVo(resultVo, topvo);

        return resultSave;
    }

    /**
     * 更新研判信息Vo
     *
     * @param saveVo    研判信息entity
     * @param principal 用户信息
     * @return ConsJudgeSaveVo
     */
    public ConsJudgeSaveVo updateConsJudge(ConsJudgeSaveVo saveVo, String id, OAuth2Authentication principal) {
        Assert.notNull(saveVo, "ConsJudgeSaveVo 不能为null");
        ConsJudgeVo vo = getConsJudgeVo(saveVo);
        LinkedHashMap<String, Object> userMap = getUserMap(principal);
        String account = userMap.get("username").toString();
        vo.setUpdateBy(account);
        vo.setUpdateTime(LocalDateTime.now());//研判时间
        ResponseEntity<ConsJudgeVo> result = client.update(vo, id);
        TopicConsVo topvo = null;
        //新增重点舆情中间表
        if (null != saveVo.getTagType()&& !saveVo.getTagType().isEmpty() && saveVo.getTagType().equals("1")) {
            TopicConsVo topVo = new TopicConsVo();
            topVo.setInfoId(saveVo.getInfoId());
            topVo.setTopicId(saveVo.getTopicId());
            topVo.setTopicName(saveVo.getTopicName());
            ResponseEntity<TopicConsVo> topResult = topClien.createOne(topVo);
            topvo = ResponseEntityUtils.achieveResponseEntityBody(topResult);
        }
        ConsJudgeVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        ConsJudgeSaveVo resultSave = getConsJudgeSaveVo(resultVo, topvo);
        return resultSave;
    }

    /**
     * 根据id获取单条研判信息Vo
     *
     * @param id 信息Id
     * @return ConsJudgeSaveVo
     */
    public ConsJudgeSaveVo findConsJudgeById(String id) {
        Assert.hasText(id, "id 不能为空字符串");
        ResponseEntity<ConsJudgeVo> result = client.findOne(id);
        ConsJudgeVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        TopicConsVo topvo = null;
        if (null != resultVo.getTagType() && !resultVo.getTagType().isEmpty() && resultVo.getTagType().equals("1")) {
            String infoId = resultVo.getInfoId();
            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("infoId", infoId);
            ResponseEntity<List<TopicVo>> topicList = topClien.findListByInfo(map);
            TopicVo topicVo = ResponseEntityUtils.achieveResponseEntityBody(topicList).get(0);
            topvo = new TopicConsVo();
            topvo.setId(topicVo.getId());
            topvo.setTopicName(topicVo.getName());
        }
        ConsJudgeSaveVo resultSave = getConsJudgeSaveVo(resultVo, topvo);
        return resultSave;
    }

    /**
     * 根据舆情信息id获取单条研判信息Vo
     *
     * @param infoid 信息Id
     * @return List<ConsJudgeVo>
     */
    public List<ConsJudgeSaveVo> findByInfoId(String infoid) {
        Assert.hasText(infoid, "infoid 不能为空字符串");
        ResponseEntity<List<ConsJudgeVo>> result = client.findByJudgeId(infoid);
        List<ConsJudgeVo> resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        TopicConsVo topvo = null;
        if (null != resultVo && resultVo.size() > 0) {
            ConsJudgeVo consJudgeVo = resultVo.get(0);
            if (null != consJudgeVo.getTagType()&& !consJudgeVo.getTagType().isEmpty() && consJudgeVo.getTagType().equals("1")) {
                String infoId = consJudgeVo.getInfoId();
                MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
                map.add("infoId", infoId);
                ResponseEntity<List<TopicVo>> topicList = topClien.findListByInfo(map);
                List<TopicVo> topicVos = ResponseEntityUtils.achieveResponseEntityBody(topicList);
                if(!CollectionUtils.isEmpty(topicVos)){
                    TopicVo topicVo = topicVos.get(0);
                    topvo = new TopicConsVo();
                    topvo.setId(topicVo.getId());
                    topvo.setTopicName(topicVo.getName());
                }

            }
            ConsJudgeSaveVo resultSave = getConsJudgeSaveVo(resultVo.get(0), topvo);
            List<ConsJudgeSaveVo> returnList = new ArrayList<>();
            returnList.add(resultSave);
            return returnList;
        }

        return new ArrayList<>();
    }

    /**
     * save vo 转正常vo
     */
    private ConsJudgeVo getConsJudgeVo(ConsJudgeSaveVo saveVo) {
        ConsJudgeVo vo = new ConsJudgeVo();
        List ids = saveVo.getInfoTypeIds();
        StringBuilder stringBuilder = new StringBuilder("");
        if (null != ids) {
            if (ids.size() == 1) {
                vo.setInfoTypeIds(ids.get(0).toString());
            } else {
                for (int i = 0; i < ids.size(); i++) {
                    stringBuilder.append(ids.get(i));
                    stringBuilder.append(",");
                }
            }
        }
        vo.setInfoTypeIds(stringBuilder.toString());
        vo.setInfoId(saveVo.getInfoId());
        vo.setIsInvolve(saveVo.getIsInvolve());
        vo.setOpinion(saveVo.getOpinion());
        vo.setTagType(saveVo.getTagType());
        vo.setId(saveVo.getId());
        vo.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return vo;
    }

    /**
     * 正常vo 转 saveVo
     */
    private ConsJudgeSaveVo getConsJudgeSaveVo(ConsJudgeVo vo, TopicConsVo topicConsVo) {
        ConsJudgeSaveVo saveVo = new ConsJudgeSaveVo();
        String ids = vo.getInfoTypeIds();
        List infoTypeIds = new ArrayList();
        if (null != ids && !ids.isEmpty()) {
            String[] idArr = ids.split(",");
            for (int i = 0; i < idArr.length; i++) {
                infoTypeIds.add(idArr[i]);
            }
        }
        saveVo.setInfoTypeIds(infoTypeIds);
        saveVo.setInfoId(vo.getInfoId());
        saveVo.setIsInvolve(vo.getIsInvolve());
        saveVo.setOpinion(vo.getOpinion());
        saveVo.setTagType(vo.getTagType());
        saveVo.setId(vo.getId());
        saveVo.setDelFlag(DelFlagEnum.NORMAL.getCode());
        if (null != topicConsVo) {
            saveVo.setTopicId(topicConsVo.getTopicId());
            saveVo.setTopicName(topicConsVo.getTopicName());
        }
        return saveVo;
    }
}
