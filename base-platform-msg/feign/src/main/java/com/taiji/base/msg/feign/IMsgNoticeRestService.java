package com.taiji.base.msg.feign;

import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IMsgNoticeRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 9:38</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-msg-notice")
public interface IMsgNoticeRestService {

    /**
     * 根据参数获取分页MsgNoticeVo多条记录。
     * <p>
     * params参数key
     *    type 查找类型 0：用户id,1：机构id
     *    orgId or account
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<MsgNoticeVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<MsgNoticeVo>> findPage(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 新增MsgNoticeVo，MsgNoticeVo不能为空。
     *
     * @param vo 消息vo
     * @return ResponseEntity<MsgNoticeVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<MsgNoticeVo> create(@RequestBody MsgNoticeVo vo);

    /**
     * 根据消息id获取记录list，
     * @param noticeId
     * @param readFlag  读取标识，0：未读，1：已读
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/records/{id}")
    @ResponseBody
    ResponseEntity<List<Receiver>> findRecords(@PathVariable("id") String noticeId,
                                               @RequestParam(name = "readFlag") String readFlag);
}
