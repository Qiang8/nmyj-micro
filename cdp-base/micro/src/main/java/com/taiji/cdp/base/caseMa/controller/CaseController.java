package com.taiji.cdp.base.caseMa.controller;

import com.taiji.cdp.base.caseMa.feign.ICaseInfoService;
import com.taiji.cdp.base.caseMa.vo.CaseVo;
import com.taiji.cdp.base.caseMa.entity.Case;
import com.taiji.cdp.base.caseMa.mapper.CaseMapper;
import com.taiji.cdp.base.caseMa.service.CaseService;
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

/**
 * 案例信息接口实现类controller
 * @author
 * @date
 */
@Slf4j
@RestController
@RequestMapping("api/cases")
@AllArgsConstructor
public class CaseController implements ICaseInfoService{

    CaseService caseService;
    CaseMapper caseMapper;

    /**
     * 新增案例信息vo
     *
     * @param vo
     * @return ResponseEntity<CaseVo>
     */
    @Override
    public ResponseEntity<CaseVo> create(
            @Validated
            @NotNull(message = "CaseVo 不能为null")
            @RequestBody CaseVo vo) {
        Case aCase = caseMapper.voToEntity(vo);
        Case result = caseService.create(aCase);
        CaseVo caseVo = caseMapper.entityToVo(result);
        return ResponseEntity.ok(caseVo);
    }
    /**
     * 查询单个案例信息vo
     *
     * @param id
     * @return ResponseEntity<CaseVo>
     */
    @Override
    public ResponseEntity<CaseVo> finCaseById(
            @NotEmpty(message = "id 不能为空")
            @PathVariable String id) {
        Case result = caseService.findOne(id);
        CaseVo caseVo = caseMapper.entityToVo(result);
        return ResponseEntity.ok(caseVo);
    }

    /**
     * 删除单个案例信息vo
     * @param id
     * @return ResponseEntity<CaseVo>
     */
    @Override
    public ResponseEntity<CaseVo> deleteLogic(
            @NotEmpty(message = "id 不能为空")
                    @PathVariable("id") String id) {
        caseService.deleteLogic(id, DelFlagEnum.DELETE);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改单个案例信息vo
     * @param vo,id
     * @param vo
     * @return ResponseEntity<CaseVo>
     */
    @Override
    public ResponseEntity<CaseVo> update(
            @Validated
            @NotNull(message = "CaseVo 不能为null")
            @RequestBody CaseVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        Case entity = caseMapper.voToEntity(vo);
        Case result = caseService.update(entity,id);
        CaseVo resultVo = caseMapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 案例信息分页查询
     * @param params
     * 参数;page,size
     * 参数：title(可选)、startTimeStart(首发时间开始)、startTimeEnd(上报时间结束)、caseTypeIds(案例类型,string[])
     * @return ResponseEntity<RestPageImpl<CaseVo>>
     *     不带取证信息
     */
    @Override
    public ResponseEntity<RestPageImpl<CaseVo>> findPage(@RequestParam MultiValueMap<String, Object> params) {
        Pageable pageable = PageUtils.getPageable(params);
        Page<Case> pageResult = caseService.findPage(params,pageable);
        RestPageImpl<CaseVo> pageVo = caseMapper.entityPageToVoPage(pageResult,pageable);
        return ResponseEntity.ok(pageVo);
    }
}
