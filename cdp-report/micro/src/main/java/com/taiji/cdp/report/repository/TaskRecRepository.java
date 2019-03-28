package com.taiji.cdp.report.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.taiji.cdp.report.common.enums.TaskReadFlagEnum;
import com.taiji.cdp.report.common.global.StatGlobal;
import com.taiji.cdp.report.entity.QTaskReceive;
import com.taiji.cdp.report.entity.Task;
import com.taiji.cdp.report.entity.TaskReceive;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.repository.BaseJpaRepository;
import com.taiji.micro.common.repository.UtilsRepository;
import com.taiji.micro.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class TaskRecRepository extends BaseJpaRepository<TaskReceive,String> {

    @Autowired
    UtilsRepository utilsRepository;

    //根据 orgId和 taskId获取接收者
    public List<TaskReceive> findRecsByOrgIdAndTaskId(String orgId, String taskId,String readFlag){
        Assert.hasText(orgId,"orgId 不能为空字符串");
        Assert.hasText(taskId,"taskId 不能为空字符串");
        QTaskReceive taskReceive = QTaskReceive.taskReceive;
        JPQLQuery<TaskReceive> query = from(taskReceive);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(taskReceive.orgId.eq(orgId));
        builder.and(taskReceive.task.id.eq(taskId));
        if(!StringUtils.isEmpty(readFlag)){
            builder.and(taskReceive.readFlag.eq(readFlag));
        }
        query.where(builder).orderBy(taskReceive.sendTime.desc());
        return findAll(query);
    }

    /**
     * 更新接收人对象 已读状态
     * @param receiveId 接收人id
     * @param taskIds 任务id
     */
    @Transactional
    public void updateTasksByRead(List<String> taskIds,String receiveId){
        Assert.notEmpty(taskIds,"taskIds 不能为空");
        Assert.hasText(receiveId,"receiveId 不能为空字符串");
        QTaskReceive taskReceive = QTaskReceive.taskReceive;
        JPAUpdateClause updateClause = querydslUpdate(taskReceive);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(taskReceive.task.id.in(taskIds));
        builder.and(taskReceive.receiverId.eq(receiveId));
        builder.and(taskReceive.readFlag.eq(TaskReadFlagEnum.NOT_READ.getCode()));//未读的
        updateClause.set(taskReceive.readFlag,TaskReadFlagEnum.HAS_READ.getCode())
                .set(taskReceive.readTime,utilsRepository.now()).where(builder).execute();
    }

    //分页查询-- 接收方
    //参数：infoTitle(舆情标题),title(任务标题),sendTimeStart(发送开始时间),sendTimeEnd(发送结束时间),
    //      readFlag(已读标识：0否1是),receiveId(接收用户id)
    public Page<TaskReceive> findPage(MultiValueMap<String, Object> params, Pageable pageable){
        QTaskReceive taskReceive = QTaskReceive.taskReceive;
        JPQLQuery<TaskReceive> query = from(taskReceive);
        BooleanBuilder builder = new BooleanBuilder();
        if(params.containsKey("infoTitle")){
            String infoTitle = params.getFirst("infoTitle").toString();
            if(!StringUtils.isEmpty(infoTitle)){
                builder.and(taskReceive.task.infoTitle.contains(infoTitle));
            }
        }
        if(params.containsKey("title")){
            String title = params.getFirst("title").toString();
            if(!StringUtils.isEmpty(title)){
                builder.and(taskReceive.task.title.contains(title));
            }
        }
        if(params.containsKey("sendTimeStart")){
            LocalDateTime sendTimeStart = DateUtil.strToLocalDateTime(params.getFirst("sendTimeStart").toString());
            if(null!=sendTimeStart){
                builder.and(taskReceive.task.sendTime.after(sendTimeStart));
            }
        }
        if(params.containsKey("sendTimeEnd")){
            LocalDateTime sendTimeEnd = DateUtil.strToLocalDateTime(params.getFirst("sendTimeEnd").toString());
            if(null!=sendTimeEnd){
                builder.and(taskReceive.task.sendTime.before(sendTimeEnd));
            }
        }

        if(params.containsKey("readFlag")){
            String readFlag = params.getFirst("readFlag").toString();
            if(!StringUtils.isEmpty(readFlag)){
                builder.and(taskReceive.readFlag.eq(readFlag));
            }
        }
        if(params.containsKey("receiveId")){
            String receiveId = params.getFirst("receiveId").toString();
            if(!StringUtils.isEmpty(receiveId)){
                builder.and(taskReceive.receiverId.eq(receiveId));
            }
        }

        builder.and(taskReceive.task.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(TaskReceive.class,taskReceive.task))
                .where(builder)
                .orderBy(taskReceive.task.sendTime.desc());
        return findAll(query,pageable);
    }
    //分页查询-- 接收方（孙毅添加）
    //      readFlag(已读标识：0否1是),receiveId(接收用户id)
    // tagFlag 为0表示未接收 1表示接收
    public List<TaskReceive> searchAllStat(MultiValueMap<String, Object> params){
        QTaskReceive taskReceive = QTaskReceive.taskReceive;
        JPQLQuery<TaskReceive> query = from(taskReceive);
        BooleanBuilder builder = new BooleanBuilder();
        if(params.containsKey("infoTitle")){
            String infoTitle = params.getFirst("infoTitle").toString();
            if(!StringUtils.isEmpty(infoTitle)){
                builder.and(taskReceive.task.infoTitle.contains(infoTitle));
            }
        }
        if(params.containsKey("title")){
            String title = params.getFirst("title").toString();
            if(!StringUtils.isEmpty(title)){
                builder.and(taskReceive.task.title.contains(title));
            }
        }
        if(params.containsKey("sendTimeStart")){
            LocalDateTime sendTimeStart = DateUtil.strToLocalDateTime(params.getFirst("sendTimeStart").toString());
            if(null!=sendTimeStart){
                builder.and(taskReceive.task.sendTime.after(sendTimeStart));
            }
        }
        if(params.containsKey("sendTimeEnd")){
            LocalDateTime sendTimeEnd = DateUtil.strToLocalDateTime(params.getFirst("sendTimeEnd").toString());
            if(null!=sendTimeEnd){
                builder.and(taskReceive.task.sendTime.before(sendTimeEnd));
            }
        }
        if(params.containsKey("receiveId")){
            String receiveId = params.getFirst("receiveId").toString();
            if(!StringUtils.isEmpty(receiveId)){
                builder.and(taskReceive.receiverId.eq(receiveId));
            }
        }
        if(params.containsKey("orgId")){
            String orgId = params.getFirst("orgId").toString();
            if(!StringUtils.isEmpty(orgId)){
                builder.and(taskReceive.orgId.eq(orgId));
            }
        }
        if(params.containsKey("tagFlag")){
            String tagFlag = params.getFirst("tagFlag").toString();
            if(!StringUtils.isEmpty(tagFlag)){
                builder.and(taskReceive.readFlag.eq(tagFlag));
            }
        }



        builder.and(taskReceive.task.delFlag.eq(DelFlagEnum.NORMAL.getCode()));

        query.select(Projections.bean(TaskReceive.class,taskReceive.task))
                .where(builder)
                .orderBy(taskReceive.task.sendTime.desc());
        return findAll(query);
    }
}
