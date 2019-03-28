package com.taiji.base.msg.service;

import com.taiji.base.msg.feign.MsgNoticeClient;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * <p>Title:MsgNoticeService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 15:02</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@Service
public class MsgNoticeService extends BaseService {

    MsgNoticeClient client;

    public void create(MsgNoticeVo noticeVo){
        Assert.notNull(noticeVo, "noticeVo不能为null值。");

        ResponseEntity<MsgNoticeVo> result = client.create(noticeVo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 查询列表
     * @param noticeId
     * @param readFlag
     * @return
     */
    public List<Receiver> findNoticeRecords(String noticeId,String readFlag) {

        Assert.hasText(noticeId,"noticeId不能为空");

        ResponseEntity<List<Receiver>> result = client.findRecords(noticeId,readFlag);
        List<Receiver> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 查询分页列表
     * @param params
     *    type 查找类型 0：用户id,1：机构id
     *    orgId or account
     * @return
     */
    public RestPageImpl<MsgNoticeVo> findNotices(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<RestPageImpl<MsgNoticeVo>> result = client
                .findPage(super.convertMap2MultiValueMap(params));
        RestPageImpl<MsgNoticeVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }
}
