package com.taiji.base.msg.mapper;

import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:MsgNoticeRecordMapper.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 18:46</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface MsgNoticeRecordMapper{

    @Mappings({
                      @Mapping(source = "msgNotice.title", target = "title"),
                      @Mapping(source = "msgNotice.sendOrg", target = "sendOrg"),
                      @Mapping(source = "msgNotice.orgName", target = "orgName"),
                      @Mapping(source = "msgNotice.msgContent", target = "msgContent"),
                      @Mapping(source = "msgNotice.entityId", target = "entityId"),
                      @Mapping(source = "msgNotice.msgType.id", target = "msgType"),
                      @Mapping(source = "msgNotice.msgType.moduleName",target = "typeName"),
                      @Mapping(source = "msgNotice.msgType.icon",target = "icon"),
                      @Mapping(source = "msgNotice.msgType.path",target = "path"),
                      @Mapping(source = "msgNotice.extra",target = "extra"),
                      @Mapping(source = "msgNotice.createBy",target = "createBy")
              })
    @InheritInverseConfiguration
    MsgNoticeRecordVo entityToVo(MsgNoticeRecord entity);

    MsgNoticeRecord voToEntity(MsgNoticeRecordVo vo);


    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "receiverName", target = "name"),
            @Mapping(source = "readFlag", target = "readFlag"),
            @Mapping(source = "readTime", target = "readTime"),
            @Mapping(source = "deptId", target = "deptId"),
            @Mapping(source = "deptName", target = "deptName")
    })
    @InheritInverseConfiguration
    Receiver entityToReceiver(MsgNoticeRecord entity);

    default List<Receiver> entityListToReceiverList(List<MsgNoticeRecord> entityList)
    {
        if ( entityList == null) {
            return null;
        }

        List<Receiver> list = new ArrayList<>(entityList.size());

        for ( MsgNoticeRecord entity : entityList ) {
            list.add( entityToReceiver(entity) );
        }

        return list;
    }

    default List<MsgNoticeRecordVo> entityListToVoList(List<MsgNoticeRecord> entityList)
    {
        if ( entityList == null) {
            return null;
        }

        List<MsgNoticeRecordVo> list = new ArrayList<>(entityList.size());

        for ( MsgNoticeRecord entity : entityList ) {
            list.add( entityToVo(entity) );
        }

        return list;
    }

    default List<MsgNoticeRecord> voListToEntityList(List<MsgNoticeRecordVo> voList)
    {
        if ( voList == null) {
            return null;
        }

        List<MsgNoticeRecord> list = new ArrayList<>(voList.size());

        for ( MsgNoticeRecordVo vo : voList ) {
            list.add( voToEntity(vo) );
        }

        return list;
    }

    default RestPageImpl<MsgNoticeRecordVo> entityPageToVoPage(Page<MsgNoticeRecord> entityPage, Pageable page)
    {
        if ( entityPage == null || page == null) {
            return null;
        }

        List<MsgNoticeRecord> content = entityPage.getContent();

        List<MsgNoticeRecordVo> list = new ArrayList<MsgNoticeRecordVo>(content.size());

        for ( MsgNoticeRecord entity : content ) {
            list.add( entityToVo(entity) );
        }

        RestPageImpl<MsgNoticeRecordVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;
    }
}
