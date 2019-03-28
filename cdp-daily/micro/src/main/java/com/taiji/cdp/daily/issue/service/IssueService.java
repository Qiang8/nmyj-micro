package com.taiji.cdp.daily.issue.service;

import com.taiji.cdp.daily.common.enums.IssueDelFlag;
import com.taiji.cdp.daily.common.enums.IssueStatus;
import com.taiji.cdp.daily.issue.entity.Issue;
import com.taiji.cdp.daily.issue.mapper.IssueMapper;
import com.taiji.cdp.daily.issue.repository.IssueRepository;
import com.taiji.cdp.daily.vo.AddAndUpdateIssueVo;
import com.taiji.cdp.daily.searchVo.IssuePageVo;
import com.taiji.cdp.daily.vo.IssueVo;
import com.taiji.micro.common.enums.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 16:01
 **/
@Service
@AllArgsConstructor
public class IssueService {

    IssueRepository issueRepository;
    IssueMapper issueMapper;

    /**
     * 新增专刊信息
     * @param addAndUpdateIssueVO
     */
    public IssueVo addIssue(AddAndUpdateIssueVo addAndUpdateIssueVO) {
        Assert.notNull(addAndUpdateIssueVO,"addAndUpdateIssueVO不能为null");
        Issue issue = issueMapper.voToEntity(addAndUpdateIssueVO.getIssue());
        //新增的专刊默认状态为：有效
        issue.setDelFlag(IssueDelFlag.ISSUE_EFFECTIVE.getStatus());
        issue.setStatus(StatusEnum.DISABLED.getCode()); //新增数据初始值为未发布（禁用）
        Issue save = issueRepository.save(issue);
        IssueVo issueVo = issueMapper.entityToVo(save);
        return issueVo;
    }

    /**
     * 更新专刊信息
     * @param issue
     */
    public IssueVo updateIssue(Issue issue) {
        Assert.notNull(issue,"issue不能为null");
        issue.setDelFlag(IssueDelFlag.ISSUE_EFFECTIVE.getStatus());
        Issue save = issueRepository.save(issue);
        IssueVo issueVo = issueMapper.entityToVo(save);
        return issueVo;
    }

    /**
     * 根据id查询专刊信息
     * @param id
     * @return
     */
    public AddAndUpdateIssueVo findIssueById(String id) {
        Assert.hasText(id,"id 不能为空字符串");
        IssueVo issueVo = issueMapper.entityToVo(issueRepository.findOne(id));
        AddAndUpdateIssueVo addAndUpdateIssueVO = new AddAndUpdateIssueVo();
        addAndUpdateIssueVO.setIssue(issueVo);
        return addAndUpdateIssueVO;
    }
    /**
     * 根据id删除专刊信息
     * @param id
     * @return
     */
    public void deleteIssue(String id) {
        Assert.hasText(id,"id 不能为空字符串");
        issueRepository.delete(id);
    }

    /**
     * 根据专刊名称检索专刊信息
     * @param params
     * @param pageable
     * @return
     */
    public Page<Issue> findIssues(IssuePageVo issuePageVO, Pageable pageable) {
        return issueRepository.findIssues(issuePageVO, pageable);
    }

    /**
     * 根据id修改专刊信息为发布状态
     * @param id
     */
    public void publishIssue(String id) {
        Assert.hasText(id,"id 不能为空字符串");
        Issue issue = issueRepository.findOne(id);
        issue.setStatus(IssueStatus.RELEASE_STATUS.getStatus());
        issueRepository.save(issue);
    }

}