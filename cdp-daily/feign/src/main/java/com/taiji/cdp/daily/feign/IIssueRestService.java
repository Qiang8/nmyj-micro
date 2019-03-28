package com.taiji.cdp.daily.feign;

import com.taiji.cdp.daily.vo.AddAndUpdateIssueVo;
import com.taiji.cdp.daily.searchVo.IssuePageVo;
import com.taiji.cdp.daily.vo.IssueVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 14:52
 **/
@FeignClient(value = "micro-issue")
public interface IIssueRestService {
    /**
     * 新增专刊
     * @param addAndUpdateIssueVO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/addIssue")
    @ResponseBody
    ResponseEntity<IssueVo> addIssue(@RequestBody AddAndUpdateIssueVo addAndUpdateIssueVO);

    /**
     * 更新专刊信息
     * @param addAndUpdateIssueVO
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/updateIssue/{id}")
    @ResponseBody
    ResponseEntity<IssueVo> updateIssue(@PathVariable(value = "id")String id,@RequestBody AddAndUpdateIssueVo addAndUpdateIssueVO);

    /**
     * 根据id查询专刊信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findIssueById/{id}")
    @ResponseBody
    ResponseEntity<AddAndUpdateIssueVo> findIssueById(@PathVariable("id") String id);

    /**
     * 根据id删除专刊信息
     * @param id
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/deleteIssue/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteIssue(@PathVariable("id")String id);

    /**
     * 根据名称检索专刊信息
     * @param params
     */
    @RequestMapping(method = RequestMethod.POST, path = "/search/findIssues")
    @ResponseBody
    ResponseEntity<RestPageImpl<IssueVo>> findIssues(@RequestBody IssuePageVo issuePageVO);

    /**
     * 根据id修改专刊信息为发布状态
     * @param id
     */
    @RequestMapping(method = RequestMethod.POST, path = "/publish")
    @ResponseBody
    ResponseEntity<Void> publishIssue(@RequestParam("id")String id);
}
