package com.taiji.base.msg.controller;

import com.taiji.base.msg.entity.MsgType;
import com.taiji.base.msg.feign.IMsgTypeRestService;
import com.taiji.base.msg.mapper.MsgTypeMapper;
import com.taiji.base.msg.service.MsgTypeService;
import com.taiji.base.msg.vo.MsgTypeVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>Title:MsgTypeController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/30 18:22</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/msg/type")
@AllArgsConstructor
public class MsgTypeController extends BaseController implements IMsgTypeRestService {

    MsgTypeService service;
    MsgTypeMapper mapper;

    /**
     * 根据code获取一条MsgTypeVo记录
     *
     * @param code
     * @return ResponseEntity<MsgTypeVo>
     */
    @Override
    public ResponseEntity<MsgTypeVo> findOneByCode(@RequestParam(name = "code")
                                                 @NotEmpty(message = "code不能为空")
                                                 String code) {

        MsgType entity = service.findOne(code);
        MsgTypeVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 根据参数获取MsgTypeVo多条记录。
     * <p>
     * <p>
     * params参数key为moduleName（可选），type（可选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<List <MsgTypeVo>>
     */
    @Override
    public ResponseEntity<List<MsgTypeVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        String moduleName = "";
        String type    = "";

        if (params.containsKey("moduleName")) {
            moduleName = params.getFirst("moduleName").toString();
        }
        if (params.containsKey("type")) {
            type = params.getFirst("type").toString();
        }
        List<MsgType>   list   = service.findAll(moduleName,type);
        List<MsgTypeVo> voList = mapper.entityListToVoList(list);

        return ResponseEntity.ok(voList);
    }
}
