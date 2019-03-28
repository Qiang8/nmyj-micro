package com.taiji.cdp.daily.monthly.service;

import com.taiji.cdp.daily.monthly.entity.Monthly;
import com.taiji.cdp.daily.monthly.repository.MonthlyRepository;
import com.taiji.cdp.daily.searchVo.MonthlyPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * <p>Title:MonthlyService.java</p >
 * <p>Description: 月报管理micro层 service</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class MonthlyService extends BaseService<Monthly, String> {

    MonthlyRepository repository;

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return Monthly
     */
    public Monthly findOne(String id) {
        Assert.hasText(id, "id不能为null或空字符串!");
        return repository.findOne(id);
    }

    /**
     * 新增方法
     *
     * @param entity
     * @return Monthly
     */
    public Monthly addMonthly(Monthly entity) {
        Assert.notNull(entity, "entity不能为null!");
        return repository.save(entity);
    }

    /**
     * 根据参数获取分页RoleVo多条记录。
     *
     * @return RestPageImpl<Monthly>
     */
    public Page<Monthly> findPage(MonthlyPageVo pageVo, Pageable pageable) {
        return repository.findPage(pageVo, pageable);
    }

    /**
     * 更新月报信息
     *
     * @param entity
     * @param id     更新 Id
     * @return Role
     */
    public Monthly update(Monthly entity, String id) {
        Assert.notNull(entity, "entity不能为null!");
        Assert.hasText(id, "id不能为null或空字符串!");
        return repository.saveEntity(entity);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return String
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id, "id不能为null或空字符串!");
        Monthly entity = repository.findOne(id);
        repository.deleteLogic(entity, delFlagEnum);
    }

    /**
     * 根据id发布一条信息。
     *
     * @param id
     * @return String
     */
    @Transactional(rollbackFor = Exception.class)
    public void publishInfo(String id, StatusEnum statusEnum) {
        Assert.hasText(id, "id不能为null或空字符串!");
        repository.publish(id, statusEnum);
    }

}