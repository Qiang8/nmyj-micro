package com.taiji.base.sys.service;

import com.taiji.base.redis.service.DicItemRedisService;
import com.taiji.base.sys.entity.DicGroup;
import com.taiji.base.sys.repository.DicGroupRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 系统字典项Service类
 *
 * @author scl
 *
 * @date 2018-08-23
 */
@Slf4j
@Service
@AllArgsConstructor
public class DicGroupService extends BaseService<DicGroup,String> {

    DicGroupRepository repository;
    DicItemRedisService dicItemRedisService;

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return DicGroup
     */
    public DicGroup findOne(String id) {
        Assert.hasText(id, "id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 根据dicCode获取一条记录。
     *
     * @param dicCode
     * @return DicGroup
     */
    public DicGroup findOneByDicCode(String dicCode) {
        Assert.hasText(dicCode, "dicCode不能为null或空字符串!");

        return repository.findOneByDicCode(dicCode);
    }

    /**
     * 根据参数获取DicGroup多条记录。
     * <p>
     *
     * @param dicName （可选）
     * @param status    （可选）
     * @return List<DicGroup>
     */
    public List<DicGroup> findAll(String dicName, String status){
        return repository.findAll(dicName,status);
    }

    /**
     * 根据参数获取分页DicGroup多条记录。
     * <p>
     *
     * @param dicName （可选 模糊查询）
     * @param status    （可选）
     * @return RestPageImpl<DicGroup>
     */
    public Page<DicGroup> findPage(String dicName, String status, Pageable pageable) {
        return repository.findPage(dicName,status,pageable);
    }

    /**
     * 新增DicGroup，DicGroup不能为空。
     *
     * @param entity
     * @return DicGroup
     */
    public DicGroup create(DicGroup entity) {
        Assert.notNull(entity,"entity不能为null!");

        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        entity.setStatus(StatusEnum.ENABLE.getCode());

        DicGroup result = repository.save(entity);
        //结果存入redis
        dicItemRedisService.createDicGroupByDicCode(result.getDicCode());

        return result;
    }

    /**
     * 更新DicGroup，DicGroup不能为空。
     *
     * @param entity
     * @param id 更新DicGroup Id
     * @return DicGroup
     */
    public DicGroup update(DicGroup entity, String id) {
        Assert.notNull(entity,"entity不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");
        DicGroup result = repository.save(entity);

        //redis操作
        if(StatusEnum.ENABLE.getCode().equals(result.getStatus())){ //启用字典表操作
            dicItemRedisService.createDicGroupByDicCode(result.getDicCode());
        }else{ //禁用字典表操作
            dicItemRedisService.deleteDicGroupByDicCode(result.getDicCode());
        }

        return result;
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return String
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum) {
        Assert.hasText(id,"id不能为null或空字符串!");
        DicGroup entity = repository.findOne(id);
        repository.deleteLogic(entity, delFlagEnum);

        //redis操作
        dicItemRedisService.deleteDicGroupByDicCode(entity.getDicCode());
    }

    /**
     * 根据参数判断用户名是否被占用。
     *
     * @param id      （可选）
     * @param dicCode （必选）
     * @return Boolean
     */
    public Boolean checkUnique(String id,String dicCode) {
        Assert.hasText(dicCode,"dicCode不能为null或空字符串!");

        return repository.checkUnique(id,dicCode);
    }

}
