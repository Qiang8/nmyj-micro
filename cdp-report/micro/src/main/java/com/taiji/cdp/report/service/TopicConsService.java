package com.taiji.cdp.report.service;

import com.taiji.cdp.report.entity.TopicCons;
import com.taiji.cdp.report.repository.TopicConsRepository;
import com.taiji.cdp.report.vo.TopicConsVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TopicConsService extends BaseService<TopicCons,String>{

    TopicConsRepository repository;

    //创建单个舆情、专题中间表记录
    public TopicCons createOne(TopicConsVo vo){
        TopicCons result = repository.create(vo);
        return result;
    }

    //批量创建舆情、专题中间表记录
    public List<TopicConsVo> createList(List<TopicConsVo> vos){
        if(!CollectionUtils.isEmpty(vos)){
            List<TopicConsVo> list = new ArrayList<>();
            for(TopicConsVo vo:vos){
                TopicCons result = createOne(vo);
                if(null!=result){
                    TopicConsVo temp = new TopicConsVo(result.getId(),result.getConsInfo().getId(),result.getTopic().getId(),result.getTopic().getName());
                    list.add(temp);
                }
            }
            return list;
        }else{
            return null;
        }
    }



    /**
     *  根据重点专题ID查询舆情信息列表-分页（重点专题使用）
     *  @param params 参数：topicId,page,size
     */
    public Page<TopicCons> findPageByTopic(MultiValueMap<String, Object> params, Pageable pageable){
        Page<TopicCons> result = repository.findPageByTopic(params,pageable);
        return result;
    }

    /**
     * 根据舆情信息查询重点专题List-不分页
     * @param params 参数：infoId
     */
    public List<TopicCons> findListByInfo(MultiValueMap<String, Object> params){
        List<TopicCons> result = repository.findListByInfo(params);
        return result;
    }

}
