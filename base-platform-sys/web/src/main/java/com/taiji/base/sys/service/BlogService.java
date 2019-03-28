package com.taiji.base.sys.service;

import com.taiji.base.sys.feign.BlogClient;
import com.taiji.base.sys.vo.BlogVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * <p>
 * <p>Title:BlogService.java</p >
 * <p>Description: 业务日志信息服务类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/30 5:28</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class BlogService extends BaseService {

    BlogClient client;

    /**
     * 获取单个业务日志信息
     * @param id
     * @return
     */
    public BlogVo findById(String id){
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<BlogVo> result = client.find(id);
        BlogVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 查询分页列表
     * @param params
     * @return
     */
    public RestPageImpl<BlogVo> findBlogs(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<RestPageImpl<BlogVo>> result = client.findPage(super.convertMap2MultiValueMap(params));
        RestPageImpl<BlogVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }
}
