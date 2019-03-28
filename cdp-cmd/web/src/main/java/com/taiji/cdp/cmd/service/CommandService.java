package com.taiji.cdp.cmd.service;

import com.taiji.cdp.cmd.feign.CommandClient;
import com.taiji.cdp.cmd.feign.ExeorgClient;
import com.taiji.cdp.cmd.util.ExportUtil;
import com.taiji.cdp.cmd.vo.CommandParamVo;
import com.taiji.cdp.cmd.vo.CommandVo;
import com.taiji.cdp.cmd.vo.ExeorgVo;
import com.taiji.cdp.cmd.vo.commandSaveVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 舆情调控单service
 */
@Service
public class CommandService extends BaseService {

    @Autowired
    CommandClient commandClient;
    @Autowired
    ExeorgClient exeorgClient;

    /**
     * 取证存储url
     */
    @Value("${command.name}")
    private String fileFixName;

    /**
     * 文件存储url
     */
    @Value("${command.local}")
    private String fileLocal;


    /**
     * 新增舆情调控单Vo
     *
     * @param saveVo CommandVo
     * @return ResponseEntity<CommandVo>
     */
    public CommandParamVo addCommand(commandSaveVo saveVo, OAuth2Authentication principal) {
        Assert.notNull(saveVo, "commandSaveVo 不能为null");
        CommandVo vo = getCommandVo(saveVo);
        List<String> orgIds = saveVo.getOrgIds();
        LinkedHashMap<String, Object> userMap = getUserMap(principal);
        String account = userMap.get("username").toString();
        String creatOrgId = userMap.get("orgId").toString();
        vo.setCreateBy(account);
        vo.setCreateOrgId(creatOrgId);
        ResponseEntity<CommandVo> result = commandClient.create(vo);
        if (HttpStatus.OK.value() == result.getStatusCode().value() && orgIds != null && orgIds.size() > 0) {
            CommandVo commandVo = result.getBody();
            String cdId = commandVo.getId();
            LocalDateTime time = commandVo.getCreateTime();
            List<ExeorgVo> vos = new ArrayList<>();
            for (String org : orgIds) {
                ExeorgVo exeorgVo = new ExeorgVo();
                exeorgVo.setCdId(cdId);
                exeorgVo.setOrgId(org);
                exeorgVo.setSendTime(time);
                vos.add(exeorgVo);
            }
            exeorgClient.createList(vos);
        }
        CommandVo commandVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        CommandParamVo paramVo = getCommandParamVo(commandVo);
        return paramVo;
    }

    /**
     * 导出舆情调控单信息
     */
    public void exportCommand(String cdId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Assert.notNull(cdId, "cdId 不能为null");
        ResponseEntity<CommandVo> responseEntity = commandClient.findOne(cdId);
        CommandVo vo = responseEntity.getBody();
        if (null != vo) {
            Map<String, Object> params = new HashMap<>();
            params.put("title", vo.getTitle());
            params.put("startTime", vo.getStartTime());
            params.put("startWebsite", vo.getStartWebsite());
            params.put("transmission", vo.getTransmission());
            params.put("judgeOpinion", vo.getJudgeOpinion());
            params.put("centerOpinion", vo.getCenterOpinion());
            params.put("leaderOpinion", vo.getLeaderOpinion());
//            params.put("transactOpinion", "");
//            params.put("disposalSituation", "");
            String fileName = fileFixName + localDateTimeToString(vo.getCreateTime()) + ".docx";
            ExportUtil.exportWord(convertTemplatePath("static/wordTemp/infoCommandTemp.docx"),  //导出word模板位置
                    fileLocal  //临时文件夹地址
                    , fileName //导出文件名
                    , params  //替换参数 ：详细参数在 InfoCommandVo类中
                    , request, response);
        }
    }


    /**
     * save vo 转正常vo
     */
    private CommandVo getCommandVo(commandSaveVo saveVo) {
        CommandVo vo = new CommandVo();
        List ids = null;
        CommandParamVo paramVo = saveVo.getCommand();
        //页面传过来的为list，代码转为string入库。
        if (null != paramVo) {
            ids = paramVo.getHandleTypeIds();
            StringBuilder stringBuilder = new StringBuilder("");
            if (null != ids) {
                if (ids.size() == 1) {
                    vo.setHandleTypeIds(ids.get(0).toString());
                } else {
                    for (int i = 0; i < ids.size(); i++) {
                        stringBuilder.append(ids.get(i));
                        stringBuilder.append(",");
                    }
                }
            }
            vo.setHandleTypeIds(stringBuilder.toString());
            vo.setInfoId(paramVo.getInfoId());
            vo.setTitle(paramVo.getTitle());
            vo.setStartTime(paramVo.getStartTime());
            vo.setStartWebsite(paramVo.getStartWebsite());
            vo.setTransmission(paramVo.getTransmission());
            vo.setJudgeOpinion(paramVo.getJudgeOpinion());
            vo.setCenterOpinion(paramVo.getCenterOpinion());
            vo.setLeaderOpinion(paramVo.getLeaderOpinion());
            vo.setTransactOpinion(paramVo.getTransactOpinion());
            vo.setDisposalSituation(paramVo.getDisposalSituation());
            vo.setHandleFlag(paramVo.getHandleFlag());
            vo.setId(paramVo.getId());
            vo.setDelFlag(DelFlagEnum.NORMAL.getCode());
        }
        return vo;
    }

    /**
     * 正常 vo 转savevo
     */
    private CommandParamVo getCommandParamVo(CommandVo commandVo) {
        CommandParamVo vo = new CommandParamVo();
        String ids = commandVo.getHandleTypeIds();
        List handleTypeIds = new ArrayList();
        if (null != ids && !ids.isEmpty()) {
            String[] idArr = ids.split(",");
            for (int i = 0; i < idArr.length; i++) {
                handleTypeIds.add(idArr[i]);
            }
        }
        vo.setHandleTypeIds(handleTypeIds);
        vo.setInfoId(commandVo.getInfoId());
        vo.setTitle(commandVo.getTitle());
        vo.setStartTime(commandVo.getStartTime());
        vo.setStartWebsite(commandVo.getStartWebsite());
        vo.setTransmission(commandVo.getTransmission());
        vo.setJudgeOpinion(commandVo.getJudgeOpinion());
        vo.setCenterOpinion(commandVo.getCenterOpinion());
        vo.setLeaderOpinion(commandVo.getLeaderOpinion());
        vo.setTransactOpinion(commandVo.getTransactOpinion());
        vo.setDisposalSituation(commandVo.getDisposalSituation());
        vo.setHandleFlag(commandVo.getHandleFlag());
        vo.setId(commandVo.getId());
        return vo;
    }

    private String localDateTimeToString(LocalDateTime time) {
        if (null == time) {
            return "";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String localTime = df.format(time);
        return localTime;
    }

    /**
     * 获取模板文件路径
     */
    private String convertTemplatePath(String path) throws IOException {
        //如果是windows 则直接返回
        if (System.getProperties().getProperty("os.name").contains("Windows")) {
            return path;
        }
        Resource resource = new ClassPathResource(path);
        FileOutputStream fileOutputStream = null;
        // 将模版文件写入到 tomcat临时目录
        String folder = System.getProperty("catalina.home");
        File tempFile = new File(folder + File.separator + path);
        // 文件存在时 不再写入
        if (tempFile.exists()) {
            return tempFile.getPath();
        }
        File parentFile = tempFile.getParentFile();
        // 判断父文件夹是否存在
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(resource.getInputStream());
            fileOutputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[10240];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return tempFile.getPath();
    }

}
