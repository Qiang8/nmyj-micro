package com.taiji.base.sample.controller;

import org.springframework.data.domain.Sort;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
public class BaseController{
    final Sort sort = new Sort(Sort.Direction.DESC, "id");
}
