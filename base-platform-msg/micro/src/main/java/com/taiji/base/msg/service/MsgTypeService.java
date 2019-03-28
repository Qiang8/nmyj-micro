package com.taiji.base.msg.service;

import com.taiji.base.msg.entity.MsgType;
import com.taiji.base.msg.repository.MsgTypeRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title:MsgTypeService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/31 11:31</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class MsgTypeService extends BaseService<MsgType,String> {
    MsgTypeRepository repository;

    public List<MsgType> findAll(String moduleName, String type){

        return repository.findAll(moduleName,type);

    }

    public MsgType findOne(String code) {

        return repository.findOneByCode(code);

    }
}
