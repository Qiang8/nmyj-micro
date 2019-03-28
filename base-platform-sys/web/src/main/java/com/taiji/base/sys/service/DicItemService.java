package com.taiji.base.sys.service;

import com.taiji.base.sys.feign.DicItemClient;
import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <p>Title:DicItemService.java</p >
 * <p>Description: 数据字典服务类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class DicItemService extends BaseService{

    DicItemClient client;

    /**
     * 根据id获取数据字典信息
     * @param id
     * @return
     */
    public DicGroupItemVo findById(String id){
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<DicGroupItemVo> result = client.find(id);
        DicGroupItemVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 新增数据字典信息
     * @param vo
     * @return
     */
    public void create(DicGroupItemVo vo){
        Assert.notNull(vo, "DicGroupItemVo不能为null值。");

        ResponseEntity<DicGroupItemVo> result = client.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 更新数据字典信息
     * @param vo
     * @param id
     */
    public void update(DicGroupItemVo vo, String id){
        Assert.notNull(vo,"vo不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<DicGroupItemVo> result = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 逻辑删除数据字典
     * @param id
     */
    public void delete(String id){
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<Void> result =  client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 查询数据字典列表
     * @param params
     * @return
     */
    public List<DicGroupItemVo> findItemsAll(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<DicGroupItemVo>> result = client.findList(super.convertMap2MultiValueMap(params));
        List<DicGroupItemVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

}
