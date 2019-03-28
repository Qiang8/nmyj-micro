package com.taiji.cdp.cmd.service;

import com.taiji.cdp.cmd.entity.Feedback;
import com.taiji.cdp.cmd.repository.FeedackRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackService extends BaseService<Feedback, String> {

    FeedackRepository repository;

    /**
     * 新增处置反馈
     * @param entity 新增处置反馈
     * @return Feedback
     */
    public Feedback create(Feedback entity) {
        Assert.notNull(entity, "Feedback 不能为null");
        Feedback result = repository.save(entity);
        return result;
    }


    /**
     * 根据id获取单条处置反馈信息Vo
     * @param id 信息Id
     * @return Feedback
     */
    public Feedback findOne(String id) {
        Assert.hasText(id, "id 不能为空字符串");
        Feedback result = repository.findOne(id);
        return result;
    }



    /**
     * 查询所以处置反馈信息
     */
    public List<Feedback> findList(){
        return repository.findAll();
    }

}
