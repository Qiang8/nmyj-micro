package com.taiji.base.sys.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.base.sys.entity.DicGroup;
import com.taiji.base.sys.entity.DicGroupItem;
import com.taiji.base.sys.entity.QDicGroup;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统字典项Repository类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Repository
public class DicGroupRepository extends BaseJpaRepository<DicGroup,String>{

    public DicGroup findOneByDicCode(String dicCode) {
        Assert.hasText(dicCode, "dicCode不能为null或空字符串!");

        QDicGroup qDicGroup = QDicGroup.dicGroup;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qDicGroup.dicCode.eq(dicCode));

        return findOne(builder);
    }

    public List<DicGroup> findAll(String dicName, String status){
        QDicGroup qDicGroup = QDicGroup.dicGroup;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(dicName))
        {
            builder.and(qDicGroup.dicName.eq(dicName));
        }

        if(StringUtils.hasText(status))
        {
            builder.and(qDicGroup.status.eq(status));
        }

        builder.and(qDicGroup.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder);
    }

    public Page<DicGroup> findPage(String dicName, String status, Pageable pageable){
        QDicGroup qDicGroup = QDicGroup.dicGroup;

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(dicName))
        {
            builder.and(qDicGroup.dicName.contains(dicName));
        }

        if(StringUtils.hasText(status))
        {
            builder.and(qDicGroup.status.eq(status));
        }

        builder.and(qDicGroup.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        return findAll(builder,pageable);
    }

    @Override
    @Transactional
    public DicGroup save(DicGroup entity)
    {
        Assert.notNull(entity, "DicGroup must not be null!");

        DicGroup result;
        if(entity.getId() == null)
        {
            result = super.save(entity);
        }
        else
        {
            DicGroup tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }

    public Boolean checkUnique(String id,String dicCode) {
        QDicGroup qDicGroup = QDicGroup.dicGroup;

        JPQLQuery<DicGroup> query = from(qDicGroup);

        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(id))
        {
            builder.and(qDicGroup.id.ne(id));
        }

        builder.and(qDicGroup.dicCode.eq(dicCode));
        builder.and(qDicGroup.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        List<DicGroup> list = query.where(builder).fetch();
        if(list.size() > 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //redis 缓存操作使用 --------- createBy qizj,2018年11月15日10:18:40 ---------------------
    /**
     * dicGroup查出所有的 dicCode -- 未删除(del_flag = '1'),启用(status = '1')
     */
    public List<String> findAllDicCode(){
        QDicGroup dicGroup = QDicGroup.dicGroup;
        JPQLQuery<DicGroup> query = from(dicGroup);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(dicGroup.status.eq(StatusEnum.ENABLE.getCode())); //启用
        builder.and(dicGroup.delFlag.eq(DelFlagEnum.NORMAL.getCode()));//未删除

        List<String> dicCodes = query.select(dicGroup.dicCode).where(builder).fetch();
        return dicCodes;
    }

}
