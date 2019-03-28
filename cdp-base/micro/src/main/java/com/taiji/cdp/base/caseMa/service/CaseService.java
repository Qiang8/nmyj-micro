package com.taiji.cdp.base.caseMa.service;
import com.taiji.cdp.base.caseMa.entity.Case;
import com.taiji.cdp.base.caseMa.repository.CaseRepository;
import com.taiji.cdp.base.caseMa.vo.CaseVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

@Service
@AllArgsConstructor
public class CaseService extends BaseService<Case,String> {

    CaseRepository repository;

    /**
     * 新增案例信息
     * @param entity
     * @return Case
     */
    public Case create(Case entity){
        Assert.notNull(entity,"Case 不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }
    /**
     * 单条案例信息查询
     * @param id
     * @return Case
     */
    public Case findOne(String id) {
        Assert.hasText(id,"id 不能为null");
        return repository.findOne(id);
    }
    /**
     * 案例信息删除
     * @param id
     * @return
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id 不能为null");
        Case result = repository.findOne(id);
        repository.deleteLogic(result,delFlagEnum);
    }
    /**
     * 案例信息修改
     * @param entity,id
     * @return Case
     */
    public Case update(Case entity, String id) {
        Assert.hasText(id, "id 不能为空字符串");
        Assert.notNull(entity, "Topic 不能为null");
        entity.setId(id);
        Case result = repository.save(entity);
        return result;
    }
    /**
     * 案例信息分页查询
     * @param params
     * 参数;page,size
     * 参数：title(可选)、startTimeStart(首发时间开始)、startTimeEnd(上报时间结束)、caseTypeIds(案例类型,string[])
     * @return ResponseEntity<RestPageImpl<CaseVo>>
     *     不带取证信息
     */
    public Page<Case> findPage(MultiValueMap<String, Object> params, Pageable pageable) {
        Page<Case> result = repository.findPage(params,pageable);
        return result;
    }
}
