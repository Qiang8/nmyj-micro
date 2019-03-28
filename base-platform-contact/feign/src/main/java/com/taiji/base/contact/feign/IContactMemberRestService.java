package com.taiji.base.contact.feign;

import com.taiji.base.contact.vo.ContactMemberVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * <p>Title:IContactMemberRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2019/1/15 0:04</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-contact-member")
public interface IContactMemberRestService {

    /**
     * 根据ContactMemberVo id获取一条记录。
     *
     * @param id 通讯录id
     * @return ResponseEntity<ContactMemberVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<ContactMemberVo> find(@PathVariable("id") String id);

    /**
     * 根据参数获取ContactMemberVo多条记录。
     * <p>
     * params参数key
     *       content 姓名、电话、部门名称综合查询
     *
     * @param params 查询参数集合
     * @return ResponseEntity<List<ContactMemberVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<ContactMemberVo>> findList(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 根据参数获取分页ContactMemberVo多条记录。
     * <p>
     * params参数key
     *        orgCode 组织机构编码
     *       letter 姓名首字母大写
     *       content 姓名、电话、部门名称综合查询
     *       name   姓名  与content不能同时使用
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<ContactMemberVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<ContactMemberVo>> findPage(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 新增ContactMemberVo，ContactMemberVo不能为空。
     *
     * @param vo 通讯录vo
     * @return ResponseEntity<ContactMemberVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<ContactMemberVo> create(@RequestBody ContactMemberVo vo);

    /**
     * 更新ContactMemberVo，ContactMemberVo不能为空。
     *
     * @param vo 通讯录vo
     * @param id 更新ContactMemberVo Id
     * @return ResponseEntity<ContactMemberVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<ContactMemberVo> update(@RequestBody ContactMemberVo vo,
                                           @PathVariable(value = "id") String id);


    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id 通讯录id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);
}
