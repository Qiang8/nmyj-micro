package com.taiji.base.msg.service;

import com.taiji.base.msg.feign.MsgTypeClient;
import com.taiji.base.msg.vo.MsgTypeVo;
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
 * <p>Title:MsgTypeService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 15:15</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@Service
public class MsgTypeService extends BaseService {

    MsgTypeClient client;

    /**
     * 根据code获取消息类型
     * @param code
     * @return
     */
    public MsgTypeVo findOneByCode(String code){
        Assert.hasText(code,"code不能为空");

        ResponseEntity<MsgTypeVo> result = client.findOneByCode(code);
        MsgTypeVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 获取消息类型列表
     * @param params
     * @return
     */
    public List<MsgTypeVo> findTypeList(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<MsgTypeVo>> result = client.findList(super.convertMap2MultiValueMap(params));
        List<MsgTypeVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }
}
