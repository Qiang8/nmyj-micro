package com.taiji.base.msg.service;

import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.repository.MsgNoticeRecordRepository;
import com.taiji.base.msg.repository.MsgNoticeRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * <p>Title:MsgNoticeRecordService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/31 11:31</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class MsgNoticeRecordService extends BaseService<MsgNoticeRecord,String> {
    MsgNoticeRecordRepository repository;
    MsgNoticeRepository       noticeRepository;

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return MsgNoticeRecord
     */
    public MsgNoticeRecord findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 更新ContactGroup不能为空。
     *
     * @param entity
     * @param id 更新ContactGroup Id
     * @return ContactGroup
     */
    public MsgNoticeRecord update(MsgNoticeRecord entity, String id) {
        Assert.notNull(entity,"entity不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        return repository.save(entity);
    }

    /**
     * 根据参数获取MsgNoticeRecordVo多条记录。
     *
     * @param userId 接收者
     * @param msgType 消息类型
     * @param readFlag 读取标识 0：未读 1：已读
     * @param noticeId 消息编码
     * @return List<MsgNoticeRecord>
     */
    public List<MsgNoticeRecord> findAll(String userId,String msgType,String readFlag,String noticeId){
        return repository.findAll(userId,msgType,readFlag,noticeId);
    }

    public Page<MsgNoticeRecord> findPage(MultiValueMap<String, Object> params, Pageable pageable) {
        return repository.findPage(params,pageable);
    }

//    @Transactional(rollbackFor = Exception.class)
//    public void updateList(List<MsgNoticeRecord>  entities)
//    {
//        repository.saveList(entities);
//    }
}
