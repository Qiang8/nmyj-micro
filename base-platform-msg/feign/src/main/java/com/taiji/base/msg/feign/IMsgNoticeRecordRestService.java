package com.taiji.base.msg.feign;

import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IMsgNoticeRecordRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 9:39</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-msg-notice-record")
public interface IMsgNoticeRecordRestService {

    /**
     * 根据MsgNoticeRecordVo id获取一条记录。
     *
     * @param id 消息记录id
     * @return ResponseEntity<MsgNoticeRecordVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<MsgNoticeRecordVo> find(@PathVariable("id") String id);

    /**
     * 根据参数获取MsgNoticeRecordVo多条记录。
     *
     * @param params 查询参数集合
     *        msgType 消息类型
     *        readFlag 读取标识  0：未读，1：已读
     *        userId
     *
     * @return ResponseEntity<List<MsgNoticeRecordVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<MsgNoticeRecordVo>> findList(
            @RequestParam MultiValueMap<String, Object> params);

    /**
     * 根据参数获取分页MsgNoticeRecordVo多条记录。
     *
     * @param params 查询参数集合
     *        msgType 消息类型
     *        readFlag 读取标识  0：未读，1：已读
     *        userId
     *
     * @return ResponseEntity<RestPageImpl<MsgNoticeRecordVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<MsgNoticeRecordVo>> findPage(
            @RequestParam MultiValueMap<String, Object> params);

    /**
     * 更新多条消息记录的readFlag为已读
     *
     * @param records 主键编码
     *
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/update/records")
    @ResponseBody
    ResponseEntity<Void> updateRecords(@RequestBody List<String> records);
}
