package com.taiji.cdp.base.sms.controller;

import com.taiji.cdp.base.sms.entity.Sms;
import com.taiji.cdp.base.sms.feign.ISmsService;
import com.taiji.cdp.base.sms.mapper.SmsMapper;
import com.taiji.cdp.base.sms.searchVo.SmsListVo;
import com.taiji.cdp.base.sms.searchVo.SmsPageVo;
import com.taiji.cdp.base.sms.service.SmsService;
import com.taiji.cdp.base.sms.vo.SmsVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/smses")
public class SmsController implements ISmsService {
    SmsService smsService;
    SmsMapper smsMapper;

    /**
     * 不分页
     */
    @Override
    public ResponseEntity<List<SmsVo>> findList(@RequestBody SmsListVo smsListVo){
        List<Sms> list = smsService.findList(smsListVo);
        List<SmsVo> voList = smsMapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 分页
     */
    @Override
    public ResponseEntity<RestPageImpl<SmsVo>> findPage(@RequestBody SmsPageVo smsPageVo){
        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",smsPageVo.getPage());
        map.add("size",smsPageVo.getSize());
        Pageable page = PageUtils.getPageable(map);

        Page<Sms> pageResult = smsService.findPage(smsPageVo,page);
        RestPageImpl<SmsVo> voPage = smsMapper.entityPageToVoPage(pageResult,page);
        return new ResponseEntity<>(voPage,HttpStatus.OK);
    }

    /**
     * 新增
     */
    @Override
    public ResponseEntity<SmsVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody SmsVo vo){
        Sms entity = smsMapper.voToEntity(vo);
        Sms result = smsService.create(entity);
        SmsVo resultVo = smsMapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 更新
     */
    @Override
    public ResponseEntity<SmsVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody SmsVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id){
        Sms entity = smsMapper.voToEntity(vo);
        Sms result = smsService.update(entity);
        SmsVo resultVo = smsMapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     *获取单条
     */
    @Override
    public ResponseEntity<SmsVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id){
        Sms result = smsService.findOne(id);
        SmsVo resultVo = smsMapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 删除
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id){
        smsService.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
