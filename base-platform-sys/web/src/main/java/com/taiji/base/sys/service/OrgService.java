package com.taiji.base.sys.service;

import com.taiji.base.sys.feign.OrgClient;
import com.taiji.base.sys.vo.OrgVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <p>Title:OrgService.java</p >
 * <p>Description: 机构信息服务类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class OrgService extends BaseService{

    OrgClient client;

    /**
     * 根据id获取机构信息
     * @param id
     * @return
     */
    public OrgVo findById(String id){
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<OrgVo> result = client.find(id);
        OrgVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 新增机构信息
     * @param vo
     * @return
     */
    public void create(OrgVo vo){
        Assert.notNull(vo, "MenuVo不能为null值。");

        ResponseEntity<OrgVo> result =  client.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 更新机构信息
     * @param vo
     * @param id
     */
    public void update(OrgVo vo, String id){
        Assert.notNull(vo,"vo不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<OrgVo> result =  client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 逻辑删除机构
     * @param id
     */
    public void delete(String id){
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<Void> result =client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }


    /**
     * 查询列表—不分页
     * @param params
     * @return
     */
    public List<OrgVo> findOrgAll(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<OrgVo>> result = client.findList(super.convertMap2MultiValueMap(params));
        List<OrgVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }
}
