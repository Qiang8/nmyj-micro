package com.taiji.cdp.report.service;

import com.taiji.cdp.base.midAtt.vo.MidAttSaveVo;
import com.taiji.cdp.report.common.enums.ConsInfoStatusEnum;
import com.taiji.cdp.report.common.enums.FlowLogNodeEnum;
import com.taiji.cdp.report.feign.*;
import com.taiji.cdp.report.vo.ConsInfoSaveVo;
import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.cdp.report.vo.ConsJudgeVo;
import com.taiji.cdp.report.vo.FlowLogVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ConsInfoService extends BaseService{

    ConsInfoClient client;
    TopicConsClient topicConsClient;
    MidAttClient midAttClient;
    FlowLogClient flowLogClient;
    ConsJudgeClient consJudgeClient;

    //舆情信息填报
    public ConsInfoVo addConsInfo(ConsInfoSaveVo saveVo, OAuth2Authentication principal){
        ConsInfoVo vo = saveVo.getConsInfo();
        LinkedHashMap<String,Object> userMap = getUserMap(principal);
        LocalDateTime now = getCurrrentTime();

        if(StringUtils.isEmpty(vo.getStatus())){
            vo.setStatus(ConsInfoStatusEnum.NOT_REPORT.getCode()); //若未传值,设置为未上报状态
        }

        vo.setCreateOrgId(userMap.get("orgId").toString()); //创建单位
        vo.setCreateOrgName(userMap.get("orgName").toString());//创建单位名称

        vo.setLastDealTime(now); //最新办理时间

        String account = userMap.get("username").toString();
        vo.setLastDealUser(account); //最新办理人

        if(!StringUtils.isEmpty(vo.getSourceTypeId())){ //舆情来源类型
            vo.setSourceTypeName(getDicItemNameById(vo.getSourceTypeId()));
        }

        vo.setCreateBy(account);
        vo.setUpdateBy(account);

        ResponseEntity<ConsInfoVo> result = client.create(vo);
        ConsInfoVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);

        List<String> fileIds = saveVo.getFileIds();
        List<String> fileDeleteIds = saveVo.getFileDeleteIds();
        if(!(CollectionUtils.isEmpty(fileIds)&&CollectionUtils.isEmpty(fileDeleteIds))){
            //更新附件部分
            ResponseEntity<Void> fileResult = midAttClient.saveMidAtts(new MidAttSaveVo(resultVo.getId(),fileIds,fileDeleteIds));
            ResponseEntityUtils.achieveResponseEntityBody(fileResult);
        }

        return resultVo;

    }

    //修改舆情信息
    public ConsInfoVo updateConsInfo(ConsInfoSaveVo saveVo,String id,OAuth2Authentication principal){
        ConsInfoVo vo = saveVo.getConsInfo();
        LinkedHashMap<String,Object> userMap = getUserMap(principal);
        LocalDateTime now = getCurrrentTime();

        vo.setLastDealTime(now); //最新办理时间

        String account = userMap.get("username").toString();
        vo.setLastDealUser(account); //最新办理人

        if(!StringUtils.isEmpty(vo.getSourceTypeId())){ //舆情来源类型
            vo.setSourceTypeName(getDicItemNameById(vo.getSourceTypeId()));
        }

        vo.setUpdateBy(account);

        ResponseEntity<ConsInfoVo> result = client.update(vo,id);
        ConsInfoVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);

        List<String> fileIds = saveVo.getFileIds();
        List<String> fileDeleteIds = saveVo.getFileDeleteIds();
        if(!(CollectionUtils.isEmpty(fileIds)&&CollectionUtils.isEmpty(fileDeleteIds))){
            //更新附件部分
            ResponseEntity<Void> fileResult = midAttClient.saveMidAtts(new MidAttSaveVo(resultVo.getId(),fileIds,fileDeleteIds));
            ResponseEntityUtils.achieveResponseEntityBody(fileResult);
        }

        return resultVo;
    }

    //获取单条舆情信息
    public ConsInfoVo findConsInfoById(String id){
        ResponseEntity<ConsInfoVo> result = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //删除舆情信息
    public void deleteConsInfo(String id){
        ResponseEntity<Void> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //舆情信息上报，更新ConsInfo表的status和receiveOrgCode字段
    public void sendInfo(String infoId,String receiveOrgCode,OAuth2Authentication principal){
        ResponseEntity<ConsInfoVo> result = client.findOne(infoId);
        ConsInfoVo infoVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        Assert.notNull(infoVo,"infoVo 不能为null");
        LocalDateTime now = getCurrrentTime();
        //LocalDateTime now = LocalDateTime.now();
        infoVo.setReceiveOrgCode(receiveOrgCode);
        infoVo.setStatus(ConsInfoStatusEnum.REPORTED.getCode()); //设置成已上报
        infoVo.setReportTime(now); //上报时间
        infoVo.setLastDealUser(principal.getName()); //当前账户
        infoVo.setLastDealTime(now); //设置成当前时间
        ResponseEntity<ConsInfoVo> updateResult = client.update(infoVo,infoId);
        ResponseEntityUtils.achieveResponseEntityBody(updateResult);

        //保存流程节点日志信息
        FlowLogVo flowLogVo = new FlowLogVo();
        //id,username,password,status,delFlag,orgId,orgName,roleList
        LinkedHashMap<String, Object> userMap = getUserMap(principal);
        flowLogVo.setEntityId(infoId);
        flowLogVo.setFlowNode(FlowLogNodeEnum.REPORT.getFlowNode());
        flowLogVo.setDealUserId(userMap.get("id").toString());
        flowLogVo.setDealOrgId(userMap.get("orgId").toString());
        flowLogVo.setDealOrgName(userMap.get("orgName").toString());
        flowLogVo.setDealUserName(userMap.get("username").toString());

        flowLogVo.setDealTime(now);
        flowLogVo.setDealContent("上报");
        flowLogClient.addConsFlows(flowLogVo);
    }

    //根据条件查询舆情信息列表-分页（盟市）
    public RestPageImpl<ConsInfoVo> findConsInfos(Map<String,Object> map,OAuth2Authentication principal){
        //创建部门ID，为空时默认为登录用户所属单位ID
        if((!map.containsKey("orgId"))||StringUtils.isEmpty(map.get("orgId").toString())){
            LinkedHashMap<String,Object> userMap = getUserMap(principal);
            String orgId = userMap.get("orgId").toString();
            map.put("orgId",orgId);
        }
        ResponseEntity<RestPageImpl<ConsInfoVo>> result = client.findPage(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询舆情信息列表-分页（自治区）
    public RestPageImpl<ConsInfoVo> findConsInfoVos(Map<String,Object> map){
        ResponseEntity<RestPageImpl<ConsInfoVo>> result = client.findRecPage(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }
    //根据条件查询舆情信息列表-不分页
    public List<ConsInfoVo> findConsInfosAll(Map<String,Object> map){
        ResponseEntity<List<ConsInfoVo>> result = client.findRecList(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    // 当舆情进行上报、研判、短信发送、审批通过、审批退回、生成调控单、舆情完成（结束）等操作时，通过前台传参，
    // 后台将CR_CONS_INFO表中的status更新为对应的舆情状态，
    // 并且将last_deal_user更新为当前登录用户名称、last_deal_time更新为系统当前操作时间
    public void updateConsStatus(String infoId,String status,OAuth2Authentication principal){
        ResponseEntity<ConsInfoVo> result = client.findOne(infoId);
        ConsInfoVo infoVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        Assert.notNull(infoVo,"infoVo 不能为null");
        infoVo.setStatus(status); //设置状态
        infoVo.setLastDealUser(principal.getName()); //当前账户
        infoVo.setLastDealTime(getCurrrentTime()); //设置成当前时间
        ResponseEntity<ConsInfoVo> updateResult = client.update(infoVo,infoId);
        /*
        保存流程节点日志
        2已研判 3短信上报 4已审批通过 5已退回 6处置中 7已完成 8已忽略
         */
        if(!StringUtils.isEmpty(status)){
            if(ConsInfoStatusEnum.HANDLING.getCode().equals(status)||ConsInfoStatusEnum.COMPLETED.getCode().equals(status)||ConsInfoStatusEnum.IGNORED.getCode().equals(status)||ConsInfoStatusEnum.JUDGED.getCode().equals(status)){
                FlowLogVo flowLogVo = new FlowLogVo();
                if(ConsInfoStatusEnum.JUDGED.getCode().equals(status)){
                    flowLogVo.setFlowNode(FlowLogNodeEnum.JUDGE.getFlowNode());
                    ResponseEntity<List<ConsJudgeVo>> byJudgeId = consJudgeClient.findByJudgeId(infoId);
                    List<ConsJudgeVo> resultVo = ResponseEntityUtils.achieveResponseEntityBody(byJudgeId);
                    if(!CollectionUtils.isEmpty(resultVo)&&resultVo!=null){
                        flowLogVo.setDealContent("研判意见："+resultVo.get(0).getOpinion());
                    }
                }
                if(ConsInfoStatusEnum.HANDLING.getCode().equals(status)){
                    flowLogVo.setFlowNode(FlowLogNodeEnum.CMD.getFlowNode());
                    flowLogVo.setDealContent("生成调控单，处置中");
                }
                if(ConsInfoStatusEnum.COMPLETED.getCode().equals(status)){
                    flowLogVo.setFlowNode(FlowLogNodeEnum.CMD_END.getFlowNode());
                    flowLogVo.setDealContent("处置完成");
                }
                if(ConsInfoStatusEnum.IGNORED.getCode().equals(status)){
                    flowLogVo.setFlowNode(FlowLogNodeEnum.IGNORE.getFlowNode());
                    flowLogVo.setDealContent("已删除处理");
                }
                //增加流程节点日志信息
                //id,username,password,status,delFlag,orgId,orgName,roleList
                LinkedHashMap<String, Object> userMap = getUserMap(principal);
                flowLogVo.setEntityId(infoId);
                flowLogVo.setDealOrgName(userMap.get("orgName").toString());
                flowLogVo.setDealUserName(userMap.get("username").toString());
                flowLogVo.setDealUserId(userMap.get("id").toString());
                flowLogVo.setDealOrgId(userMap.get("orgId").toString());
                flowLogVo.setDealTime(getCurrrentTime());
                flowLogClient.addConsFlows(flowLogVo);
            }
        }
        ResponseEntityUtils.achieveResponseEntityBody(updateResult);
    }

    //根据舆情ID数组查询舆情信息列表-不分页（交接班使用）
    public List<ConsInfoVo> findConsInfosByIds(List<String> infoIds){
        ResponseEntity<List<ConsInfoVo>> result = client.findList(infoIds);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据重点专题ID查询舆情信息列表-分页（重点专题使用）
    public RestPageImpl<ConsInfoVo> findTopicConsInfos(Map<String,Object> map){
        ResponseEntity<RestPageImpl<ConsInfoVo>> result = topicConsClient.findPageByTopic(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

}
