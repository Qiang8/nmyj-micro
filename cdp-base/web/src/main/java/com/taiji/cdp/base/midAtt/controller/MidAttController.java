package com.taiji.cdp.base.midAtt.controller;

import com.taiji.cdp.base.midAtt.service.MidAttService;
import com.taiji.cdp.base.midAtt.vo.AttachmentVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/midfiles")
public class MidAttController {

    MidAttService service;

    /**
     * 根据业务实体ID获取附件对象列表
     * @param entityId 业务实体ID
     * @return
     */
    @GetMapping(path = "/getDocs")
    public ResultEntity getAttsByEntityId(
            @NotEmpty(message = "业务实体ID entityId 不能为空字符串")
            @RequestParam(value = "entityId") String entityId){
        List<AttachmentVo> list = service.getAttsByEntityId(entityId);
        return ResultUtils.success(list);
    }

}
