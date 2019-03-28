package com.taiji.cdp.report.service;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.sys.vo.OrgVo;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.cdp.msgService.InfoMsgService;
import com.taiji.cdp.report.common.enums.TaskReadFlagEnum;
import com.taiji.cdp.report.common.enums.TaskSendFlagEnum;
import com.taiji.cdp.report.feign.OrgClient;
import com.taiji.cdp.report.feign.TaskClient;
import com.taiji.cdp.report.feign.UserClient;
import com.taiji.cdp.report.feign.UtilsFeignClient;
import com.taiji.cdp.report.vo.TaskExeOrgVo;
import com.taiji.cdp.report.vo.TaskReceiveVo;
import com.taiji.cdp.report.vo.TaskSaveVo;
import com.taiji.cdp.report.vo.TaskVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService extends BaseService{

    TaskClient client;
    OrgClient orgClient;
    UserClient userClient;
    UtilsFeignClient utilsFeignClient;
    private InfoMsgService infoMsgService;
    //新增任务信息
    public TaskSaveVo addTaskVo(TaskSaveVo vo, OAuth2Authentication principal){
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        TaskVo task =vo.getTask();
        Assert.notNull(task,"TaskVo 不能为null");

        List<String> orgIds = vo.getOrgIds();
        Map<String,Object> orgMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(orgIds)){
            ResponseEntity<List<OrgVo>> orgList= orgClient.findOrgInfo(orgIds);
            List<OrgVo> orgVos = ResponseEntityUtils.achieveResponseEntityBody(orgList);
            orgMap = orgVos.stream().collect(Collectors.toMap(OrgVo::getId,OrgVo::getOrgName));
        }
        task.setOrgMap(orgMap);

        task.setTaskStatus(TaskSendFlagEnum.NOT_SEND.getCode());
        task.setCreateOrgId(userMap.get("orgId").toString());
        task.setCreateOrgName(userMap.get("orgName").toString());
        String account = userMap.get("username").toString();
        task.setCreateBy(account);
        task.setUpdateBy(account);

        ResponseEntity<TaskVo> result = client.create(task);
        TaskVo voResult = ResponseEntityUtils.achieveResponseEntityBody(result);
        Map<String,Object> orgResult = voResult.getOrgMap();
        List<String> orgs = new ArrayList<>(orgResult.keySet());
        return new TaskSaveVo(voResult,orgs);
    }

    //更新任务信息
    public TaskSaveVo updateTaskVo(TaskSaveVo vo, String id,OAuth2Authentication principal){
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        TaskVo task =vo.getTask();
        Assert.notNull(task,"TaskVo 不能为null");
        Assert.hasText(id,"id 不能为空字符串");

        List<String> orgIds = vo.getOrgIds();
        Map<String,Object> orgMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(orgIds)){
            ResponseEntity<List<OrgVo>> orgList= orgClient.findOrgInfo(orgIds);
            List<OrgVo> orgVos = ResponseEntityUtils.achieveResponseEntityBody(orgList);
            orgMap = orgVos.stream().collect(Collectors.toMap(OrgVo::getId,OrgVo::getOrgName));
        }
        task.setOrgMap(orgMap);

        String account = userMap.get("username").toString();
        task.setUpdateBy(account);

        ResponseEntity<TaskVo> result = client.update(task,id);
        TaskVo voResult = ResponseEntityUtils.achieveResponseEntityBody(result);
        Map<String,Object> orgResult = voResult.getOrgMap();
        List<String> orgs = new ArrayList<>(orgResult.keySet());
        return new TaskSaveVo(voResult,orgs);
    }

    //获取单条舆情信息
    public TaskSaveVo findTaskVoById(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ResponseEntity<TaskVo> result = client.findOneTask(id);
        TaskVo voResult = ResponseEntityUtils.achieveResponseEntityBody(result);
        Map<String,Object> orgResult = voResult.getOrgMap();
        List<String> orgs = new ArrayList<>(orgResult.keySet());
        return new TaskSaveVo(voResult,orgs);
    }

    //删除任务信息
    public void deleteTask(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ResponseEntity<Void> result = client.deleteTask(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //根据条件查询任务信息列表-分页  --发送方
    public RestPageImpl<TaskVo> findTasks(Map<String,Object> map, OAuth2Authentication principal){
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String userOrgId = "";
        if(map.containsKey("orgId")){
            userOrgId = map.get("orgId").toString();
        }
        if(StringUtils.isEmpty(userOrgId)){ //如果是空
            userOrgId = userMap.get("orgId").toString();
        }
        map.put("orgId",userOrgId);

        ResponseEntity<RestPageImpl<TaskVo>> result = client.search(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //任务信息下发，后台更新Task表的status和sendTime字段，并且将接收部门的人员的相关初始数据入库TaskReceive表
    public void sendTask(String taskId, OAuth2Authentication principal){
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String account = userMap.get("username").toString();
        Assert.hasText(taskId,"taskId 不能为空字符串");
        ResponseEntity<TaskVo> result = client.findOneTask(taskId);
        TaskVo voResult = ResponseEntityUtils.achieveResponseEntityBody(result);
        Map<String,Object> orgResult = voResult.getOrgMap();
        List<String> orgs = new ArrayList<>(orgResult.keySet());
        LocalDateTime nowTime = utilsFeignClient.now().getBody();
        //添加接收人对象
        ResponseEntity<List<UserVo>> userResult= userClient.findListByOrgIds(orgs);
        List<UserVo> users = ResponseEntityUtils.achieveResponseEntityBody(userResult);
        if(!CollectionUtils.isEmpty(users)){
            List<TaskReceiveVo> receiveVos = new ArrayList<>();
            for(UserVo user:users){
                TaskReceiveVo receiveVo = new TaskReceiveVo();
                UserProfileVo profileVo = user.getProfile();
                receiveVo.setOrgId(profileVo.getOrgId());
                receiveVo.setOrgName(profileVo.getOrgName());
                receiveVo.setReadFlag(TaskReadFlagEnum.NOT_READ.getCode());
                receiveVo.setTask(voResult);
                receiveVo.setReceiverId(user.getId());
                receiveVo.setReceiverName(profileVo.getName());
                receiveVo.setSendBy(account);
                receiveVos.add(receiveVo);
            }
            ResponseEntity<List<TaskReceiveVo>> createRecList = client.createReceivers(receiveVos);
            ResponseEntityUtils.achieveResponseEntityBody(createRecList);
        }else{
            return; //如果没有接收者则直接结束
        }
        voResult.setTaskStatus(TaskSendFlagEnum.HAS_SEND.getCode());
        voResult.setSendTime(nowTime);
        voResult.setUpdateBy(account);
        ResponseEntity<TaskVo> updateResult = client.update(voResult,taskId); //更新下发状态
        ResponseEntityUtils.achieveResponseEntityBody(updateResult);
    }

    //根据任务ID查询接收状态信息列表(获取所有接收部门的接收状态及部门下仅已接收人员的列表)
    public List<TaskExeOrgVo> findTaskRecStatus(String taskId){
        Assert.hasText(taskId,"taskId 不能为空字符串");
        ResponseEntity<List<TaskExeOrgVo>> exeOrgResult = client.viewTaskStatus(taskId);
        return ResponseEntityUtils.achieveResponseEntityBody(exeOrgResult);
    }

    //根据条件查询接收任务信息列表-分页 -- 接收方
    public RestPageImpl<TaskVo> findRecTasks(Map<String,Object> map, OAuth2Authentication principal){
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        map.put("receiveId",userMap.get("id").toString());
        ResponseEntity<RestPageImpl<TaskVo>> result = client.findRecTasks(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //查看个人任务信息，标记为已读
    public void readTasks(List<String> taskIds, OAuth2Authentication principal){
        Assert.notEmpty(taskIds,"任务ids不能为空");
        LinkedHashMap<String,Object> userMap = SecurityUtils.getPrincipalMap(principal);
        String receiveId = userMap.get("id").toString();
        String orgId = userMap.get("orgId").toString();
        Map<String,Object> map = new HashMap<>();
        map.put("taskIds",taskIds);
        map.put("receiveId",receiveId);
        map.put("orgId",orgId);
        ResponseEntity<Void> readResult = client.updateTasksByRead(convertMap2MultiValueMap(map));
        ResponseEntityUtils.achieveResponseEntityBody(readResult);
    }

    /**
     *  添加系统消息
     * @param taskId
     */
    public void sendInfoMsg(String taskId) {
        ResponseEntity<TaskVo> responseEntity = client.findOneTask(taskId);
        TaskVo taskVo = ResponseEntityUtils.achieveResponseEntityBody(responseEntity);
        if (null != taskVo){
            //添加应急任务下发系统消息
            infoMsgService.sendSystemDispatchMsg(taskVo);
        }
    }

}
