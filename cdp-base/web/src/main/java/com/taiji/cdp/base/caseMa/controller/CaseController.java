package com.taiji.cdp.base.caseMa.controller;
import com.taiji.cdp.base.caseMa.service.CaseService;
import com.taiji.cdp.base.caseMa.vo.CaseSaveVo;
import com.taiji.cdp.base.caseMa.vo.CaseVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @Auther: lpx
 * @Date: 2019-02-28 15:40
 * @Description: 案例管理信息类接口
 */
@Slf4j
@AllArgsConstructor
@RestController
public class CaseController {

    CaseService service;

    /**
     * 新增案例信息
     * @param vo
     *  @return ResultEntity
     * */
    @PostMapping(path = "/cases")
    public ResultEntity addCase(
            @Validated
        @NotNull(message = "CaseSaveVo 不能为null")
        @RequestBody CaseSaveVo vo, OAuth2Authentication principal){
            CaseVo result = service.addCaseVo(vo,principal);
            return ResultUtils.success(result);
    }

    /**
     * 修改案例信息
     * @param vo,id
     *  @return ResultEntity
     * */
    @PutMapping(path = "/cases/{id}")
    public ResultEntity updateCase(
            @Validated
            @NotNull(message = "CaseSaveVo 不能为null")
            @RequestBody CaseSaveVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id
            ,OAuth2Authentication principal){
        CaseVo result = service.update(vo,id,principal);
        return ResultUtils.success(result);
    }

    /**
     * 获取单条案例信息
     * @param id
     * @return ResultEntity
     */
    @GetMapping(path = "/cases/{id}")
    public ResultEntity findCaseById(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id){
        CaseVo result = service.finCaseById(id);
        return ResultUtils.success(result);
    }

    /**
     * 根据ID删除单条案例信息
     * @param id
     * @return ResultEntity
     */
    @DeleteMapping(path = "/cases/{id}")
    public ResultEntity deleteCase(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id){
        service.deleteById(id);
        return ResultUtils.success();
    }
    /**
     * 根据条件查询任务信息列表-分页  --发送方
     * @param map
     * {
    "title": "string",
    "startTimeStart": "2019-03-04T02:13:57.915Z",
    "startTimeEnd": "2019-03-04T02:13:57.915Z",
    "caseTypeIds": [
    "string"
    ],
    "page": 0,
    "size": 0
    }
     * @param principal 用户信息
     */
    @PostMapping(path = "/cases/search")
    public ResultEntity findCases(
            @RequestBody Map<String,Object> map, OAuth2Authentication principal){
        if(map.containsKey("page")&&map.containsKey("size")){
            RestPageImpl<CaseVo> result = service.findCases(map,principal);
            return ResultUtils.success(result);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }
}
