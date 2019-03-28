package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.taiji.cdp.report.common.enums.TaskReadFlagEnum;
import com.taiji.cdp.report.entity.QTaskExeOrg;
import com.taiji.cdp.report.entity.TaskExeOrg;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@Transactional
public class TaskExeOrgRepository extends BaseJpaRepository<TaskExeOrg,String>{

    @Transactional
    public TaskExeOrg create(TaskExeOrg entity){
        Assert.notNull(entity,"ConsInfo 不能为null");
        TaskExeOrg old = findByTaskIdAndOrgId(entity.getTaskId(),entity.getOrgId());
        if(null!=old){
            return old;
        }else{
            return super.save(entity);
        }
    }

    //避免重复入库
    private TaskExeOrg findByTaskIdAndOrgId(String taskId,String orgId){
        Assert.hasText(taskId,"taskId 不能为空字符串");
        Assert.hasText(orgId,"orgId 不能为空字符串");
        QTaskExeOrg taskExeOrg = QTaskExeOrg.taskExeOrg;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(taskExeOrg.orgId.eq(orgId));
        builder.and(taskExeOrg.taskId.eq(taskId));
        List<TaskExeOrg> list = findAll(builder);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }else{
            return null;
        }
    }

    //根据任务id获取所有负责单位
    public List<TaskExeOrg> findByTaskId(String taskId){
        Assert.hasText(taskId,"taskId 不能为空字符串");
        QTaskExeOrg taskExeOrg = QTaskExeOrg.taskExeOrg;
        return findAll(taskExeOrg.taskId.eq(taskId));
    }

    //根据任务id删除所有负责单位
    @Transactional
    public void deleteListByTaskId(String taskId){
        Assert.hasText(taskId,"taskId 不能为空字符串");
        QTaskExeOrg taskExeOrg = QTaskExeOrg.taskExeOrg;
        JPADeleteClause deleteClause = querydslDelete(taskExeOrg);
        deleteClause.where(taskExeOrg.taskId.eq(taskId)).execute();
    }

    //更新exeOrg已读状态
    @Transactional
    public void updateListByByRead(List<String> taskIds,String orgId){
        Assert.notEmpty(taskIds,"taskIds 不能为空");
        Assert.hasText(orgId,"orgId 不能为空字符串");
        QTaskExeOrg taskExeOrg = QTaskExeOrg.taskExeOrg;
        JPAUpdateClause updateClause = querydslUpdate(taskExeOrg);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(taskExeOrg.taskId.in(taskIds));
        builder.and(taskExeOrg.orgId.eq(orgId));
        builder.and(taskExeOrg.readFlag.eq(TaskReadFlagEnum.NOT_READ.getCode()));//未读的
        updateClause.set(taskExeOrg.readFlag,TaskReadFlagEnum.HAS_READ.getCode()).where(builder).execute();
    }


    /**
     * 统计下发给本单位的任务数量（本年度）
     */
    public List<TaskExeOrg> findStatTasks(String orgId){
        QTaskExeOrg taskExeOrg = QTaskExeOrg.taskExeOrg;
        JPQLQuery<TaskExeOrg> query = from(taskExeOrg);
        BooleanBuilder builder = new BooleanBuilder();
        if(StringUtils.hasText(orgId)){
            builder.and(taskExeOrg.orgId.eq(orgId));
        }
        query.where(builder);
        return findAll(query);
    }
}
