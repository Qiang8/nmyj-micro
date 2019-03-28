package com.taiji.base.sys.feign;

import com.taiji.base.sys.vo.BlogVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Title:IBlogRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 10:41</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-blog")
public interface IBlogRestService {
    /**
     * 根据BlogVo id获取一条记录。
     *
     * @param id    业务日志id
     * @return ResponseEntity<BlogVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<BlogVo> find(@PathVariable("id") String id);


    /**
     * 根据参数获取分页UserVo多条记录。
     *
     * params参数key为createTimeStart（可选），createTimeEnd（可选）。
     *
     * @param params    查询参数集合
     * @return ResponseEntity<RestPageImpl<BlogVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<BlogVo>> findPage(@RequestParam MultiValueMap<String, Object> params);


}
