package com.taiji.cdp.daily.duty.service;

import com.taiji.cdp.daily.duty.feign.DutyShiftClient;
import com.taiji.cdp.daily.duty.vo.DutyAddVo;
import com.taiji.cdp.daily.issue.service.BaseService;
import com.taiji.cdp.daily.vo.DutyShiftItemVo;
import com.taiji.cdp.daily.vo.DutyShiftVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:DutyShiftService.java</p >
 * <p>Description: 交接班对外service层 service</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/21 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class DutyShiftService extends BaseService {

    DutyShiftClient client;

    /**
     * 新增交接班信息
     *
     * @param dutyAddVo
     * @return
     */
    public void addDuty(DutyAddVo dutyAddVo, OAuth2Authentication principal) throws Exception {
        DutyShiftVo vo = dutyAddVo.getDutyShift();
        Assert.notNull(vo, "交接班对象不能为null值。");
        LinkedHashMap<String, Object> userMap = getUserMap(principal);
        vo.setCreateBy(userMap.get("username").toString());//取证人姓名
        vo.setCreateOrgId(userMap.get("orgId").toString());
        vo.setCreateTime(LocalDateTime.now());
        ResponseEntity<DutyShiftVo> result = client.createDuty(vo);
        String id = result.getBody().getId();
        if (StringUtils.isNotBlank(id)) {
            List<String> infoIds = dutyAddVo.getInfoIds();
            for (int i = 0; i < infoIds.size(); i++) {
                DutyShiftItemVo itemVo = new DutyShiftItemVo();
                itemVo.setDutyId(id);
                itemVo.setInfoId(infoIds.get(i));
                client.createDutyItem(itemVo);
            }
        }
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }


    /**
     * 根据id获取一条记录
     *
     * @param id
     * @return DutyAddVo
     */
    public DutyAddVo findById(String id) throws Exception {
        Assert.hasText(id, "id不能为null值或空字符串。");
        DutyAddVo dutyAddVo = new DutyAddVo();
        ResponseEntity<DutyShiftVo> result = client.findDuty(id);
        String dutyId = result.getBody().getId();
        ResponseEntity<List<DutyShiftItemVo>> entitys =  client.findDutyItem(dutyId);
        List<DutyShiftItemVo> itemVos = entitys.getBody();
        List<String> infoIds = new ArrayList<>();
        if (itemVos != null && itemVos.size() > 0) {
            for (DutyShiftItemVo itemVo : itemVos) {
                infoIds.add(itemVo.getInfoId());
            }
        }
        dutyAddVo.setDutyShift(result.getBody());
        dutyAddVo.setInfoIds(infoIds);
        return dutyAddVo;
    }

    /**
     * 查询分页列表
     *
     * @param params
     * @return
     */
    public RestPageImpl<DutyShiftVo> findByPages(Map<String, Object> params) throws Exception {
        Assert.notNull(params, "params不能为null值");
        ResponseEntity<RestPageImpl<DutyShiftVo>> result = client.findPage(super.convertMap2MultiValueMap(params));
        RestPageImpl<DutyShiftVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);
        return body;
    }

    /**
     * 获取最新的交接班信息
     *
     * @return
     */
    public DutyAddVo findNewDuty() throws Exception {
        DutyAddVo dutyAddVo = new DutyAddVo();
        ResponseEntity<DutyShiftVo> result = client.findNewDuty();
        String dutyId = result.getBody().getId();
        List<DutyShiftItemVo> itemVos = client.findDutyItem(dutyId).getBody();
        List<String> infoIds = new ArrayList<>();
        if (itemVos != null && itemVos.size() > 0) {
            for (DutyShiftItemVo itemVo : itemVos) {
                infoIds.add(itemVo.getInfoId());
            }
        }
        dutyAddVo.setDutyShift(result.getBody());
        dutyAddVo.setInfoIds(infoIds);
        return dutyAddVo;
    }


}