package com.taiji.cdp.report.service;

import com.taiji.cdp.report.entity.EvidenceInfo;
import com.taiji.cdp.report.repository.EvdienceRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@AllArgsConstructor
public class EvidenceService extends BaseService<EvidenceInfo, String> {

    EvdienceRepository repository;

    /**
     * 新增电子取证Vo
     *
     * @param entity 新增电子取证entity
     * @return EvidenceInfo
     */
    public EvidenceInfo create(EvidenceInfo entity) {
        Assert.notNull(entity, "EvidenceInfo 不能为null");
        EvidenceInfo result = repository.save(entity);
        return result;
    }

    /**
     * 根据id获取单条电子取证信息Vo
     *
     * @param id 信息Id
     * @return EvidenceInfo
     */
    public EvidenceInfo findOne(String id) {
        Assert.hasText(id, "id 不能为空字符串");
        EvidenceInfo result = repository.findOne(id);
        return result;
    }

    /**
     * 根据舆情信息id获取单条电子取证信息Vo
     *
     * @param id 信息Id
     * @return EvidenceInfo
     */
    public List<EvidenceInfo> findByJudgeId(String id) {
        Assert.hasText(id, "id 不能为空字符串");
        List<EvidenceInfo> result = repository.findByJudgeId(id);
        return result;
    }
}
