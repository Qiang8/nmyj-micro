package com.taiji.cdp.cmd.service;

import com.taiji.cdp.cmd.entity.Treat;
import com.taiji.cdp.cmd.repository.TreatRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @program: nmyj-micro
 * @Description:
 * @Author: WangJian(wangjiand@mail.taiji.com.cn)
 * @Date: 2019/3/5 11:40
 **/

@Repository
@AllArgsConstructor
public class TreatService  extends BaseService<Treat, String> {
    TreatRepository treatRepository;

    /**
     * 记录调控单办理情况
     * @param treat
     */
    public void addTreatment(Treat treat) {
        Assert.notNull(treat,"treat 不能为null");
        treat.setDelFlag(DelFlagEnum.NORMAL.getCode());
        treatRepository.save(treat);
    }

    /**
     * 更新调控单办理情况
     * @param treat
     * @param id
     */
    public void updateTreatment(Treat treat, String id) {
        Assert.notNull(treat,"treat 不能为null");
        Assert.hasText(id,"id 不能为空字符串");
        //treat.setDelFlag(DelFlagEnum.NORMAL.getCode());
        treat.setId(id);
        treatRepository.save(treat);
    }

    /**
     * 根据Id获取单条调控单办理信息
     * @param id
     * @return 查询结果
     */
    public Treat findOneTreatment(String id) {
        Assert.hasText(id,"id 不能为空字符串");
        Treat treat = treatRepository.findOne(id);
        return treat;
    }

    /**
     * 根据Id删除单条调控单办理信息
     * @param id
     */
    public void deleteOneTreatment(String id) {
        Assert.hasText(id,"id 不能为空字符串");
        Treat treat = treatRepository.findOne(id);
        treatRepository.deleteLogic(treat,DelFlagEnum.DELETE);
    }

    /**
     * 查询办理情况列表-不分页
     * @return 查询结果集
     */
    public List<Treat> searchAllTreatment() {
        List<Treat> treats = treatRepository.searchAllTreatment();
        return treats;
    }
}
