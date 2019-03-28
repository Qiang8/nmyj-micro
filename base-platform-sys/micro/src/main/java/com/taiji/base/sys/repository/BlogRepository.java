package com.taiji.base.sys.repository;

import com.querydsl.core.BooleanBuilder;
import com.taiji.base.sys.entity.Blog;
import com.taiji.base.sys.entity.QBlog;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>Title:BlogRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 19:41</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
public class BlogRepository extends BaseJpaRepository<Blog,String> {
    public Page<Blog> findPage(LocalDateTime createTimeStart, LocalDateTime createTimeEnd, Pageable pageable){
        QBlog qBlog = QBlog.blog;

        BooleanBuilder builder = new BooleanBuilder();
        if(null != createTimeStart)
        {
            builder.and(qBlog.createTime.after(createTimeStart));
        }

        if(null != createTimeEnd)
        {
            builder.and(qBlog.createTime.before(createTimeEnd));
        }

        return findAll(builder,pageable);
    }
}
