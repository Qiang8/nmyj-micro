package com.taiji.base.msg.service;

import com.taiji.base.msg.feign.MsgNoticeRecordClient;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
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
 * <p>Title:MsgNoticeRecordService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 15:14</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@Service
public class MsgNoticeRecordService extends BaseService {

    MsgNoticeRecordClient client;

    /**
     * 根据id获取消息记录信息
     * @param id
     * @return
     */
    public MsgNoticeRecordVo findById(String id){
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<MsgNoticeRecordVo> result = client.find(id);
        MsgNoticeRecordVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 获取个人消息记录列表-分页
     * @param params
     *        msgType 消息类型
     *        readFlag 读取标识  0：未读，1：已读
     *        userId
     *       page+size 分页信息
     * @return
     */
    public RestPageImpl<MsgNoticeRecordVo> findRecords(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<RestPageImpl<MsgNoticeRecordVo>> result = client.findPage(
                super.convertMap2MultiValueMap(params));
        RestPageImpl<MsgNoticeRecordVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 获取个人消息记录列表-不分页
     * @param params
     *        msgType 消息类型
     *        readFlag 读取标识  0：未读，1：已读
     *        userId
     * @return
     */
    public List<MsgNoticeRecordVo> findNoticeList(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<MsgNoticeRecordVo>> result = client.findList(
                super.convertMap2MultiValueMap(params));
        List<MsgNoticeRecordVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 更新读取标识
     * @param records
     */
    public void updateRecords(List<String> records){

        ResponseEntity<Void> result = client.updateRecords(records);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }
}
