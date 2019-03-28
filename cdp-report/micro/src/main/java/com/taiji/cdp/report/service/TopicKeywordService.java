package com.taiji.cdp.report.service;

import com.taiji.cdp.report.entity.TopicKeyword;
import com.taiji.cdp.report.repository.TopicKeywordRepository;
import com.taiji.cdp.report.vo.TopicKeywordVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@AllArgsConstructor
public class TopicKeywordService extends BaseService<TopicKeyword, String> {

    TopicKeywordRepository repository;

    /**
     * 新增专题管理关键字
     * @param entity 新增专题管理关键字
     * @return Topic
     */
    public TopicKeyword create(TopicKeyword entity) {
        Assert.notNull(entity, "Topic 不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        TopicKeyword result = repository.save(entity);
        return result;
    }

    /**
     * 更新专题管理关键字
     * @param entity 专题管理关键字entity
     * @param id     专题管理关键字Id
     * @return Topic
     */
    public TopicKeyword update(TopicKeyword entity, String id) {
        Assert.hasText(id, "id 不能为空字符串");
        Assert.notNull(entity, "Topic 不能为null");
        entity.setId(id);
        TopicKeyword result = repository.save(entity);
        return result;
    }

    /**
     * 根据id获取单条专题管理关键字信息Vo
     * @param id 关键字信息Id
     * @return Topic
     */
    public TopicKeyword findOne(String id) {
        Assert.hasText(id, "id 不能为空字符串");
        TopicKeyword result = repository.findOne(id);
        return result;
    }

    /**
     * 根据id逻辑删除单条专题管理关键字信息
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        TopicKeyword result = repository.findOne(id);
        repository.deleteLogic(result,delFlagEnum);
    }


    /**
     * 查询所以专题管理关键字信息
     */
    public List<TopicKeyword> findList(TopicKeywordVo vo){
        return repository.findList(vo);
    }

}
