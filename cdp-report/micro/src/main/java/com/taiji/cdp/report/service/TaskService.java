package com.taiji.cdp.report.service;

import com.taiji.cdp.report.entity.Task;
import com.taiji.cdp.report.repository.TaskRepository;
import com.taiji.cdp.report.vo.StatVQueryVo;
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
public class TaskService extends BaseService<Task,String> {

    TaskRepository repository;

    //新增task
    public Task create(Task entity){
        Assert.notNull(entity,"Task 不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        return repository.save(entity);
    }

    //更新task
    public Task update(Task entity,String id){
        Assert.notNull(entity,"Task 不能为null");
        Assert.hasText(id,"id 不能为空字符串");
        entity.setId(id);
        return repository.save(entity);
    }

    //根据id 获取 task
    public Task findOne(String id){
        Assert.hasText(id,"id 不能为空字符串");
        return repository.findOne(id);
    }

    //逻辑删除task
    public void deleteLogic(String id,DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        Task task = repository.findOne(id);
        repository.deleteLogic(task,delFlagEnum);
    }

    //分页查询-- 发送方
    public Page<Task> findPage(MultiValueMap<String, Object> params,Pageable pageable){
        return repository.findPage(params,pageable);
    }


    /**根据条件查询符合的数据*/
    public List<Task> findStatList(List<String> taskIds, StatVQueryVo statVQueryVo){
        return repository.findStatList(taskIds,statVQueryVo);
    }

}
