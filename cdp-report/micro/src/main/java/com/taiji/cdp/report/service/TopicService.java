package com.taiji.cdp.report.service;

import com.taiji.cdp.report.entity.Topic;
import com.taiji.cdp.report.repository.TopicRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@AllArgsConstructor
public class TopicService extends BaseService<Topic, String> {

    TopicRepository repository;

    /**
     * 新增专题管理
     * @param entity 新增专题管理
     * @return Topic
     */
    public Topic create(Topic entity) {
        Assert.notNull(entity, "Topic 不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        Topic result = repository.save(entity);
        return result;
    }

    /**
     * 更新专题管理
     * @param entity 专题管理entity
     * @param id     专题管理Id
     * @return Topic
     */
    public Topic update(Topic entity, String id) {
        Assert.hasText(id, "id 不能为空字符串");
        Assert.notNull(entity, "Topic 不能为null");
        entity.setId(id);
        Topic result = repository.save(entity);
        return result;
    }

    /**
     * 根据id获取单条专题管理信息Vo
     * @param id 信息Id
     * @return Topic
     */
    public Topic findOne(String id) {
        Assert.hasText(id, "id 不能为空字符串");
        Topic result = repository.findOne(id);
        return result;
    }

    /**
     * 根据id逻辑删除单条专题管理信息
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        Topic result = repository.findOne(id);
        repository.deleteLogic(result,delFlagEnum);
    }


    /**
     * 查询所以专题管理信息
     */
    public List<Topic> findList(){
        return repository.findListAll();
    }

}
