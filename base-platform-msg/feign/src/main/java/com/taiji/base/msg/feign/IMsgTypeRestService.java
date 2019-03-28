package com.taiji.base.msg.feign;

import com.taiji.base.msg.vo.MsgTypeVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>Title:IMsgTypeRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 9:57</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-msg-type")
public interface IMsgTypeRestService {

    /**
     *  根据code获取一条MsgTypeVo记录
     *
     * @return ResponseEntity<MsgTypeVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findOne")
    @ResponseBody
    ResponseEntity<MsgTypeVo> findOneByCode(@RequestParam(name = "code") String code);

    /**
     * 根据参数获取MsgTypeVo多条记录。
     * <p>
     *
     * params参数key为moduleName（可选），type（可选）。
     *
     * @param params 查询参数集合
     *
     * @return ResponseEntity<List<MsgTypeVo>>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<MsgTypeVo>> findList(@RequestParam MultiValueMap<String, Object> params);
}
