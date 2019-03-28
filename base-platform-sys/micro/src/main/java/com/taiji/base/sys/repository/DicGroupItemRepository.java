package com.taiji.base.sys.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.sys.entity.DicGroupItem;
import com.taiji.base.sys.entity.QDicGroupItem;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统数据字典Repository类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Repository
public class DicGroupItemRepository extends BaseJpaRepository<DicGroupItem,String>{
    public List<DicGroupItem> findAll(String dicCode){
        QDicGroupItem qDicGroupItem = QDicGroupItem.dicGroupItem;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(dicCode))
        {
            builder.and(qDicGroupItem.dicCode.eq(dicCode));
        }

        builder.and(qDicGroupItem.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        Sort sort = new Sort(Sort.Direction.ASC,"orders");

        return findAll(builder,sort);
    }

    @Override
    @Transactional
    public DicGroupItem save(DicGroupItem entity)
    {
        Assert.notNull(entity, "DicGroupItem must not be null!");

        DicGroupItem result;
        if(entity.getId() == null)
        {
            result = super.save(entity);
        }
        else
        {
            DicGroupItem tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }

    //redis 缓存操作使用 --------- createBy qizj,2018年11月15日10:18:40 ---------------------
    /**
     * 根据dicCode获取所有的字典项List
     */
    public List<DicGroupItem> findItemsByDicCode(String dicCode){
        Assert.hasText(dicCode,"dicCode 不能为空字符串");
        return this.findAll(dicCode);
    }
    /**
     * 根据dicCode获取所有的字典项id串
     */
    public List<String> findItemIdsByDicCode(String dicCode){
        Assert.hasText(dicCode,"dicCode 不能为空字符串");

        QDicGroupItem qDicGroupItem = QDicGroupItem.dicGroupItem;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qDicGroupItem.dicCode.eq(dicCode));
        builder.and(qDicGroupItem.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        JPQLQuery<DicGroupItem> query = from(qDicGroupItem);

        return query.select(qDicGroupItem.id).where(builder).orderBy(qDicGroupItem.orders.asc()).fetch();
    }
}
