package com.taiji.cdp.base.sms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.base.sms.entity.QSmsTemp;
import com.taiji.cdp.base.sms.entity.SmsTemp;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SmsTempRepository extends BaseJpaRepository<SmsTemp,String>{

    @Override
    @Transactional
    public SmsTemp save(SmsTemp entity){
        Assert.notNull(entity,"sms 对象不能为Null");

        SmsTemp result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            SmsTemp temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return  result;
    }

    // 分页查询
    // 参数：name,content
    public Page<SmsTemp> findPage(MultiValueMap<String,Object> params, Pageable pageable){
        QSmsTemp smsTemp = QSmsTemp.smsTemp;
        JPQLQuery<SmsTemp> query = from(smsTemp);
        BooleanBuilder builder = new BooleanBuilder();

        if(params.containsKey("name")){
            String name = params.getFirst("name").toString();
            if(!StringUtils.isEmpty(name)){
                builder.and(smsTemp.name.contains(name));
            }
        }

        if(params.containsKey("content")){
            String content = params.getFirst("content").toString();
            if(!StringUtils.isEmpty(content)){
                builder.and(smsTemp.content.contains(content));
            }
        }

        builder.and(smsTemp.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(SmsTemp.class,
                smsTemp.id,
                smsTemp.name,
                smsTemp.code,
                smsTemp.content,
                smsTemp.notes
                )).where(builder).orderBy(smsTemp.updateTime.desc());

        return findAll(query,pageable);
    }

    //不分页查询
    //参数：name,content
    public List<SmsTemp> findList(MultiValueMap<String,Object> params){
        QSmsTemp smsTemp = QSmsTemp.smsTemp;
        JPQLQuery<SmsTemp> query = from(smsTemp);
        BooleanBuilder builder = new BooleanBuilder();

        if(params.containsKey("name")){
            String name = params.getFirst("name").toString();
            if(!StringUtils.isEmpty(name)){
                builder.and(smsTemp.name.contains(name));
            }
        }

        if(params.containsKey("content")){
            String content = params.getFirst("content").toString();
            if(!StringUtils.isEmpty(content)){
                builder.and(smsTemp.content.contains(content));
            }
        }

        builder.and(smsTemp.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.where(builder).orderBy(smsTemp.updateTime.desc());
        return findAll(query);
    }

}
