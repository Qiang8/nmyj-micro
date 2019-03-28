package com.taiji.cdp.report.service;

import com.taiji.cdp.report.entity.ConsInfo;
import com.taiji.cdp.report.repository.ConsInfoRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsInfoService extends BaseService<ConsInfo,String>{

    ConsInfoRepository repository;

    /**
     * 新增舆情信息
     */
    public ConsInfo create(ConsInfo entity){
        Assert.notNull(entity,"ConsInfo 不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        ConsInfo result = repository.save(entity);
        return result;
    }

    /**
     * 更新舆情信息
     */
    public ConsInfo update(ConsInfo entity,String id){
        Assert.hasText(id,"id 不能为空字符串");
        Assert.notNull(entity,"ConsInfo 不能为null");
        entity.setId(id);
        ConsInfo result = repository.save(entity);
        return result;
    }

    /**
     * 根据id获取单条舆情信息
     */
    public ConsInfo findOne(String id){
        Assert.hasText(id,"id 不能为空字符串");
        ConsInfo result = repository.findOne(id);
        return result;
    }

    /**
     * 根据id逻辑删除单条舆情信息
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        ConsInfo result = repository.findOne(id);
        repository.deleteLogic(result,delFlagEnum);
    }

    /**
     * 舆情信息分页查询-- 盟市使用
     * 参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)、createTimeStart(创建时间开始)
     *     、createTimeEnd(创建时间结束)、statuses(舆情信息状态,string[])、orgId(创建部门orgId)
     */
    public Page<ConsInfo> findPage(MultiValueMap<String, Object> params, Pageable pageable){
        Page<ConsInfo> result = repository.findPage(params,pageable);
        return result;
    }

    /**
     * 舆情信息分页查询-- 自治区使用
     * 参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)
     *      、statuses(舆情信息状态,string[])
     */
    public Page<ConsInfo> findRecPage(MultiValueMap<String, Object> params, Pageable pageable){
        Page<ConsInfo> result = repository.findRecPage(params,pageable);
        return result;
    }

    /**
     * 舆情信息不分页查询
     * @param params
     * 参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)、statuses(舆情信息状态,string[])
     * @return ResponseEntity<List<ConsInfoVo>>
     *     不带取证信息
     */
    public List<ConsInfo> findRecList(MultiValueMap<String, Object> params){
        List<ConsInfo> result = repository.findRecList(params);
        return result;
    }

    /**
     *  根据舆情ID数组查询舆情信息列表-不分页（交接班使用）
     */
    public List<ConsInfo> findList(List<String> infoIds){
        List<ConsInfo> result = repository.findList(infoIds);
        return result;
    }

}
