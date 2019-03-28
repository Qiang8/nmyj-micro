package com.taiji.base.msg.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.base.msg.entity.MsgType;
import com.taiji.base.msg.entity.QMsgType;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>Title:MsgTypeRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/29 17:12</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@Transactional(
        readOnly = true
)
public class MsgTypeRepository extends BaseJpaRepository<MsgType,String> {

    public MsgType findOneByCode(String code) {
        QMsgType qMsgType = QMsgType.msgType;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMsgType.code.eq(code));

        return findOne(builder);
    }

    public List<MsgType> findAll(String moduleName,String type){
        QMsgType qMsgType = QMsgType.msgType;

        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(moduleName))
        {
            builder.and(qMsgType.moduleName.eq(moduleName));
        }

        if(StringUtils.hasText(type))
        {
            builder.and(qMsgType.type.eq(type));
        }

        return findAll(builder);
    }
}
