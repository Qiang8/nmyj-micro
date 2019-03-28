package com.taiji.cdp.daily.duty.service;

import com.taiji.cdp.daily.duty.entity.DutyShiftEntity;
import com.taiji.cdp.daily.duty.repository.DutyShiftRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

/**
 * <p>Title:DutyShiftService.java</p >
 * <p>Description: 交接班管理micro层 service</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/21 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class DutyShiftService extends BaseService<DutyShiftEntity, String> {

    DutyShiftRepository repository;

    /**
     * 新增方法
     *
     * @param entity
     * @return DutyShiftEntity
     */
    public DutyShiftEntity addDuty(DutyShiftEntity entity) {
        Assert.notNull(entity, "entity不能为null!");
        return repository.save(entity);
    }

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return DutyShiftEntity
     */
    public DutyShiftEntity findOne(String id) {
        Assert.hasText(id, "id不能为null或空字符串!");
        return repository.findOne(id);
    }

    /**
     * 根据参数获取分页RoleVo多条记录。
     *
     * @return RestPageImpl<DutyShiftEntity>
     */
    public Page<DutyShiftEntity> findPage(MultiValueMap<String, Object> parameter, Pageable pageable) {
        return repository.findPage(parameter, pageable);
    }

    /**
     * 获取最新一条交接班信息
     *
     * @return DutyShiftEntity
     */
    public DutyShiftEntity findNewDuty() {
        return repository.findNewDuty();
    }


}