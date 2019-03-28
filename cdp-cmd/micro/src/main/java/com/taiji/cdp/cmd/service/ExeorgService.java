package com.taiji.cdp.cmd.service;

import com.taiji.cdp.cmd.entity.ExeorgEntity;
import com.taiji.cdp.cmd.repository.ExeorgRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:ExeorgService.java</p >
 * <p>Description: 舆情调控单与处置单位关联ExeorgService类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/02/26 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class ExeorgService extends BaseService<ExeorgEntity, String> {

    ExeorgRepository repository;

    public ExeorgEntity create(ExeorgEntity entity) {
        Assert.notNull(entity, "entity 不能为null");
        ExeorgEntity result = repository.save(entity);
        return result;
    }

    /**
     * 创建多个中间表对象
     */
    public List<ExeorgEntity> createList(List<ExeorgEntity> list) {
        List<ExeorgEntity> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (ExeorgEntity exeorgEntity : list) {
                ExeorgEntity result = create(exeorgEntity);
                if (null != result) {
                    resultList.add(result);
                }
            }
        }
        return resultList;
    }


    public ExeorgEntity update(ExeorgEntity entity, String id) {
        Assert.hasText(id, "id 不能为空字符串");
        Assert.notNull(entity, "entity 不能为null");
        entity.setId(id);
        ExeorgEntity result = repository.save(entity);
        return result;
    }

    public List<ExeorgEntity> findByCmdId(String cmdId) {
        Assert.hasText(cmdId, "cmdId 不能为空字符串");
        List<ExeorgEntity> result = repository.findByCmdId(cmdId);
        return result;
    }

    public List<ExeorgEntity> findcmdIdByOrgId(String orgId) {
        Assert.hasText(orgId, "id 不能为空字符串");
        List<ExeorgEntity> result = repository.findcmdIdByOrgId(orgId);
        return result;
    }


}
