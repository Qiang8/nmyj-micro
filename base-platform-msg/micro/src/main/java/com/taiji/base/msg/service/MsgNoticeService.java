package com.taiji.base.msg.service;

import com.taiji.base.msg.entity.MsgNotice;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * <p>Title:MsgNoticeService.java</p >
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
public class MsgNoticeService extends BaseService<MsgNotice,String> {

    MsgNoticeRepository repository;
    MsgNoticeRecordRepository recordRepository;

    public Page<MsgNotice> findPage(MultiValueMap<String, Object> params, Pageable pageable) {
        return repository.findPage(params,pageable);
    }

    /**
     * 新增MsgNotice，MsgNotice不能为空。
     *
     * @param entity
     * @return MsgNotice
     */
    public MsgNotice create(MsgNotice entity) {
        Assert.notNull(entity,"entity不能为null!");

        MsgNotice result = repository.save(entity);

        if (!CollectionUtils.isEmpty(entity.getMsgNoticeRecordList()))
        {
            List<MsgNoticeRecord> msgNoticeRecordList = entity.getMsgNoticeRecordList();
            for (MsgNoticeRecord msgNoticeRecord : msgNoticeRecordList)
            {
                msgNoticeRecord.setMsgNotice(result);
                recordRepository.save(msgNoticeRecord);
            }
        }
        return result;
    }
}
