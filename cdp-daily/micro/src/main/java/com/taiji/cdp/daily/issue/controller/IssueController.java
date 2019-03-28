package com.taiji.cdp.daily.issue.controller;

import com.taiji.cdp.daily.feign.IIssueRestService;
import com.taiji.cdp.daily.issue.entity.Issue;
import com.taiji.cdp.daily.issue.mapper.AddAndUpdateIssueMapper;
import com.taiji.cdp.daily.issue.mapper.IssueMapper;
import com.taiji.cdp.daily.issue.service.IssueService;
import com.taiji.cdp.daily.vo.AddAndUpdateIssueVo;
import com.taiji.cdp.daily.searchVo.IssuePageVo;
import com.taiji.cdp.daily.vo.IssueVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 15:58
 **/
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/issue")
public class IssueController extends BaseController implements IIssueRestService {

    IssueService issueService;
    IssueMapper issueMapper;
    AddAndUpdateIssueMapper addAndUpdateIssueMapper;
    /**
     * 新增专刊
     * @param addAndUpdateIssueVO
     * @return
     */
    @Override
    public ResponseEntity<IssueVo> addIssue(
            @Validated@NotNull(message = "issueVo不能为null")
            @RequestBody AddAndUpdateIssueVo addAndUpdateIssueVO) {
        IssueVo issueVo = issueService.addIssue(addAndUpdateIssueVO);
        return ResponseEntity.ok(issueVo);
    }

    /**
     * 更新专刊信息
     * @param addAndUpdateIssueVO
     */
    @Override
    public ResponseEntity<IssueVo> updateIssue(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id,
            @Validated@NotNull(message = "issueVo不能为null")
            @RequestBody AddAndUpdateIssueVo addAndUpdateIssueVO) {
        Issue issue = issueMapper.voToEntity(addAndUpdateIssueVO.getIssue());
        issue.setId(id);
        IssueVo issueVo = issueService.updateIssue(issue);
        return ResponseEntity.ok(issueVo);
    }

    /**
     * 根据id查询专刊信息
     * @param
     * @return
     */
    @Override
    public ResponseEntity<AddAndUpdateIssueVo> findIssueById(
            @PathVariable("id")
            @NotEmpty(message = "id不能为空")String id) {
        AddAndUpdateIssueVo issueById = issueService.findIssueById(id);
        return ResponseEntity.ok(issueById);
    }

    /**
     * 根据id删除专刊信息
     * @param id
     */
    @Override
    public ResponseEntity<Void> deleteIssue(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        issueService.deleteIssue(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据标题查询专刊
     * @param issuePageVO
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<IssueVo>> findIssues(
            @Validated
            @NotNull(message = "issueSearchVO不能为null")
            @RequestBody IssuePageVo issuePageVO) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        int page = issuePageVO.getPage();
        int size = issuePageVO.getSize();
        Assert.notNull(page,"page 不能为null或空字符串!");
        Assert.notNull(size,"size 不能为null或空字符串!");
        params.add("page",page);
        params.add("size",size);
        Pageable pageable = PageUtils.getPageable(params);
        Page<Issue> issues = issueService.findIssues(issuePageVO,pageable);
        RestPageImpl<IssueVo> issueVos = issueMapper.entityPageToVoPage(issues, pageable);
        return ResponseEntity.ok(issueVos);
    }

    /**
     * 发布专刊，将专刊信息状态（0：未发布，1：已发布）
     * @param id
     */
    @Override
    public ResponseEntity<Void> publishIssue(
            @NotEmpty(message = "id不能为空")
            @RequestParam(value = "id") String id) {
        issueService.publishIssue(id);
        return ResponseEntity.ok().build();
    }
}