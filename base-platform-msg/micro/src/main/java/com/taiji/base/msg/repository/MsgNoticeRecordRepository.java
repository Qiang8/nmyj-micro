package com.taiji.base.msg.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.entity.QMsgNoticeRecord;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:MsgNoticeRecordRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/29 17:14</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@Transactional(
        readOnly = true
)
public class MsgNoticeRecordRepository extends BaseJpaRepository<MsgNoticeRecord,String> {


    public List<MsgNoticeRecord> findAll(String userId,String msgType,String readFlag,String noticeId){
        QMsgNoticeRecord qMsgNoticeRecord = QMsgNoticeRecord.msgNoticeRecord;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(userId)){
            builder.and(qMsgNoticeRecord.receiverId.eq(userId));
        }

        if(StringUtils.hasText(msgType)){
            builder.and(qMsgNoticeRecord.msgNotice.msgType.id.eq(msgType));
        }
        if(StringUtils.hasText(noticeId)){
            builder.and(qMsgNoticeRecord.msgNotice.id.eq(noticeId));
        }

        if(StringUtils.hasText(readFlag)){
            builder.and(qMsgNoticeRecord.readFlag.eq(readFlag));
        }

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");

        return findAll(builder,sort);
    }

    public Page<MsgNoticeRecord> findPage(MultiValueMap<String, Object> params, Pageable pageable){
        QMsgNoticeRecord qMsgNoticeRecord = QMsgNoticeRecord.msgNoticeRecord;

        String userId = "";
        String msgType = "";
        String readFlag = "";
        String noticeId = "";

        if(params.containsKey("userId"))
        {
            userId = params.getFirst("userId").toString();
        }

        if(params.containsKey("msgType"))
        {
            msgType = params.getFirst("msgType").toString();
        }

        if(params.containsKey("readFlag"))
        {
            readFlag = params.getFirst("readFlag").toString();
        }

        if(params.containsKey("noticeId"))
        {
            noticeId = params.getFirst("noticeId").toString();
        }

        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(userId)){
            builder.and(qMsgNoticeRecord.receiverId.eq(userId));
        }

        if(StringUtils.hasText(msgType)){
            builder.and(qMsgNoticeRecord.msgNotice.msgType.id.eq(msgType));
        }

        if(StringUtils.hasText(noticeId)){
            builder.and(qMsgNoticeRecord.msgNotice.id.eq(noticeId));
        }

        if(StringUtils.hasText(readFlag)){
            builder.and(qMsgNoticeRecord.readFlag.eq(readFlag));
        }

        return findAll(builder,pageable);
    }

    @Override
    @Transactional
    public MsgNoticeRecord save(MsgNoticeRecord entity)
    {
        Assert.notNull(entity, "MsgNoticeRecord must not be null!");

        MsgNoticeRecord result;
        if(!StringUtils.hasText(entity.getId()))
        {
            result = super.save(entity);
        }
        else
        {
            MsgNoticeRecord tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }
}

