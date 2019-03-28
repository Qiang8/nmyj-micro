package com.taiji.base.msg.queue;

import com.taiji.base.msg.entity.MsgNotice;
import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.mapper.CycleAvoidingMappingContext;
import com.taiji.base.msg.mapper.MsgNoticeRecordMapper;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import com.taiji.base.msg.vo.MsgNoticeVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * <p>Title:MsgSendService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/8 16:43</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class MsgSendService {
    private RabbitTemplate rabbitTemplate;

    private MsgNoticeRecordMapper msgNoticeRecordMapper;

    public void sendMsgNotice(MsgNotice msgNotice)
    {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            if (null != msgNotice)
            {
                List<MsgNoticeRecord> list = msgNotice.getMsgNoticeRecordList();
                for (MsgNoticeRecord record : list)
                {
                    MsgNoticeRecordVo vo = msgNoticeRecordMapper.entityToVo(record);
                    this.rabbitTemplate.convertAndSend("msgFanoutExchange", null, vo);
                }
            }
        });
    }
}
