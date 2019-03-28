package com.taiji.cdp.report.service;

import com.taiji.cdp.report.common.constant.DateUtil;
import com.taiji.cdp.report.common.constant.DownlodeImgUtils;
import com.taiji.cdp.report.common.constant.HttpClientUtils;
import com.taiji.cdp.report.feign.EvidenceClient;
import com.taiji.cdp.report.vo.AnalyzeInfoDTO;
import com.taiji.cdp.report.vo.EvidenceVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EvidenceService extends BaseService {

    @Autowired
    EvidenceClient client;

    /**
     * 取证存储url
     */
    @Value("${forensics.save.URL}")
    private String forensicsSaveURL;

    /**
     * 取证获取url
     */
    @Value("${forensics.get.URL}")
    private String forensicsGetURL;

    /**
     * 取证token值
     */
    @Value("${forensicsToken}")
    private String forensicsToken;

    /**
     * 本地存储路径
     */
    @Value("${localPath}")
    private String localPath;

    /**
     * 新增电子取证Vo
     *
     * @param vo 新增电子取证entity
     * @return EvidenceVo
     */
    public EvidenceVo addConsEvidence(EvidenceVo vo, OAuth2Authentication principal) {
        Assert.notNull(vo, "ConsJudgeInfo 不能为null");
        LinkedHashMap<String, Object> userMap = getUserMap(principal);
        vo.setEdUserName(userMap.get("username").toString());//取证人姓名
        vo.setEdUserId(userMap.get("id").toString()); //取证人id
        vo.setEdTime(LocalDateTime.now());//取证时间
        //调用第三方接口取证，先截图，然后获取返回值
        //第三方提供的接口访问token
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("token", forensicsToken);
        paramsMap.put("urladdr", vo.getInfoUrl());
        //调用第三方接口截图
        Map<String, String> photoMap = HttpClientUtils.httpPostWithForm(forensicsSaveURL, paramsMap);
        if ("1".equals(photoMap.get("code"))) {
            //code为1说明取证完成，
            try {
                //因为取证有延迟所以占用资源25s
                Thread.sleep(40000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //第三方提供的接口访问token
            Map<String, String> getMap = new HashMap<String, String>();
            getMap.put("token", forensicsToken);
            getMap.put("dataid", photoMap.get("urlId"));
            Map<String, String> dateMap = HttpClientUtils.httpPostWithForm(forensicsGetURL, getMap);
            if ("1".equals(dateMap.get("code"))) {
                String photoUrl = dateMap.get("pic");
                vo.setPhotoServerLocation(photoUrl);
                String fileName = getFileNameFromURL() + ".png";
                String photoLocation = localPath + "/" + DateUtil.dateD2String(new Date());
                Thread t = new Thread(new DownlodeImgUtils(photoUrl, fileName, photoLocation));
                t.setName("线程" + (vo.getInfoId()));
                t.start();
                vo.setPhotoLocation(photoLocation);
                ResponseEntity<EvidenceVo> result = client.create(vo);
                EvidenceVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
                return resultVo;
            }
        }
        return null;
    }

    /**
     * 根据id获取单条电子取证信息Vo
     *
     * @param id 信息Id
     * @return EvidenceVo
     */
    public EvidenceVo findEvidenceById(String id) {
        Assert.hasText(id, "id 不能为空字符串");
        ResponseEntity<EvidenceVo> result = client.findOne(id);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 根据舆情信息id获取单条电子取证信息Vo
     *
     * @param infoId 信息Id
     * @return EvidenceVo
     */
    public List<EvidenceVo> findByInfoId(String infoId) {
        Assert.hasText(infoId, "infoId 不能为空字符串");
        ResponseEntity<List<EvidenceVo>> result = client.findByJudgeId(infoId);
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }


    public static String getFileNameFromURL() {
        return UUID.randomUUID().toString();
    }

    /**
     * 解析URL
     *
     * @param url 信息Id
     * @return EvidenceVo
     */
    public AnalyzeInfoDTO parsingURL(String url) {
        Assert.hasText(url, "url地址不能为空字符串");
        AnalyzeInfoDTO vo = new AnalyzeInfoDTO();
        //第三方提供的接口访问token
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("token", forensicsToken);
        paramsMap.put("urladdr", url);
        //调用第三方接口截图
        Map<String, String> photoMap = HttpClientUtils.httpPostWithForm(forensicsSaveURL, paramsMap);
        if ("1".equals(photoMap.get("code"))) {
            //code为1说明取证完成，
            try {
                //因为取证有延迟所以占用资源25s
                Thread.sleep(40000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //第三方提供的接口访问token
            Map<String, String> getMap = new HashMap<String, String>();
            getMap.put("token", forensicsToken);
            getMap.put("dataid", photoMap.get("urlId"));
            getMap.put("dataCode","01010");
            Map<String, String> dateMap = HttpClientUtils.httpPostWithForm(forensicsGetURL, getMap);
            if ("1".equals(dateMap.get("code"))) {
                vo.setTitle(dateMap.get("qztitle"));
                vo.setContent(dateMap.get("qzcontent"));
            }
        }
        return ResponseEntityUtils.achieveResponseEntityBody(ResponseEntity.ok(vo));
    }
}
