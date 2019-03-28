package com.taiji.cdp.base.sms.controller;

import com.taiji.cdp.base.sms.service.SmsTempService;
import com.taiji.cdp.base.sms.vo.SmsTempVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/smstemps")
public class SmsTempController {

    @Autowired
    private SmsTempService service;


    // 新增短信模板
    @PostMapping
    public ResultEntity addSmsTemplate(
            @Validated
            @NotNull(message = "SmsVo不能为null")
            @RequestBody SmsTempVo smsTempVo, OAuth2Authentication principal){
        service.create(smsTempVo,principal);
        return ResultUtils.success();
    }

    //修改短信模板
    @PutMapping(path = "{id}")
    public ResultEntity updateSmsTemplate(
            @NotNull(message = "SmsVo不能为null")
            @RequestBody SmsTempVo smsTempVo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id,
            OAuth2Authentication principal){
        service.update(smsTempVo,id,principal);
        return ResultUtils.success();
    }

    //获取单条短信模板
    @GetMapping(path = "{id}")
    public ResultEntity findSmsTemplateById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        SmsTempVo vo = service.findOne(id);
        return ResultUtils.success(vo);
    }

    //删除短信模板
    @DeleteMapping(path = "{id}")
    public ResultEntity deleteSmsTemplate(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id){
        service.deleteLogic(id);
        return ResultUtils.success();
    }

    //短信模板 --分页查询
    @PostMapping(path = "/search")
    public ResultEntity findSmsTemplates(@RequestBody Map<String,Object> map){
        if(map.containsKey("page")&&map.containsKey("size")){
            RestPageImpl<SmsTempVo> pageVo = service.findPage(map);
            return ResultUtils.success(pageVo);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

}
