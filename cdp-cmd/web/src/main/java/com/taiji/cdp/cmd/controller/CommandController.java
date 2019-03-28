package com.taiji.cdp.cmd.controller;

import com.taiji.cdp.cmd.service.CommandService;
import com.taiji.cdp.cmd.vo.CommandParamVo;
import com.taiji.cdp.cmd.vo.commandSaveVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/commands")
public class CommandController {

    CommandService service;

    /**
     * 新增舆情调控单信息
     *
     * @param vo        新增舆情调控单vo
     * @param principal 用户信息
     * @return CommandSaveVo
     */
    @PostMapping
    public ResultEntity addCommand(
            @Validated
            @NotNull(message = "ConsJudgeSaveVo 不能为null")
            @RequestBody commandSaveVo vo, OAuth2Authentication principal) {
        CommandParamVo result = service.addCommand(vo, principal);
        return ResultUtils.success(result);
    }

    /**
     * 导出舆情调控单信息
     *
     * @param cdId 调控单Id
     * @return List<ConsJudgeVo>
     */
    @GetMapping(path = "/export")
    public ResultEntity exportCommand(
            @NotEmpty(message = "cdId  不能为空字符串")
            @RequestParam String cdId, HttpServletRequest request, HttpServletResponse response) {
        try {
            service.exportCommand(cdId, request, response);
        } catch (IOException e) {
            return ResultUtils.fail(ResultCodeEnum.INTERNAL_ERROR,"导出舆情调控单失败！");
        }
        return ResultUtils.success();
    }

}
