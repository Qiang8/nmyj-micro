package com.taiji.base.sys.controller;

import com.taiji.base.sys.service.BlogService;
import com.taiji.base.sys.vo.BlogVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * <p>Title:BlogController.java</p >
 * <p>Description: 业务日志控制类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/30 5:29</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/blogs")
public class BlogController extends BaseController{

    BlogService blogService;

    /**
     * 获取单个业务日志信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findBlogById(@NotEmpty(message = "id不能为空")
                                     @PathVariable(name = "id") String id){
        BlogVo blogVo = blogService.findById(id);

        return ResultUtils.success(blogVo);
    }

    /**
     * 获取业务日志列表——分页
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findBlogs(@RequestBody Map<String, Object> paramsMap){

        if(paramsMap.containsKey("page") && paramsMap.containsKey("size")){
            RestPageImpl<BlogVo> pageList = blogService.findBlogs(paramsMap);

            return ResultUtils.success(pageList);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

}
