package com.taiji.base.sys.service;

import com.taiji.base.sys.entity.Blog;
import com.taiji.base.sys.repository.BlogRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * <p>Title:BlogService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 19:48</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class BlogService extends BaseService<Blog,String> {

    BlogRepository repository;

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return Blog
     */
    public Blog findOne(String id) {
        Assert.hasText(id, "id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 根据参数获取分页Blog多条记录。
     * <p>
     *
     * @param createTimeStart （可选）
     * @param createTimeEnd    （可选）
     * @return RestPageImpl<Blog>
     */
    public Page<Blog> findPage(LocalDateTime createTimeStart, LocalDateTime createTimeEnd, Pageable pageable) {
        return repository.findPage(createTimeStart,createTimeEnd,pageable);
    }
}
