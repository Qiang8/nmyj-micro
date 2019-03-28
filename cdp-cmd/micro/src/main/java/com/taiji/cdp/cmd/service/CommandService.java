package com.taiji.cdp.cmd.service;

import com.taiji.cdp.cmd.entity.CommandEntity;
import com.taiji.cdp.cmd.repository.CommandRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

/**
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class CommandService extends BaseService<CommandEntity, String> {

    CommandRepository repository;

    public CommandEntity create(CommandEntity entity) {
        Assert.notNull(entity, "entity 不能为null");
        CommandEntity result = repository.save(entity);
        return result;
    }

    public CommandEntity update(CommandEntity entity, String id) {
        Assert.hasText(id, "id 不能为空字符串");
        Assert.notNull(entity, "entity 不能为null");
        entity.setId(id);
        CommandEntity result = repository.save(entity);
        return result;
    }

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return CommandEntity
     */
    public CommandEntity findOne(String id) {
        Assert.hasText(id, "id不能为null或空字符串!");
        return repository.findOne(id);
    }

    /**
     * 根据参数获取分页获取多条舆情调控单信息。
     *
     * @return RestPageImpl<CommandEntity>
     */
    public Page<CommandEntity> findPage(MultiValueMap<String, Object> parameter, Pageable pageable) {
        return repository.findPage(parameter, pageable);
    }

    /**
     * 根据参数获取分页获取多条反馈的舆情调控单信息。
     *
     * @return RestPageImpl<CommandEntity>
     */
    public Page<CommandEntity> findRecivePage(MultiValueMap<String, Object> parameter, Pageable pageable) {
        return repository.findRecivePage(parameter, pageable);
    }

    /**
     * 根据id发布一条信息。
     *
     * @param cdId
     * @return String
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String cdId, String status) {
        Assert.hasText(cdId, "cdId不能为null或空字符串!");
        repository.updateStatus(cdId, status);
    }


}
