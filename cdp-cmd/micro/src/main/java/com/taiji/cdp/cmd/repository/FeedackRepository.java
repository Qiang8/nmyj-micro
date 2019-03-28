package com.taiji.cdp.cmd.repository;

import com.taiji.cdp.cmd.entity.Feedback;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


/**
 * @author sunyi
 */
@Repository
@Transactional(readOnly = true)
public class FeedackRepository extends BaseJpaRepository<Feedback, String> {

    /**
     * 新增处置反馈信息Vo
     *
     * @param entity 新增处置反馈entity
     * @return Topic
     */
    @Override
    @Transactional
    public Feedback save(Feedback entity) {
        Assert.notNull(entity, "Feedback 不能为null");
        Feedback result;
        if (!StringUtils.hasText(entity.getId())) {
            entity.setId(null);
            result = super.save(entity);
        } else {
            Feedback temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, temp);
            result = super.save(temp);
        }
        return result;
    }


}
