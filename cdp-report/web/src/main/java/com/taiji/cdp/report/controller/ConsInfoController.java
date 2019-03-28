package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.common.enums.ConsInfoStatusEnum;
import com.taiji.cdp.report.service.ConsInfoService;
import com.taiji.cdp.report.vo.ConsInfoSaveVo;
import com.taiji.cdp.report.vo.ConsInfoVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/reports")
public class ConsInfoController {

    ConsInfoService service;

    /**
     * 舆情信息填报（自治区用户保存时默认为1已上报状态，其他单位用户保存时默认为0未上报状态）
     * @param vo
     * @param principal 用户信息
     */
    @PostMapping
    public ResultEntity addConsInfo(
            @Validated
            @NotNull(message = "ConsInfoVo 不能为null")
            @RequestBody ConsInfoSaveVo vo,OAuth2Authentication principal){
        ConsInfoVo result = service.addConsInfo(vo,principal);
        return ResultUtils.success(result);
    }

    /**
     * 修改舆情信息
     * @param vo
     * @param id
     * @param principal 用户信息
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateConsInfo(
            @Validated
            @NotNull(message = "ConsInfoVo 不能为null")
            @RequestBody ConsInfoSaveVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id
            ,OAuth2Authentication principal){
        ConsInfoVo result = service.updateConsInfo(vo,id,principal);
        return ResultUtils.success(result);
    }

    /**
     * 获取单条舆情信息
     * @param id
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findConsInfoById(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        ConsInfoVo result = service.findConsInfoById(id);
        return ResultUtils.success(result);
    }

    /**
     * 删除舆情信息
     * @param id
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteConsInfo(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id")String id){
        service.deleteConsInfo(id);
        return ResultUtils.success();
    }

    /**
     * 舆情信息上报，更新ConsInfo表的status和receiveOrgCode字段
     * @param receiveOrgCode 接收单位编码
     */
    @PostMapping(path = "/send")
    public ResultEntity sendInfo(
            @NotEmpty(message = "infoId 不能为空字符串")
            @RequestParam("infoId") String infoId,
            @RequestParam("receiveOrgCode")String receiveOrgCode,OAuth2Authentication principal){
        service.sendInfo(infoId,receiveOrgCode,principal);
        return ResultUtils.success();
    }

    /**
     * 根据条件查询舆情信息列表-分页（盟市）
     * @param map 查询条件
     * 参数：title(可选)、reportTimeStart(上报时间开始))、reportTimeEnd(上报时间结束)、createTimeStart(创建时间开始)
     *      、createTimeEnd(创建时间结束)、statuses(舆情信息状态,string[])、orgId(创建部门orgId)
     */
    @PostMapping(path = "/search")
    public ResultEntity findConsInfos(
            @RequestBody Map<String,Object> map,OAuth2Authentication principal){
        if(map.containsKey("page") && map.containsKey("size")){
            RestPageImpl<ConsInfoVo> result = service.findConsInfos(map,principal);
            return ResultUtils.success(result);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 根据条件查询舆情信息列表-分页（自治区）
     * @param map 查询条件
     * 参数：title(可选)、reportTimeStart(上报时间开始)、reportTimeEnd(上报时间结束)、statuses(舆情信息状态,string[])
     */
    @PostMapping(path = "/rec/search")
    public ResultEntity findConsInfoVos(
            @RequestBody Map<String,Object> map){
        if(map.containsKey("page") && map.containsKey("size")){
            RestPageImpl<ConsInfoVo> result = service.findConsInfoVos(map);
            return ResultUtils.success(result);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 根据条件查询舆情信息列表-不分页
     * @param map 查询条件
     * 参数：title(可选)、reportTimeStart(上报时间开始)、reportTimeEnd(上报时间结束)、statuses(舆情信息状态,string[])
     */
    @PostMapping(path = "/rec/searchAll")
    public ResultEntity findConsInfosAll(
            @RequestBody Map<String,Object> map){
        List<ConsInfoVo> result = service.findConsInfosAll(map);
        return ResultUtils.success(result);
    }

    /**
     * 当舆情进行上报、研判、短信发送、审批通过、审批退回、生成调控单、舆情完成（结束）等操作时，通过前台传参，
     * 后台将CR_CONS_INFO表中的status更新为对应的舆情状态，
     * 并且将last_deal_user更新为当前登录用户名称、last_deal_time更新为系统当前操作时间
     * @param infoId 舆情ID
     * @param status 舆情信息状态：1已上报 2已研判 3短信上报 4已审批通过 5已退回 6处置中 7已完成 8已忽略
     */
    @PostMapping(path = "/rec/updateStatus")
    public ResultEntity updateConsStatus(
            @NotEmpty(message = "infoId 不能为空字符串")
            @RequestParam("infoId") String infoId,
            @NotEmpty(message = "status 不能为空字符串")
            @RequestParam("status") String status,OAuth2Authentication principal){
        if(null!=ConsInfoStatusEnum.codeOf(status)){
            service.updateConsStatus(infoId,status,principal);
            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR,"舆情信息状态 参数传输异常");
        }
    }

    /**
     * 根据舆情ID数组查询舆情信息列表-不分页（交接班使用）
     * @param infoIds 舆情ID数组
     */
    @PostMapping(path = "/list")
    public ResultEntity findConsInfosByIds(
            @NotBlank
            @RequestBody List<String> infoIds){
        List<ConsInfoVo> result = service.findConsInfosByIds(infoIds);
        return ResultUtils.success(result);
    }

    /**
     * 根据重点专题ID查询舆情信息列表-分页（重点专题使用）
     * @param map 查询条件
     * 参数：topicId,page,size
     */
    @PostMapping(path = "/searchTopic")
    public ResultEntity findTopicConsInfos(
            @RequestBody Map<String,Object> map){
        if(map.containsKey("page") && map.containsKey("size")){
            RestPageImpl<ConsInfoVo> result = service.findTopicConsInfos(map);
            return ResultUtils.success(result);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

}
