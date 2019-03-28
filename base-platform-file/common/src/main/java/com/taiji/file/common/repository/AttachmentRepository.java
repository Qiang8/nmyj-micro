package com.taiji.file.common.repository;

import com.taiji.file.common.entity.Attachment;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * <p>Title:AttachmentRepository.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/23 10:13</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
public class AttachmentRepository extends BaseJpaRepository<Attachment,String> {

    @Override
    @Transactional
    public Attachment save(Attachment entity)
    {
        Assert.notNull(entity, "entity must not be null!");

        Attachment result;
        if(entity.getId() == null)
        {
            result = super.save(entity);
        }
        else
        {
            Attachment tempEntity = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, tempEntity);
            result = super.save(tempEntity);
        }

        return result;
    }
}
