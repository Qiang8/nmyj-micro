package com.taiji.cdp.base.midAtt.controller;

import com.taiji.cdp.base.midAtt.entity.Attachment;
import com.taiji.cdp.base.midAtt.entity.MidAtt;
import com.taiji.cdp.base.midAtt.feign.IMidAttRestService;
import com.taiji.cdp.base.midAtt.mapper.AttachmentMapper;
import com.taiji.cdp.base.midAtt.mapper.MidAttMapper;
import com.taiji.cdp.base.midAtt.service.MidAttService;
import com.taiji.cdp.base.midAtt.vo.AttachmentVo;
import com.taiji.cdp.base.midAtt.vo.MidAttSaveVo;
import com.taiji.cdp.base.midAtt.vo.MidAttVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 附件中间表对象接口服务实现类
 * @author qizhijie-pc
 * @date 2019年1月7日17:55:11
 */
@Slf4j
@RestController
@RequestMapping("/api/midFiles")
@AllArgsConstructor
public class MidAttController implements IMidAttRestService{

    MidAttService service;
    MidAttMapper mapper;
    AttachmentMapper attachmentMapper;


    /**
     * 创建单个中间表对象vo
     *
     * @param vo 中间表对象
     * @return ResponseEntity<MidAttVo>
     */
    @Override
    public ResponseEntity<MidAttVo> createOne(
            @Validated
            @NotNull(message = "MidAttVo 不能为null")
            @RequestBody MidAttVo vo) {
        MidAtt entity = mapper.voToEntity(vo);
        MidAtt result = service.createOne(entity);
        MidAttVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 创建多个中间表对象vo
     *
     * @param vos
     * @return ResponseEntity<MidAttVo>
     */
    @Override
    public ResponseEntity<List<MidAttVo>> createList(
            @Validated
            @NotNull(message = "vos 不能为null")
            @RequestBody List<MidAttVo> vos) {
        List<MidAtt> entities = mapper.voListToEntityList(vos);
        List<MidAtt> list = service.createList(entities);
        List<MidAttVo> resultVos = mapper.entityListToVoList(list);
        return ResponseEntity.ok(resultVos);
    }

    /**
     * 通过附件id删除单个中间表对象vo
     *
     * @param attId 附件id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteByAttId(
            @NotEmpty(message = "attId 不能为空字符串")
            @RequestParam(value = "attId") String attId) {
        service.deleteByAttId(attId);
        return ResponseEntity.ok().build();
    }

    /**
     * 通过附件ids删除多个中间表对象vo
     *
     * @param attIds 附件id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteByAttIds(
            @NotNull(message = "attIds 不能为null")
            @RequestBody List<String> attIds) {
        service.deleteByAttIds(attIds);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据业务实体id获取所有附件对象vo
     *
     * @param entityId 业务实体id
     * @return ResponseEntity<List < AttachmentVo>>
     */
    @Override
    public ResponseEntity<List<AttachmentVo>> findAttsByEntityId(
            @NotEmpty(message = "entityId 不能为空字符串")
            @PathVariable(value = "entityId") String entityId) {
        List<Attachment> list = service.findAttsByEntityId(entityId);
        List<AttachmentVo> resultList = attachmentMapper.entityListToVoList(list);
        return ResponseEntity.ok(resultList);
    }

    /**
     * 业务提交保存时调用，保存附件信息
     *
     * @param midAttSaveVo 业务带附件提交对象
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> saveMidAtts(
            @NotNull(message = "midAttSaveVo 不能为null")
            @Validated
            @RequestBody MidAttSaveVo midAttSaveVo) {
        service.saveMidAtts(midAttSaveVo);
        return ResponseEntity.ok().build();
    }
}
