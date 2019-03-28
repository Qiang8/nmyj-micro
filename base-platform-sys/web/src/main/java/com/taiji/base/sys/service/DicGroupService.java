package com.taiji.base.sys.service;

import com.taiji.base.sys.feign.DicGroupClient;
import com.taiji.base.sys.vo.DicGroupVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * <p>Title:DicService.java</p >
 * <p>Description: 字典信息服务类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class DicGroupService extends BaseService{

    DicGroupClient client;

    /**
     * 根据id获取字典信息
     * @param id
     * @return
     */
    public DicGroupVo findById(String id){
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<DicGroupVo> result = client.find(id);
        DicGroupVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 根据dicCode获取字典信息
     * @param dicCode
     * @return
     */
    public DicGroupVo findByDicCode(String dicCode){
        Assert.hasText(dicCode, "dicCode不能为null值或空字符串。");
        MultiValueMap<String,Object> valueMap = new LinkedMultiValueMap<>();
        valueMap.set("dicCode",dicCode);

        ResponseEntity<DicGroupVo> result = client.findOne(valueMap);
        DicGroupVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 新增字典信息
     * @param vo
     * @return
     */
    public void create(DicGroupVo vo){
        Assert.notNull(vo, "DicGroupVo不能为null值。");

        ResponseEntity<DicGroupVo> result = client.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 更新字典信息
     * @param vo
     * @param id
     */
    public void update(DicGroupVo vo, String id){
        Assert.notNull(vo,"vo不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<DicGroupVo> result = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 逻辑删除字典
     * @param id
     */
    public void delete(String id){
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<Void> result =  client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 查询字典分页列表
     * @param params
     * @return
     */
    public RestPageImpl<DicGroupVo> findGroups(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<RestPageImpl<DicGroupVo>> result = client.findPage(super.convertMap2MultiValueMap(params));
        RestPageImpl<DicGroupVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 查询字典列表
     * @param params
     * @return
     */
    public List<DicGroupVo> findGroupsAll(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<DicGroupVo>> result = client.findList(super.convertMap2MultiValueMap(params));
        List<DicGroupVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 检查字典编码的重复性
     * @param dicCode
     * @return
     */
    public Boolean checkDicCode(String dicCode){
        Assert.hasText(dicCode, "dicCode不能为null或空字符串!");

        ResponseEntity<Boolean> result= client.checkUnique("",dicCode);
        Boolean body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return  body;
    }
}
