package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.taiji.cdp.report.entity.QTask;
import com.taiji.cdp.report.entity.Task;
import com.taiji.cdp.report.vo.StatVQueryVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.utils.BeanUtils;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class TaskRepository extends BaseJpaRepository<Task,String> {

    @Override
    @Transactional
    public Task save(Task entity){
        Assert.notNull(entity,"ConsInfo 不能为null");
        Task result;
        if(!StringUtils.hasText(entity.getId()))
        {
            entity.setId(null);
            result = super.save(entity);
        }
        else
        {
            Task temp = findOne(entity.getId());
            BeanUtils.copyNonNullProperties(entity, temp);
            result = super.save(temp);
        }

        return result;
    }

    //分页查询-- 发送方
    // 参数：infoTitle(舆情标题),title(任务标题),createTimeStart(创建开始时间),createTimeEnd(创建结束时间),
    //     status(任务状态：0未下发 1已下发),orgId(创建部门ID)
    public Page<Task> findPage(MultiValueMap<String, Object> params, Pageable pageable){
        QTask task =QTask.task;
        JPQLQuery<Task> query = from(task);
        BooleanBuilder builder = new BooleanBuilder();
        if(params.containsKey("infoTitle")){
            String infoTitle = params.getFirst("infoTitle").toString();
            if(!StringUtils.isEmpty(infoTitle)){
                builder.and(task.infoTitle.contains(infoTitle));
            }
        }
        if(params.containsKey("title")){
            String title = params.getFirst("title").toString();
            if(!StringUtils.isEmpty(title)){
                builder.and(task.title.contains(title));
            }
        }
        if(params.containsKey("createTimeStart")){
            LocalDateTime createTimeStart = DateUtil.strToLocalDateTime(params.getFirst("createTimeStart").toString());
            if(null!=createTimeStart){
                builder.and(task.createTime.after(createTimeStart));
            }
        }
        if(params.containsKey("createTimeEnd")){
            LocalDateTime createTimeEnd = DateUtil.strToLocalDateTime(params.getFirst("createTimeEnd").toString());
            if(null!=createTimeEnd){
                builder.and(task.createTime.before(createTimeEnd));
            }
        }
        if(params.containsKey("status")){
            String status = params.getFirst("status").toString();
            if(!StringUtils.isEmpty(status)){
                builder.and(task.taskStatus.eq(status));
            }
        }
        if(params.containsKey("orgId")){
            String orgId = params.getFirst("orgId").toString();
            if(!StringUtils.isEmpty(orgId)){
                builder.and(task.createOrgId.eq(orgId));
            }
        }
        builder.and(task.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.select(Projections.bean(Task.class,
                task.id,
                task.infoId,
                task.infoTitle,
                task.title,
                task.taskStatus,
                task.createBy,
                task.createTime)).where(builder).orderBy(task.createTime.desc());

        return findAll(query,pageable);
    }



    /**
     * 统计下发给本单位的任务数量（本年度）
     * cj_task_exeorg表中的org_id=登录人所属部门
     */
    public List<Task> findStatList(List<String> taskIds, StatVQueryVo statVQueryVo){
        QTask task =QTask.task;
        JPQLQuery<Task> query = from(task);
        BooleanBuilder builder = new BooleanBuilder();
        if(CollectionUtils.isEmpty(taskIds)){
                builder.and(task.id.in(taskIds));
        }
        String orgId = statVQueryVo.getOrgId();
        if(StringUtils.hasText(orgId)){
            builder.and(task.createOrgId.eq(orgId));
        }
        LocalDateTime startLocalDate = statVQueryVo.getStartLocalDate();
        LocalDateTime endLocalDate = statVQueryVo.getEndLocalDate();
        if(startLocalDate!=null && endLocalDate!=null){
            builder.and(task.sendTime.between(startLocalDate,endLocalDate));
        }
        builder.and(task.delFlag.eq(DelFlagEnum.NORMAL.getCode()));
        query.where(builder).orderBy(task.createTime.desc());
        return findAll(query);
    }
}
