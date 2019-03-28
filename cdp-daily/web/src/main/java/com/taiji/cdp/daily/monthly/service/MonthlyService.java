package com.taiji.cdp.daily.monthly.service;

import com.taiji.base.file.service.FileEsService;
import com.taiji.base.file.vo.EsFileVo;
import com.taiji.cdp.base.midAtt.vo.AttachmentVo;
import com.taiji.cdp.daily.issue.feign.AttSimpleClient;
import com.taiji.cdp.daily.issue.service.BaseService;
import com.taiji.cdp.daily.monthly.feign.MidAttClient;
import com.taiji.cdp.daily.monthly.feign.MonthlyClient;
import com.taiji.cdp.daily.monthly.vo.MonthlyInfoVo;
import com.taiji.cdp.daily.searchVo.MonthlyPageVo;
import com.taiji.cdp.daily.vo.MonthlyVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Title:MonthlyClient.java</p >
 * <p>Description: 月报管理对外service层 service</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class MonthlyService extends BaseService {

    MonthlyClient client;

    MidAttClient midAttClient;
    AttSimpleClient attSimpleClient;

    FileEsService fileEsService;

    /**
     * 根据id获取一条记录
     *
     * @param id
     * @return
     */
    public MonthlyVo findById(String id) {
        Assert.hasText(id, "id不能为null值或空字符串。");
        ResponseEntity<MonthlyVo> result = client.find(id);
        MonthlyVo body = ResponseEntityUtils.achieveResponseEntityBody(result);
        return body;
    }

    /**
     * 查询分页列表
     *
     * @param pageVo
     * @return
     */
    public RestPageImpl<MonthlyVo> findByPages(MonthlyPageVo pageVo) throws Exception {
        EsFileVo vo = new EsFileVo();
        vo.setTitle(pageVo.getTitle());
        vo.setPage(pageVo.getPage());
        vo.setSize(pageVo.getSize());
        String result = fileEsService.esSelect(vo);
        //转对象
        List<String> list = fileEsService.esJsonStrToIds(result);
        if(!CollectionUtils.isEmpty(list)){
            pageVo.setIds(list);
            pageVo.setTitle(null);
        }
        ResponseEntity<RestPageImpl<MonthlyVo>> results = client.findPage(pageVo);
        return  ResponseEntityUtils.achieveResponseEntityBody(results);
    }

    /**
     * 新增月报信息
     *
     * @param monthlyInfoVo
     * @return
     */
    public void create(MonthlyInfoVo monthlyInfoVo, OAuth2Authentication principal) throws Exception{
        MonthlyVo vo = monthlyInfoVo.getMonthly();
        Assert.notNull(vo, "月报对象不能为null值。");
        vo.setDelFlag(DelFlagEnum.NORMAL.getCode());
        vo.setCreateTime(LocalDateTime.now());
        vo.setStatus(StatusEnum.DISABLED.getCode()); //新增数据初始值为未发布（禁用）
        MonthlyVo result = client.create(vo).getBody();
        saveMidAttSaveVo(result.getId(),monthlyInfoVo.getFileIds(),monthlyInfoVo.getFileDeleteIds());
        List<AttachmentVo> attByEntity = midAttClient.findAttsByEntityId(result.getId()).getBody();
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
        String createTime = result.getCreateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        esFileVo.setCreateTime(createTime);
        esFileVo.setId(result.getId());
        esFileVo.setContent(content);
        esFileVo.setTitle(result.getTitle());
        //组装数据并调用服务
        fileEsService.updateOrAdd(esFileVo,0);
    }

    /**
     * 更新MonthlyVo，MonthlyVo不能为空。
     *
     * @param monthlyInfoVo
     * @param id
     */
    public void update(MonthlyInfoVo monthlyInfoVo, String id,OAuth2Authentication principal) throws Exception {
        MonthlyVo vo = monthlyInfoVo.getMonthly();
        Assert.notNull(vo, "月报对象不能为null值。");
        Assert.hasText(id, "id不能为null或空字符串!");
        MonthlyVo result = client.update(vo, id).getBody();
        saveMidAttSaveVo(result.getId(),monthlyInfoVo.getFileIds(),monthlyInfoVo.getFileDeleteIds());

        List<AttachmentVo> attByEntity = midAttClient.findAttsByEntityId(result.getId()).getBody();
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
        fileEsService.updateOrAdd(esFileVo,1);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    public void delete(String id) throws Exception {
        Assert.hasText(id, "id不能为null或空字符串!");
        ResponseEntity<MonthlyVo> result = client.deleteLogic(id);
        //获取附件id
        ResponseEntity<List<AttachmentVo>> minAtt = midAttClient.findAttsByEntityId(id);
        AttachmentVo vo = minAtt.getBody().get(0);
        midAttClient.deleteByAttId(vo.getId());
        ResponseEntityUtils.achieveResponseEntityBody(result);
            fileEsService.delete(id);
    }

    /**
     * 发布月报
     *
     * @param id
     */
    public void publish(String id) {
        Assert.hasText(id, "id不能为null或空字符串!");
        ResponseEntity<MonthlyVo> result = client.publishInfo(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

}