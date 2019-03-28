package com.taiji.cdp.cmd.controller;

import com.taiji.cdp.cmd.entity.Treat;
import com.taiji.cdp.cmd.feign.ITreatService;
import com.taiji.cdp.cmd.mapper.TreatMapper;
import com.taiji.cdp.cmd.service.TreatService;
import com.taiji.cdp.cmd.vo.TreatVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: nmyj-micro
 * @Description:
 * @Author: WangJian(wangjiand @ mail.taiji.com.cn)
 * @Date: 2019/3/5 11:34
 **/

@Slf4j
@RestController
@RequestMapping("/api/treat")
@AllArgsConstructor
public class TreatController implements ITreatService {
    TreatService treatService;
    TreatMapper treatMapper;

    /**
     * 记录调控单办理情况
     *
     * @param treatVo
     * @return
     */
    @Override
    public ResponseEntity<Void> addTreatment(@Validated
                                             @NotNull(message = "treatVo 不能为null")
                                             @RequestBody TreatVo treatVo) {

        Treat treat = treatMapper.voToEntity(treatVo);
        treatService.addTreatment(treat);
        return ResponseEntity.ok().build();
    }

    /**
     * 更新调控单办理情况
     *
     * @param treatVo
     * @param id      办理Id
     * @return
     */
    @Override
    public ResponseEntity<Void> updateTreatment( @Validated
                                                 @NotNull(message = "treatVo 不能为null")
                                                 @RequestBody TreatVo treatVo,
                                                 @NotEmpty(message = "id 不能为空字符串")
                                                 @PathVariable("id")String id) {
        Treat treat = treatMapper.voToEntity(treatVo);
        treatService.updateTreatment(treat,id);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据Id获取单条调控单办理信息
     *
     * @param id
     * @return 查询结果
     */
    @Override
    public ResponseEntity<TreatVo> findOneTreatment(@NotEmpty(message = "id 不能为空字符串")
                                                    @PathVariable("id")String id) {
        Treat treat = treatService.findOneTreatment(id);
        TreatVo treatVo = treatMapper.entityToVo(treat);
        return ResponseEntity.ok(treatVo);
    }

    /**
     * 根据Id删除单条调控单办理信息
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteOneTreatment(@NotEmpty(message = "id 不能为空字符串")
                                                   @PathVariable("id")String id) {
        treatService.deleteOneTreatment(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询办理情况列表-不分页
     *
     * @return 查询结果集
     */
    @Override
    public ResponseEntity<List<TreatVo>> searchAllTreatment() {
        List<Treat> treats = treatService.searchAllTreatment();
        List<TreatVo> treatVos = treatMapper.entityListToVoList(treats);
        return ResponseEntity.ok(treatVos);
    }
}
