package com.taiji.cdp.report.service;

import com.taiji.cdp.report.entity.ConsJudgeInfo;
import com.taiji.cdp.report.repository.ConsJudgeRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsJudgeService extends BaseService<ConsJudgeInfo, String> {

    ConsJudgeRepository repository;

    /**
     * 新增研判信息Vo
     *
     * @param entity 新增研判信息vo
     * @return ConsJudgeInfo
     */
    public ConsJudgeInfo create(ConsJudgeInfo entity) {
        Assert.notNull(entity, "ConsJudgeInfo 不能为null");
        ConsJudgeInfo result = repository.save(entity);
        return result;
    }

    /**
     * 更新研判信息Vo
     *
     * @param entity 研判信息entity
     * @param id     信息Id
     * @return ConsJudgeInfo
     */
    public ConsJudgeInfo update(ConsJudgeInfo entity, String id) {
        Assert.hasText(id, "id 不能为空字符串");
        Assert.notNull(entity, "ConsJudgeInfo 不能为null");
        entity.setId(id);
        ConsJudgeInfo result = repository.save(entity);
        return result;
    }

    /**
     * 根据id获取单条研判信息Vo
     *
     * @param id 信息Id
     * @return ConsJudgeInfo
     */
    public ConsJudgeInfo findOne(String id) {
        Assert.hasText(id, "id 不能为空字符串");
        ConsJudgeInfo result = repository.findOne(id);
        return result;
    }

    /**
     * 根据舆情信息id获取单条研判信息Vo
     *
     * @param infoid 信息Id
     * @return List<ConsJudgeInfo>
     */
    public List<ConsJudgeInfo> findByJudgeId(String infoid) {
        Assert.hasText(infoid, "infoid 不能为空字符串");
        List<ConsJudgeInfo> result = repository.findByJudgeId(infoid);
        return result;
    }

}
