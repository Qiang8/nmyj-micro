package com.taiji.base.sys.controller;

import com.taiji.base.sys.entity.Blog;
import com.taiji.base.sys.feign.IBlogRestService;
import com.taiji.base.sys.mapper.BlogMapper;
import com.taiji.base.sys.service.BlogService;
import com.taiji.base.sys.vo.BlogVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>Title:BlogController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 19:49</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/blog")
@AllArgsConstructor
public class BlogController extends BaseController implements IBlogRestService {

    BlogService service;

    BlogMapper mapper;


    /**
     * 根据BlogVo id获取一条记录。
     *
     * @param id
     * @return ResponseEntity<BlogVo>
     */
    @Override
    public ResponseEntity<BlogVo> find(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id) {
        Blog   entity = service.findOne(id);
        BlogVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据参数获取分页UserVo多条记录。
     * <p>
     * params参数key为createTimeStart（可选），createTimeEnd（可选）。
     *
     * @param params
     * @return ResponseEntity<RestPageImpl   <   BlogVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<BlogVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);

        String strCreateTimeStart;
        String strCreateTimeEnd;

        LocalDateTime createTimeStart;
        LocalDateTime createTimeEnd;

        if (params.containsKey("createTimeStart")) {
            strCreateTimeStart = params.getFirst("createTimeStart").toString();

            createTimeStart = DateUtil.strToLocalDateTime(strCreateTimeStart);
        } else {
            createTimeStart = null;
        }

        if (params.containsKey("createTimeEnd")) {
            strCreateTimeEnd = params.getFirst("createTimeEnd").toString();

            createTimeEnd = DateUtil.strToLocalDateTime(strCreateTimeEnd);
        } else {
            createTimeEnd = null;
        }

        Page<Blog>           result = service.findPage(createTimeStart, createTimeEnd, pageable);
        RestPageImpl<BlogVo> voPage = mapper.entityPageToVoPage(result, pageable);
        return ResponseEntity.ok(voPage);
    }
}
