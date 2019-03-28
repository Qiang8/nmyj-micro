package com.taiji.cdp.msgService;

import com.taiji.base.common.msg.MsgConfig;
import com.taiji.base.common.msg.MsgSendGlobal;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.cdp.msgService.feign.MsgNoticeClient;
import com.taiji.cdp.msgService.feign.UserClient;
import com.taiji.cdp.report.vo.TaskVo;
import com.taiji.micro.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 用于组装信息报送模块的消息service
 */
@Component
@AllArgsConstructor
public class InfoMsgService {

    MsgNoticeClient msgClient;
    UserClient userClient;

    public void sendSystemDispatchMsg(TaskVo taskVo) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if (null != taskVo){
                String title = MsgSendGlobal.MSG_INFO_TASK_TITLE;
                String sendOrg = taskVo.getCreateOrgId();
                String sendOrgName = taskVo.getCreateOrgName();
                String dealTime = DateUtil.getDateTimeStr(taskVo.getSendTime());
                StringBuilder builder = new StringBuilder();
                builder.append(dealTime)
                        .append(",")
                        .append(sendOrgName)
                        .append("发送了一条任务,")
                        .append("标题为").append(taskVo.getTitle());
                String msgContent = builder.toString();
                String entityId = taskVo.getId();

                List<String> orgs = new ArrayList<>(taskVo.getOrgMap().keySet());
                List<UserVo> userList= userClient.findListByOrgIds(orgs).getBody();


                List<Receiver> receivers = new ArrayList<>();
                if(!CollectionUtils.isEmpty(userList)){
                    for(UserVo userVo : userList){
                        Receiver receiver = new Receiver();
                        receiver.setId(userVo.getId());
                        receiver.setName(userVo.getAccount());
                        receivers.add(receiver);
                    }
                }else{
                    return;
                }

                MsgNoticeVo msgNoticeVo = new MsgNoticeVo();
                msgNoticeVo.setTitle(title);
                msgNoticeVo.setSendOrg(sendOrg);
                msgNoticeVo.setOrgName(sendOrgName);
                msgNoticeVo.setMsgContent(msgContent);
                String msgType = MsgSendGlobal.MSG_TYPE_TASK_NEWS_ID;
                MsgConfig msgConfig = MsgSendGlobal.msgTypeMap.get(msgType);
                msgNoticeVo.setMsgType(msgType);
                msgNoticeVo.setTypeName(msgConfig.getModuleName());
                msgNoticeVo.setIcon(msgConfig.getIcon());
                msgNoticeVo.setPath(msgConfig.getPath());
                msgNoticeVo.setEntityId(entityId);
                msgNoticeVo.setReceivers(receivers);
                //发送消息
                msgClient.create(msgNoticeVo);
            }
        });
    }
}
