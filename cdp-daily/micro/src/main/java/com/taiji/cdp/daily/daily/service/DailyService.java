package com.taiji.cdp.daily.daily.service;

import com.taiji.cdp.daily.daily.entity.Daily;
import com.taiji.cdp.daily.daily.repository.DailyRepository;
import com.taiji.cdp.daily.searchVo.DailyPageVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@AllArgsConstructor
public class DailyService extends BaseService<Daily, String> {

    DailyRepository repository;

    /**
     * 新增日报管理
     * @param entity 新增日报管理
     * @return Daily
     */
    public Daily create(Daily entity) {
        Assert.notNull(entity, "Daily 不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        if(entity.getStatus().isEmpty()){
            entity.setStatus(DelFlagEnum.DELETE.getCode());
        }
        Daily result = repository.save(entity);
        return result;
    }

    /**
     * 更新日报管理
     * @param entity 日报管理entity
     * @param id     日报管理Id
     * @return Daily
     */
    public Daily update(Daily entity, String id) {
        Assert.hasText(id, "id 不能为空字符串");
        Assert.notNull(entity, "Daily 不能为null");
        entity.setId(id);
        Daily result = repository.save(entity);
        return result;
    }

    /**
     * 根据id获取单条日报管理信息Vo
     * @param id 信息Id
     * @return Daily
     */
    public Daily findOne(String id) {
        Assert.hasText(id, "id 不能为空字符串");
        Daily result = repository.findOne(id);
        return result;
    }

    /**
     * 根据id逻辑删除单条日报管理信息
     */
    public void deleteLogic(String id, DelFlagEnum delFlagEnum){
        Assert.hasText(id,"id 不能为空字符串");
        Daily result = repository.findOne(id);
        repository.deleteLogic(result,delFlagEnum);
    }

    /**分页*/
    public Page<Daily> findPage(DailyPageVo dailyPageVo, Pageable pageable){
        Page<Daily> result = repository.findPage(dailyPageVo,pageable);
        return result;
    }



    /**
     * 查询所以日报管理信息
     */
    public List<Daily> findList(){
        return repository.findListAll();
    }

    /**
     * 发布日报管理信息
     */
    public void publish(String dailyId){
        Assert.hasText(dailyId, "dailyId 不能为空字符串");
        Daily entity = repository.findOne(dailyId);
        if(entity!=null){
            entity.setStatus("1");
            repository.save(entity);
        }
    }

}
