package com.taiji.cdp.base.sms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.base.sms.entity.QSmsRecieve;
import com.taiji.cdp.base.sms.entity.SmsRecieve;
import com.taiji.cdp.base.sms.vo.SmsRecieveVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class SmsRecieveRepository extends BaseJpaRepository<SmsRecieve,String> {

    //不分页
    public List<SmsRecieve> findList(SmsRecieveVo smsRecieveVo){
        QSmsRecieve smsRecieve = QSmsRecieve.smsRecieve;
        JPQLQuery<SmsRecieve> query = from(smsRecieve);

        BooleanBuilder builder = new BooleanBuilder();

        String receiverName = smsRecieveVo.getReceiverName();
        if (StringUtils.hasText(receiverName)){
            builder.and(smsRecieve.receiverName.contains(receiverName));
        }
        return findAll(builder);
    }

    @Override
    @Transactional
    public SmsRecieve save(SmsRecieve entity){
        Assert.notNull(entity,"sms 对象不能为Null");

        SmsRecieve result;
        if (StringUtils.isEmpty(entity.getId())){
            result = super.save(entity);
        }else {
            SmsRecieve temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity,temp);
            result = super.save(temp);
        }
        return  result;
    }
}
