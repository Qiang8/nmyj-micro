package com.taiji.cdp.report.controller;

import com.taiji.cdp.report.service.ConsJudgeService;
import com.taiji.cdp.report.vo.ConsJudgeSaveVo;
import com.taiji.cdp.report.vo.ConsJudgeVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class ConsJudgeController {

    ConsJudgeService service;

    /**
     * 新增研判信息Vo
     *
     * @param vo        新增研判信息vo
     * @param principal 用户信息
     * @return ConsJudgeSaveVo
     */
    @PostMapping(path = "/judges")
    public ResultEntity addConsInfo(
            @Validated
            @NotNull(message = "ConsJudgeSaveVo 不能为null")
            @RequestBody ConsJudgeSaveVo vo, OAuth2Authentication principal) {
        ConsJudgeSaveVo result = service.addConsJudge(vo, principal);
        return ResultUtils.success(result);
    }

    /**
     * 修改舆情信息
     *
     * @param vo
     * @param id
     * @param principal 用户信息
     */
    @PutMapping(path = "/judges/{id}")
    public ResultEntity updateConsInfo(
            @Validated
            @NotNull(message = "ConsJudgeSaveVo 不能为null")
            @RequestBody ConsJudgeSaveVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id
            , OAuth2Authentication principal) {
        ConsJudgeSaveVo result = service.updateConsJudge(vo, id, principal);
        return ResultUtils.success(result);
    }

    /**
     * 根据id获取单条研判信息Vo
     *
     * @param id 信息Id
     * @return ConsJudgeVo
     */
    @GetMapping(path = "/judges/{id}")
    public ResultEntity findConsInfoById(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable("id") String id) {
        ConsJudgeSaveVo result = service.findConsJudgeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 根据舆情信息id获取单条研判信息Vo
     *
     * @param infoId 信息Id
     * @return List<ConsJudgeVo>
     */
    @GetMapping(path = "/consJudges")
    public ResultEntity findByJudgeId(
            @NotEmpty(message = "infoId 不能为空字符串")
            @RequestParam String infoId) {
        List<ConsJudgeSaveVo> list = service.findByInfoId(infoId);
        ConsJudgeSaveVo result = null;
        if(!CollectionUtils.isEmpty(list)){
            result = list.get(0);
        }
        return ResultUtils.success(result);
    }

}
