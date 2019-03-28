package com.taiji.base.msg.controller;

import com.taiji.base.msg.entity.MsgNotice;
import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.enums.ReadFlagEnum;
import com.taiji.base.msg.feign.IMsgNoticeRestService;
import com.taiji.base.msg.mapper.MsgNoticeMapper;
import com.taiji.base.msg.mapper.MsgNoticeRecordMapper;
import com.taiji.base.msg.queue.MsgSendService;
import com.taiji.base.msg.service.MsgNoticeRecordService;
import com.taiji.base.msg.service.MsgNoticeService;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:MsgNoticeController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 18:26</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/msg/notice")
@AllArgsConstructor
public class MsgNoticeController extends BaseController implements IMsgNoticeRestService {

    MsgNoticeService service;
    MsgNoticeRecordService recordService;
    MsgNoticeMapper mapper;
    MsgNoticeRecordMapper recordMapper;

    MsgSendService msgSendService;

    /**
     * 根据参数获取分页MsgNoticeVo多条记录。
     * <p>
     * params参数key
     *    type 查找类型 0：用户id,1：机构id
     *    orgId or account
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl < MsgNoticeVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<MsgNoticeVo>> findPage(@RequestParam
                                                             MultiValueMap<String, Object> params) {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageUtils.getPageable(params,sort);

        Page<MsgNotice> result = service.findPage(params, pageable);
        RestPageImpl<MsgNoticeVo> voPage = mapper.entityPageToVoPage(result, pageable);

        return ResponseEntity.ok(voPage);
    }

    /**
     * 根据消息id获取记录list，
     *
     * @param noticeId
     * @param readFlag 读取标识，0：未读，1：已读
     * @return
     */
    @Override
    public ResponseEntity<List<Receiver>> findRecords(@NotEmpty(message = "id不能为空")
                                                      @PathVariable(value = "id")
                                                          String noticeId,
                                                      @RequestParam(name = "readFlag",required = false)
                                                      @NotEmpty(message = "readFlag不能为空")
                                                          String readFlag) {

        List<MsgNoticeRecord>   result = recordService.findAll("","",readFlag,noticeId);
        List<Receiver> voList = recordMapper.entityListToReceiverList(result);
        return ResponseEntity.ok(voList);
    }

    /**
     * 新增MsgNoticeVo，MsgNoticeVo不能为空。
     *
     * @param vo 消息vo
     *
     * @return ResponseEntity<MsgNoticeVo>
     */
    @Override
    public ResponseEntity<MsgNoticeVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody MsgNoticeVo vo) {
        List<Receiver> receivers = vo.getReceivers();

        MsgNotice   tempEntity = mapper.voToEntity(vo);

        if(!CollectionUtils.isEmpty(receivers))
        {
            List<MsgNoticeRecord> msgNoticeRecordList = new ArrayList<>();
            for (Receiver receiver: receivers) {
                MsgNoticeRecord msgNoticeRecord = new MsgNoticeRecord();
                msgNoticeRecord.setReceiverId(receiver.getId());
                msgNoticeRecord.setReceiverName(receiver.getName());
                msgNoticeRecord.setDeptId(receiver.getDeptId());
                msgNoticeRecord.setDeptName(receiver.getDeptName());
                msgNoticeRecord.setReadFlag(ReadFlagEnum.UNREAD.getCode());

                msgNoticeRecordList.add(msgNoticeRecord);
            }
            tempEntity.setMsgNoticeRecordList(msgNoticeRecordList);
        }

        tempEntity.setDelFlag(DelFlagEnum.NORMAL.getCode());

        MsgNotice   entity     = service.create(tempEntity);
        MsgNoticeVo tempVo     = mapper.entityToVo(entity);

        msgSendService.sendMsgNotice(entity);

        return ResponseEntity.ok(tempVo);
    }
}
