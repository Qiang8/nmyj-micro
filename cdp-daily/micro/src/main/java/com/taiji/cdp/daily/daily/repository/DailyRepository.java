package com.taiji.cdp.daily.daily.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.daily.daily.entity.Daily;
import com.taiji.cdp.daily.daily.entity.QDaily;
import com.taiji.cdp.daily.searchVo.DailyPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author sunyi
 */
@Repository
public class DailyRepository extends BaseJpaRepository<Daily, String> {

    /**
     * 新增日报管理信息Vo
     * @param entity 新增日报管理entity
     * @return Daily
     */
    @Override
    @Transactional
    public Daily save(Daily entity) {
        Assert.notNull(entity, "Daily 不能为null");
        Daily result;
        if (!StringUtils.hasText(entity.getId())) {
            entity.setId(null);
            result = super.save(entity);
        } else {
            Daily temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, temp);
            result = super.save(temp);
        }
        return result;
    }

    /**
     * 分页查询
     * @param dailyPageVo
     * @param pageable
     * @return
     */
    public Page<Daily> findPage(DailyPageVo dailyPageVo, Pageable pageable){
        JPQLQuery<Daily> query = buildQuery(dailyPageVo);
        return findAll(query,pageable);
    }

    /**
     * 查询所以
     */
    public List<Daily> findListAll(){
        QDaily daily = QDaily.daily;
        JPQLQuery<Daily> query = from(daily);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(daily.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(daily.updateTime.desc());
        return findAll(query);
    }

    private JPQLQuery<Daily> buildQuery(DailyPageVo dailyPageVo){
        QDaily daily = QDaily.daily;
        JPQLQuery<Daily> query = from(daily);
        BooleanBuilder builder = new BooleanBuilder();
        String title = dailyPageVo.getTitle();
        if(StringUtils.hasText(title)){
            builder.and(daily.title.contains(title));
        }
        List<String> ids = dailyPageVo.getIds();
        if(!CollectionUtils.isEmpty(ids)){
            builder.and(daily.id.in(ids));
        }
        builder.and(daily.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder)
                .orderBy(daily.updateTime.desc());
        return query;
    }

}
