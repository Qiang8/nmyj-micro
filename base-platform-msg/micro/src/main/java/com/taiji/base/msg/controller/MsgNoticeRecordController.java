package com.taiji.base.msg.controller;

import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.enums.ReadFlagEnum;
import com.taiji.base.msg.feign.IMsgNoticeRecordRestService;
import com.taiji.base.msg.mapper.MsgNoticeRecordMapper;
import com.taiji.base.msg.service.MsgNoticeRecordService;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.service.UtilsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:MsgNoticeRecordController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 18:42</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/msg/noticeRecord")
@AllArgsConstructor
public class MsgNoticeRecordController extends BaseController implements IMsgNoticeRecordRestService {
    MsgNoticeRecordService service;
    MsgNoticeRecordMapper  mapper;

    UtilsService  utilsService;

    /**
     * 根据MsgNoticeRecordVoid获取一条记录。
     *
     * @param id 消息记录id
     * @return ResponseEntity<MsgNoticeRecordVo>
     */
    @Override
    public ResponseEntity<MsgNoticeRecordVo> find(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id) {
        MsgNoticeRecord entity = service.findOne(id);
        MsgNoticeRecordVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据参数获取MsgNoticeRecordVo多条记录。
     *
     * @param params 查询参数集合
     *        msgType 消息类型
     *        readFlag 读取标识  0：未读，1：已读
     *        userId
     * @return ResponseEntity<List < MsgNoticeRecordVo>>
     */
    @Override
    public ResponseEntity<List<MsgNoticeRecordVo>> findList(@RequestParam
                                                            MultiValueMap<String, Object> params) {
        String userId = "";
        String msgType = "";
        String readFlag = "";

        if (params.containsKey("userId")) {
            userId = params.getFirst("userId").toString();
        }
        if (params.containsKey("msgType")) {
            msgType = params.getFirst("msgType").toString();
        }

        if (params.containsKey("readFlag")) {
            readFlag = params.getFirst("readFlag").toString();
        }

        List<MsgNoticeRecord>   list   = service.findAll(userId,msgType,readFlag,"");
        List<MsgNoticeRecordVo> voList = mapper.entityListToVoList(list);

        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取分页MsgNoticeRecordVo多条记录。
     *
     * @param params 查询参数集合
     *        msgType 消息类型
     *        readFlag 读取标识  0：未读，1：已读
     *        userId
     * @return ResponseEntity<RestPageImpl < MsgNoticeRecordVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<MsgNoticeRecordVo>> findPage(@RequestParam
                                                                    MultiValueMap<String, Object> params) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageUtils.getPageable(params,sort);

        Page<MsgNoticeRecord> result = service.findPage(params, pageable);
        RestPageImpl<MsgNoticeRecordVo> voPage = mapper.entityPageToVoPage(result, pageable);

        return ResponseEntity.ok(voPage);
    }

    /**
     * 更新消息记录。
     *
     * @param records 主键编码
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> updateRecords(@RequestBody List<String> records) {

        for(String id: records){
            MsgNoticeRecord record = service.findOne(id);
            record.setReadFlag(ReadFlagEnum.READ.getCode());
            record.setReadTime(utilsService.now());

            service.update(record,id);
        }

        return ResponseEntity.ok().build();
    }
}
