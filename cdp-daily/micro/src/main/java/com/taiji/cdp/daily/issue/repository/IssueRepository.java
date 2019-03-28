package com.taiji.cdp.daily.issue.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.daily.issue.entity.Issue;
import com.taiji.cdp.daily.issue.entity.QIssue;
import com.taiji.cdp.daily.searchVo.IssuePageVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 16:03
 **/
@Repository
public class IssueRepository extends BaseJpaRepository<Issue,String> {
    /**
     * 根据专刊名称检索专刊信息
     * @param pageVo
     * @param pageable
     * @return
     */
    public Page<Issue> findIssues(IssuePageVo pageVo, Pageable pageable) {
        QIssue sample = QIssue.issue;
        BooleanBuilder builder = new BooleanBuilder();
        JPQLQuery<Issue> query = from(sample);
        String title = pageVo.getTitle();
        if(StringUtils.hasText(title)) {
            builder.and(sample.title.contains(title));
        }
        List<String> ids = pageVo.getIds();
        if(!CollectionUtils.isEmpty(ids)) {
            builder.and(sample.id.in(ids));
        }
        query.select(Projections.bean(Issue.class,sample.id, sample.title,sample.createTime,sample.status,sample.createBy)).where(builder).orderBy(sample.createTime.desc());

        return findAll(query,pageable);
    }
}