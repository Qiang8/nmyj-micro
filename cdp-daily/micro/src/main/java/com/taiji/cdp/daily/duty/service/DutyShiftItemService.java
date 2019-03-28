package com.taiji.cdp.daily.duty.service;

import com.taiji.cdp.daily.duty.entity.DutyShiftItemEntity;
import com.taiji.cdp.daily.duty.repository.DutyShiftItemRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>Title:DutyShiftItemService.java</p >
 * <p>Description: 交接班对应舆情信息管理micro层 service</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/21 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class DutyShiftItemService extends BaseService<DutyShiftItemEntity, String> {

    DutyShiftItemRepository repository;

    /**
     * 新增方法
     *
     * @param entity
     * @return DutyShiftItemEntity
     */
    public DutyShiftItemEntity addDutyItem(DutyShiftItemEntity entity) {
        Assert.notNull(entity, "entity不能为null!");
        return repository.save(entity);
    }

    /**
     * 根据交接班dutyId获取交接班信息列表。
     *
     * @param id
     * @return DutyShiftItemEntity
     */
    public List<DutyShiftItemEntity> findDutyItem(String id) {
        Assert.hasText(id, "id不能为null或空字符串!");
        return repository.findByDutyId(id);
    }

}