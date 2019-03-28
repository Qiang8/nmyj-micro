package com.taiji.cdp.daily.issue.service;

import com.taiji.base.file.service.FileEsService;
import com.taiji.base.file.vo.EsFileVo;
import com.taiji.cdp.base.midAtt.vo.AttachmentVo;
import com.taiji.cdp.daily.issue.feign.AttSimpleClient;
import com.taiji.cdp.daily.issue.feign.IssueClient;
import com.taiji.cdp.daily.monthly.feign.MidAttClient;
import com.taiji.cdp.daily.vo.AddAndUpdateIssueVo;
import com.taiji.cdp.daily.searchVo.IssuePageVo;
import com.taiji.cdp.daily.vo.IssueVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 14:44
 **/
@Service
@AllArgsConstructor
public class IssueService extends BaseService {

    IssueClient issueClient;
    MidAttClient midAttClient;
    AttSimpleClient attSimpleClient;

    FileEsService fileEsService;
    /**
     * 新增专刊
     * @param addAndUpdateIssueVO
     */
    public void addIssue(AddAndUpdateIssueVo addAndUpdateIssueVO, OAuth2Authentication principal)  throws Exception {
        Assert.notNull(addAndUpdateIssueVO.getIssue(), "issue不能为null值。");
        ResponseEntity<IssueVo> issueVoResponseEntity = issueClient.addIssue(addAndUpdateIssueVO);
        IssueVo result = ResponseEntityUtils.achieveResponseEntityBody(issueVoResponseEntity);
        //保存该专刊的附件信息
        saveMidAttSaveVo(result.getId(),addAndUpdateIssueVO.getFileIds(),addAndUpdateIssueVO.getFileDeleteIds());
        List<AttachmentVo> attByEntity = midAttClient.findAttsByEntityId(result.getId()).getBody();
        String content = "";
        if(!CollectionUtils.isEmpty(attByEntity)){
            List<String> list =
                    attByEntity.stream().map(AttachmentVo::getLocation).collect(Collectors.toList());
            //根据文件路径集合获得文件内容集合（.doc和.docx）
            List<String> listResponseEntity = attSimpleClient.readDocsContent(list).getBody();
            content = String.join("", listResponseEntity);
        }
        //需要组装数据的vo
        EsFileVo esFileVo = new EsFileVo();
        esFileVo.setAccount(getUserMap(principal).get("username").toString());
        String createTime = result.getCreateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        esFileVo.setCreateTime(createTime);
        esFileVo.setId(result.getId());
        esFileVo.setContent(content);
        esFileVo.setTitle(result.getTitle());
        //组装数据并调用服务
        String orAdd = fileEsService.updateOrAdd(esFileVo, 0);
    }



    /**
     * 更新专刊信息
     * @param id
     * @param addAndUpdateIssueVO
     */
    public void updateIssue(String id,
                            AddAndUpdateIssueVo addAndUpdateIssueVO,
                            OAuth2Authentication principal) throws Exception{
        Assert.notNull(addAndUpdateIssueVO.getIssue(), "MenuVo不能为null值。");
        ResponseEntity<IssueVo> issueVoResponseEntity = issueClient.updateIssue(id, addAndUpdateIssueVO);
        IssueVo result = ResponseEntityUtils.achieveResponseEntityBody(issueVoResponseEntity);
        this.saveMidAttSaveVo(id,addAndUpdateIssueVO.getFileIds(),addAndUpdateIssueVO.getFileDeleteIds());
        //获得当前实体对于的文件信息
        List<AttachmentVo> attByEntity = midAttClient.findAttsByEntityId(id).getBody();
        String content = "";
        if(!CollectionUtils.isEmpty(attByEntity)){
            List<String> list =
                    attByEntity.stream().map(AttachmentVo::getLocation).collect(Collectors.toList());
            //根据文件路径集合获得文件内容集合（.doc和.docx）
            List<String> listResponseEntity = attSimpleClient.readDocsContent(list).getBody();
            content = String.join("", listResponseEntity);
        }
        EsFileVo esFileVo = new EsFileVo();//需要组装数据的vo
        LinkedHashMap<String,Object> userMap = getUserMap(principal);
        esFileVo.setAccount(userMap.get("username").toString());
        String createTime = result.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        esFileVo.setCreateTime(createTime);
        esFileVo.setId(result.getId());
        esFileVo.setContent(content);
        esFileVo.setTitle(result.getTitle());
        //组装数据并调用服务
        String orAdd = fileEsService.updateOrAdd(esFileVo, 1);
    }

    /**
     * 根据Id查询专刊信息
     * @param id
     * @return
     */
    public AddAndUpdateIssueVo findIssueById(String id) {
        Assert.notNull(id, "id不能为null值。");
        ResponseEntity<AddAndUpdateIssueVo> issueById = issueClient.findIssueById(id);
        AddAndUpdateIssueVo addAndUpdateIssueVO = ResponseEntityUtils.achieveResponseEntityBody(issueById);
        return addAndUpdateIssueVO;

    }

    /**
     * 根据id删除专刊信息
     * @param id
     */
    public void deleteIssue(String id) throws Exception{
        Assert.notNull(id, "id不能为null值。");
        issueClient.deleteIssue(id);
        fileEsService.delete(id);
    }

    /**
     * 根据名称检索专刊信息
     * @param issuePageVO
     */
    public RestPageImpl<IssueVo> findIssues(IssuePageVo issuePageVO) throws Exception {
        //组装数据查询大数据中标题和内容符合查询条件的数据
        EsFileVo vo = new EsFileVo();
        vo.setTitle(issuePageVO.getTitle());
        vo.setPage(issuePageVO.getPage());
        vo.setSize(issuePageVO.getSize());
        String result = fileEsService.esSelect(vo);
        //转对象
        List<String> list = fileEsService.esJsonStrToIds(result);
        if(!CollectionUtils.isEmpty(list)){
            issuePageVO.setIds(list);
            issuePageVO.setTitle(null);
        }
        ResponseEntity<RestPageImpl<IssueVo>> resultVo = issueClient.findIssues(issuePageVO);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**
     * 根据id专刊信息为发布状态
     * @param id
     */
    public void publishIssue(String id) {
        Assert.notNull(id, "id不能为null值。");
        issueClient.publishIssue(id);
    }
}