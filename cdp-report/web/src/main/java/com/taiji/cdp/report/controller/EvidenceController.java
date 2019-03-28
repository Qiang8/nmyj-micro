package com.taiji.cdp.report.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taiji.cdp.report.service.EvidenceService;
import com.taiji.cdp.report.vo.AnalyzeInfoDTO;
import com.taiji.cdp.report.vo.EvidenceVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class EvidenceController {
    EvidenceService service;

    /**
     * 新增电子取证Vo
     *
     * @param vo 新增电子取证entity
     * @return EvidenceVo
     */
    @PostMapping(path = "/evidences")
    public ResultEntity addConsInfo(
            @Validated
            @NotNull(message = "EvidenceVo 不能为null")
            @RequestBody EvidenceVo vo, OAuth2Authentication principal) {
        EvidenceVo result = service.addConsEvidence(vo, principal);
        if (null == result) {
            return ResultUtils.fail(ResultCodeEnum.OPERATE_FAIL);
        }
        return ResultUtils.success(result);
    }

    /**
     * 根据id获取单条电子取证信息Vo
     *
     * @param id 信息Id
     * @return EvidenceVo
     */
    @GetMapping(path = "/evidences/{id}")
    public ResultEntity findConsInfoById(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        EvidenceVo result = service.findEvidenceById(id);
        return ResultUtils.success(result);
    }

    /**
     * 根据舆情信息id获取单条电子取证信息Vo
     *
     * @param infoId 信息Id
     * @return EvidenceVo
     */
    @GetMapping(path = "/consEvidences")
    public ResultEntity findByJudgeId(
            @NotEmpty(message = "id 不能为空字符串")
            @RequestParam("infoId") String infoId) {
        List<EvidenceVo> result = service.findByInfoId(infoId);
        return ResultUtils.success(result);
    }

    /**
     * 解析URL
     *
     * @param infourl 信息Id
     * @return ParsingURLVo
     */
    @PostMapping(path = "/reportAnalyzes")
    public ResultEntity parsingURL(
            @NotEmpty(message = "infoUrl  不能为空字符串")
            @RequestBody String infourl) {
        JSONObject object = JSON.parseObject(infourl);
        String url = (String) object.get("infoUrl");
        AnalyzeInfoDTO result = service.parsingURL(url);
        return ResultUtils.success(result);
    }

}
