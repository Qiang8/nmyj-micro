package com.taiji.cdp.cmd.service;

import com.taiji.cdp.cmd.feign.TreatClient;
import com.taiji.cdp.cmd.vo.TreatVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: nmyj-micro
 * @description: 舆情操作日志记录
 * @author: TaiJi.WangJian
 * @create: 2019-03-04 14:43
 **/
@Service
@AllArgsConstructor
public class TreatService extends BaseService {
    TreatClient treatClient;

    /**
     * 记录调控单办理情况
     * @param treatVo 调控单办理情况Vo
     */
    public void addTreatment(TreatVo treatVo, OAuth2Authentication principal) {
        LinkedHashMap<String,Object> userMap = getUserMap(principal);
        treatVo.setCreateBy(userMap.get("username").toString());
        treatVo.setCreateTime(getCurrrentTime());
        ResponseEntity<Void> result = treatClient.addTreatment(treatVo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }
    /**
     * 更新调控单办理情况
     * @param treatVo 调控单办理情况Vo
     * @param id 办理情况Id
     */
    public void updateTreatment(TreatVo treatVo, String id,OAuth2Authentication principal) {
        LinkedHashMap<String,Object> userMap = getUserMap(principal);
        treatVo.setUpdateBy(userMap.get("username").toString());
        treatVo.setUpdateTime(getCurrrentTime());
        ResponseEntity<Void> result = treatClient.updateTreatment(treatVo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 根据Id获取单条调控单办理信息
     * @param id 操作Id
     * @return 操作Vo
     */
    public TreatVo findOneTreatment(String id) {
        ResponseEntity<TreatVo> result = treatClient.findOneTreatment(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 根据Id删除单条调控单办理信息
     * @param id 操作Id
     */
    public void deleteOneTreatment(String id) {
        ResponseEntity<Void> result = treatClient.deleteOneTreatment(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 查询办理情况列表-不分页
     * @return 办理情况结果集
     */
    public List<TreatVo> searchAllTreatment() {
        ResponseEntity<List<TreatVo>> result = treatClient.searchAllTreatment();
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }
}
