package com.taiji.cdp.daily.issue.controller;

import com.taiji.cdp.daily.issue.service.IssueService;
import com.taiji.cdp.daily.vo.AddAndUpdateIssueVo;
import com.taiji.cdp.daily.searchVo.IssuePageVo;
import com.taiji.cdp.daily.vo.IssueVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 14:29
 **/
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/issues")
public class IssueController extends BaseController{
    IssueService issueService;
    /**
     * 新增专刊信息
     * @param addAndUpdateIssueVO 专刊vo
     * @return ResultEntity
     */
    @PostMapping
    public ResultEntity addIssue(
            @RequestBody
            @NotNull(message = "addAndUpdateIssueVO不能为null") AddAndUpdateIssueVo addAndUpdateIssueVO,
            OAuth2Authentication principal) throws Exception{
            issueService.addIssue(addAndUpdateIssueVO,principal);
            return ResultUtils.success();
    }

    /**
     * 修改专刊信息
     * @param id 需要修改的专刊的唯一主键
     * @param addAndUpdateIssueVO 专刊vo
     * @return ResultEntity
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateIssue(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id")String id,
            @NotNull(message = "addAndUpdateIssueVO不能为null")
            @RequestBody AddAndUpdateIssueVo addAndUpdateIssueVO,
            OAuth2Authentication principal) throws Exception{
            issueService.updateIssue(id,addAndUpdateIssueVO,principal);
            return ResultUtils.success();
    }

    /**
     * 获取单条专刊信息
     * @param id 需要查询的专刊的唯一主键
     * @return ResultEntity
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findIssueById(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id){
            AddAndUpdateIssueVo addAndUpdateIssueVO = issueService.findIssueById(id);
            return ResultUtils.success(addAndUpdateIssueVO.getIssue());
    }

    /**
     * 删除专刊信息
     * @param id 需要删除的专刊的唯一主键
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteIssue(
            @NotEmpty(message = "id不能为空")
            @PathVariable(name = "id") String id) throws Exception{
            issueService.deleteIssue(id);
            return ResultUtils.success();
    }

    /**
     * 根据条件查询专刊信息列表-分页（暂时先按标题查询，后需改为与大数据平台对接进行全文检索）
     * @param issuePageVO 专刊vo
     * @return ResultEntity
     */
    @PostMapping(path = "/search")
    public ResultEntity findIssues(
            @NotNull(message = "issueSearchVO不能为空")
            @RequestBody IssuePageVo issuePageVO) throws Exception{
            RestPageImpl<IssueVo> pageList =  issueService.findIssues(issuePageVO);
            return ResultUtils.success(pageList);
    }

    /**
     * 专刊信息发布，后台将该专刊状态置为已发布状态（0:未发布，1:已发布）
     * @param id 需要修改的专刊的唯一主键
     * @return ResultEntity
     */
    @PutMapping(path = "/publish")
    public ResultEntity publishIssue(
            @NotEmpty(message = "id不能为空")
            @RequestParam(name = "id") String id){
        issueService.publishIssue(id);
        return ResultUtils.success();

    }
}