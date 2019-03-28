package com.taiji.cdp.report.mapper;

import com.taiji.cdp.report.entity.Task;
import com.taiji.cdp.report.entity.TaskReceive;
import com.taiji.cdp.report.vo.TaskVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper extends BaseMapper<Task,TaskVo>{

    default RestPageImpl<TaskVo> taskRecPageToTaskVoPage(Page<TaskReceive> entityPage, Pageable page)
    {
        if ( entityPage == null || page == null) {
            return null;
        }

        List<TaskReceive> content = entityPage.getContent();
        List<TaskVo> list = new ArrayList<>(content.size());

        for ( TaskReceive entity : content ) {
            Task task = entity.getTask();
            list.add( entityToVo(task) );
        }

        RestPageImpl<TaskVo> voPage = new RestPageImpl(list,page,entityPage.getTotalElements());

        return voPage;
    }
    default List<TaskVo> taskRecToTaskVo(List<TaskReceive> content)
    {
        if ( content == null) {
            return null;
        }

        List<TaskVo> list = new ArrayList<>(content.size());

        for ( TaskReceive entity : content ) {
            Task task = entity.getTask();
            list.add( entityToVo(task) );
        }


        return list;
    }

}
