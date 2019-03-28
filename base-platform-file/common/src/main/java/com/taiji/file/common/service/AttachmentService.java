package com.taiji.file.common.service;

import com.taiji.base.file.enums.FileStatusEnum;
import com.taiji.file.common.entity.Attachment;
import com.taiji.file.common.repository.AttachmentRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>Title:AttachmentService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/23 10:13</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class AttachmentService extends BaseService<Attachment,String> {

    AttachmentRepository repository;

    /**
     * 根据id获取一条记录。
     *
     * @param id
     * @return Attachment
     */
    public Attachment findOne(String id) {
        Assert.hasText(id,"id不能为null或空字符串!");

        return repository.findOne(id);
    }

    /**
     * 根据多个id获取多条文件上传记录。
     *
     * @param ids
     * @return List<Attachment>
     */
    public List<Attachment> findAll(List<String> ids)
    {
        Assert.notEmpty(ids,"ids不能为空List");

        return repository.findAll(ids);
    }

    /**
     * 新增Attachment，Attachment不能为空。
     *
     * @param entity
     * @return Attachment
     */
    public Attachment create(Attachment entity) {
        Assert.notNull(entity,"entity不能为null!");

        entity.setFileStatus(FileStatusEnum.UPLOADED.getCode());

        return repository.save(entity);
    }

    /**
     * 更新Attachment，Attachment不能为空。
     *
     * @param entity
     * @param id 更新Attachment Id
     * @return Attachment
     */
    public Attachment update(Attachment entity, String id) {
        Assert.notNull(entity,"entity不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        return repository.save(entity);
    }


}
