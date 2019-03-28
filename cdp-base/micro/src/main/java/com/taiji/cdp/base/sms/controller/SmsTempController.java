package com.taiji.cdp.base.sms.controller;

import com.taiji.cdp.base.sms.entity.SmsTemp;
import com.taiji.cdp.base.sms.feign.ISmsTempRestService;
import com.taiji.cdp.base.sms.mapper.SmsTempMapper;
import com.taiji.cdp.base.sms.service.SmsTempService;
import com.taiji.cdp.base.sms.vo.SmsTempVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/smstemps")
public class SmsTempController implements ISmsTempRestService{

    SmsTempService service;
    SmsTempMapper mapper;

    /**
     * 新增短信模板vo
     *
     * @param vo 短信模板vo
     * @return ResponseEntity<SmsTempVo>
     */
    @Override
    public ResponseEntity<SmsTempVo> create(
            @NotNull(message = "SmsTempVo 不能为null")
            @Validated
            @RequestBody SmsTempVo vo) {
        SmsTemp entity = mapper.voToEntity(vo);
        SmsTemp result = service.create(entity);
        SmsTempVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 修改短信模板vo
     *
     * @param vo 短信模板vo
     * @param id 短信模板vo id
     * @return ResponseEntity<SmsTempVo>
     */
    @Override
    public ResponseEntity<SmsTempVo> update(
            @NotNull(message = "SmsTempVo 不能为null")
            @Validated
            @RequestBody SmsTempVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        SmsTemp entity = mapper.voToEntity(vo);
        SmsTemp result = service.update(entity,id);
        SmsTempVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取单个短信模板vo
     *
     * @param id 短信模板vo id
     * @return ResponseEntity<SmsTempVo>
     */
    @Override
    public ResponseEntity<SmsTempVo> findOne(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        SmsTemp result = service.findOne(id);
        SmsTempVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 逻辑删除短信模板vo
     *
     * @param id 短信模板vo id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据条件查询短信模板列表  --不分页查询
     *
     * @param params 参数：name,content
     * @return ResponseEntity<List < SmsTempVo>>
     */
    @Override
    public ResponseEntity<List<SmsTempVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        List<SmsTemp> results = service.findList(params);
        List<SmsTempVo> resultsVo = mapper.entityListToVoList(results);
        return ResponseEntity.ok(resultsVo);
    }

    /**
     * 根据条件查询短信模板列表 --分页查询
     *
     * @param params 参数：page,size,name,content
     * @return ResponseEntity<RestPageImpl < SmsTempVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<SmsTempVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<SmsTemp> results = service.findPage(params,pageable);
        RestPageImpl<SmsTempVo> resultsVo = mapper.entityPageToVoPage(results,pageable);
        return ResponseEntity.ok(resultsVo);
    }
}
