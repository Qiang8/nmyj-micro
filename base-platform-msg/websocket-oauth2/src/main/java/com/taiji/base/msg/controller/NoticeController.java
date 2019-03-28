package com.taiji.base.msg.controller;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.msg.service.MsgNoticeService;
import com.taiji.base.msg.vo.MsgNoticeVo;
import com.taiji.base.msg.vo.Receiver;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/notices")
public class NoticeController extends BaseController{

    MsgNoticeService noticeService;

    /**   后台消息发送调用接口，发送消息格式如下：
     {
     "title":"title",
     "sendOrg":"sendOrg",
     "orgName":"orgName",
     "msgContent":"msgContentAAAAAA",
     "msgType":"1",
     "typeName":"test-websocket",
     "extra":"extra object info（json format）",
     "icon":"icon",
     "path":"path",
     "entityId":"1",
     "receivers":[
     {
     "id":"1",
     "name":"god",
     "deptId":"deptId",
     "deptName":"deptName",
     "readFlag":"",
     "readTime":""
     }
     ]
     }
     * @param msgNoticeVo
     * @return
     */
    @PostMapping
    public ResultEntity addNotice(@RequestBody MsgNoticeVo msgNoticeVo){

        noticeService.create(msgNoticeVo);

        return ResultUtils.success();
    }

    /**
     * 获取当前用户id或当前用户所在机构的通知发送列表-分页
     *
     * @param paramsMap
     *    key值包括
     *       查找类型type 0：用户id,1：机构id
     *       page+size 分页信息
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findNotices(OAuth2Authentication principal,
                                    @RequestBody Map<String, Object> paramsMap){
        LinkedHashMap<String,Object> principalMap = SecurityUtils.getPrincipalMap(principal);
        if(paramsMap.containsKey("page")
                && paramsMap.containsKey("size")
                && paramsMap.containsKey("type")){

            String type= (String) paramsMap.get("type");
            if("0".equals(type)){
                String account = (String)principalMap.get("username");
                paramsMap.put("account",account);
            }else{
                String orgId = (String) principalMap.get("orgId");
                paramsMap.put("orgId",orgId);
            }
            RestPageImpl<MsgNoticeVo> pageList = noticeService.findNotices(paramsMap);

            return ResultUtils.success(pageList);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 获取消息读取信息
     * @param noticeId
     * @param  readFlag
     * @return
     */
    @GetMapping(path = "/records/{id}")
    public ResultEntity findNoticeRecords(@NotEmpty(message = "id不能为空")
                                       @PathVariable(name = "id") String noticeId,
                                       @RequestParam(name = "readFlag",required = false) String readFlag){

        List<Receiver> allList = noticeService.findNoticeRecords(noticeId,readFlag);

        return ResultUtils.success(allList);
    }


}
