package com.taiji.base.msg.controller;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.msg.service.MsgNoticeRecordService;
import com.taiji.base.msg.vo.MsgNoticeRecordVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <p>Title:NoticeController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 14:06</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/noticeRecords")
public class NoticeRecordController extends BaseController{

    MsgNoticeRecordService recordService;

    /**
     * 根据当前用户账号，获取通知消息列表-分页
     *
     * @param paramsMap
     *    key值包括
     *        msgType 消息类型
     *        readFlag 读取标识  0：未读，1：已读
     *       page+size 分页信息
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findRecords(OAuth2Authentication principal,
                                    @RequestBody Map<String, Object> paramsMap){
        LinkedHashMap<String,Object> principalMap = SecurityUtils.getPrincipalMap(principal);
        if(paramsMap.containsKey("page") && paramsMap.containsKey("size")){
            String userId = (String)principalMap.get("id");
            paramsMap.put("userId",userId);

            RestPageImpl<MsgNoticeRecordVo> pageList = recordService.findRecords(paramsMap);

            return ResultUtils.success(pageList);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 根据当前用户账号，获取通知消息列表-不分页
     *
     * @param paramsMap
     *  key值包括
     *        msgType 消息类型
     *        readFlag 读取标识  0：未读，1：已读
     *
     * @return
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findAllRecords(OAuth2Authentication principal,
                        @RequestBody Map<String, Object> paramsMap){

        LinkedHashMap<String,Object> principalMap = SecurityUtils.getPrincipalMap(principal);
        String userId = (String)principalMap.get("id");
        paramsMap.put("userId",userId);

        List<MsgNoticeRecordVo> allList = recordService.findNoticeList(paramsMap);

        return ResultUtils.success(allList);

    }

    /**
     * 消息清理
     * @param paramsMap
     *         key  array  recordIds
     * @return
     */
    @PostMapping(path = "/clear")
    public ResultEntity noticeClear(@RequestBody Map<String, Object> paramsMap){

        if (paramsMap.containsKey("recordIds")) {

            List<String> recordIds = (List<String>) paramsMap.get("recordIds");
            recordService.updateRecords(recordIds);

            return ResultUtils.success();
        } else {
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }
}
