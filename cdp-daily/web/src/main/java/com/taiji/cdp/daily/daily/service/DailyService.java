package com.taiji.cdp.daily.daily.service;

import com.taiji.base.file.service.FileEsService;
import com.taiji.base.file.vo.EsFileVo;
import com.taiji.cdp.base.midAtt.vo.AttachmentVo;
import com.taiji.cdp.daily.daily.feign.DailyClient;
import com.taiji.cdp.daily.daily.vo.DailySaveVo;
import com.taiji.cdp.daily.issue.feign.AttSimpleClient;
import com.taiji.cdp.daily.issue.service.BaseService;
import com.taiji.cdp.daily.monthly.feign.MidAttClient;
import com.taiji.cdp.daily.searchVo.DailyPageVo;
import com.taiji.cdp.daily.vo.DailyVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DailyService extends BaseService {

    DailyClient client;
    MidAttClient midAttClient;
    AttSimpleClient attSimpleClient;

    FileEsService fileEsService;
    /**
     * 新增日报管理
     */
    public void create(DailySaveVo saveVo, OAuth2Authentication principal) throws Exception {
        DailyVo vo = saveVo.getDaily();
        DailyVo result = client.create(vo).getBody();
        //附件
        saveMidAttSaveVo(result.getId(),saveVo.getFileIds(),saveVo.getFileDeleteIds());
        //获得当前实体对于的文件信息
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

    /**修改日报管理*/
    public void update(DailySaveVo saveVo,String id,OAuth2Authentication principal) throws Exception{
        LinkedHashMap<String,Object> userMap = getUserMap(principal);
        LocalDateTime now = getCurrrentTime();
        String account = userMap.get("username").toString();
        DailyVo vo = saveVo.getDaily();
        vo.setUpdateBy(account);
        vo.setUpdateTime(now);
        DailyVo result = client.update(vo, id).getBody();
        //附件
        saveMidAttSaveVo(result.getId(),saveVo.getFileIds(),saveVo.getFileDeleteIds());
        List<AttachmentVo> attByEntity = midAttClient.findAttsByEntityId(id).getBody();
        String content = "";
        if(!CollectionUtils.isEmpty(attByEntity)){
            List<String> list =
                    attByEntity.stream().map(AttachmentVo::getLocation).collect(Collectors.toList());
            //根据文件路径集合获得文件内容集合（.doc和.docx）
            List<String> listResponseEntity = attSimpleClient.readDocsContent(list).getBody();
            content = String.join("", listResponseEntity);
        }
        //获得当前实体对于的文件信息
        EsFileVo esFileVo = new EsFileVo();//需要组装数据的vo
        esFileVo.setAccount(account);
        String createTime = result.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        esFileVo.setCreateTime(createTime);
        esFileVo.setId(result.getId());
        esFileVo.setContent(content);
        esFileVo.setTitle(result.getTitle());
        //组装数据并调用服务
        fileEsService.updateOrAdd(esFileVo,1);
    }

    /**获取单条日报信息*/
    public DailyVo findOne(String id){
        ResponseEntity<DailyVo> result = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    //删除日报信息
    public void delete(String id) throws Exception{
        client.deleteLogic(id);
        fileEsService.delete(id);
    }

    /**
     * 获取日报信息分页list（先查询大数据）
     */
    public RestPageImpl<DailyVo> findPage(DailyPageVo dailyPageVo) throws Exception{

        //组装数据查询大数据中标题和内容符合查询条件的数据
        EsFileVo vo = new EsFileVo();
        vo.setTitle(dailyPageVo.getTitle());
        vo.setPage(dailyPageVo.getPage());
        vo.setSize(dailyPageVo.getSize());
        String result = fileEsService.esSelect(vo);
        //转对象
        List<String> list = fileEsService.esJsonStrToIds(result);
        if(!CollectionUtils.isEmpty(list)){
            dailyPageVo.setIds(list);
            dailyPageVo.setTitle(null);
        }
        ResponseEntity<RestPageImpl<DailyVo>> resultVo = client.findPage(dailyPageVo);
        return ResponseEntityUtils.achieveResponseEntityBody(resultVo);
    }

    /**查询所以日报信息*/
    public List<DailyVo> findList(){
        ResponseEntity<List<DailyVo>> result = client.findList();
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**日报信息发布*/
    public void publish(String dailyId){
        client.publish(dailyId);
    }

}
