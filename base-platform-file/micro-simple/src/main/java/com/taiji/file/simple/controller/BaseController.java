package com.taiji.file.simple.controller;

import org.springframework.data.domain.Sort;

/**
 * <p>Title:BaseController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/23 10:15</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class BaseController{
    final Sort sort = new Sort(Sort.Direction.DESC, "id");
}
