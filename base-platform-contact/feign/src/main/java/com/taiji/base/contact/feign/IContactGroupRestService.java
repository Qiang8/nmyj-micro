package com.taiji.base.contact.feign;

import com.taiji.base.contact.vo.ContactGroupVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * <p>Title:IContactGroupRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2019/1/15 0:04</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-contact-group")
public interface IContactGroupRestService {

    /**
     * 根据ContactGroupVo id获取一条记录。
     *
     * @param id 通讯录分组id
     * @return ResponseEntity<ContactGroupVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<ContactGroupVo> find(@PathVariable("id") String id);

    /**
     * 根据参数获取ContactGroupVo多条记录。
     * <p>
     * params参数key。
     *      userId 查找个人分组
     *
     * @return ResponseEntity<List<ContactGroupVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<ContactGroupVo>> findList(@RequestParam(name = "userId") String userId);

    /**
     * 新增ContactGroupVo，ContactGroupVo不能为空。
     *
     * @param vo 通讯录分组vo
     * @return ResponseEntity<ContactGroupVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<ContactGroupVo> create(@RequestBody ContactGroupVo vo);

    /**
     * 更新ContactGroupVo，ContactGroupVo不能为空。
     *
     * @param vo 通讯录分组vo
     * @param id 更新ContactGroupVo Id
     * @return ResponseEntity<ContactGroupVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<ContactGroupVo> update(@RequestBody ContactGroupVo vo,
                                          @PathVariable(value = "id") String id);


    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id 通讯录分组id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);
}
