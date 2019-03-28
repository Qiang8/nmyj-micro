package com.taiji.base.contact.feign;

import com.taiji.base.contact.vo.ContactMemberVo;
import com.taiji.base.contact.vo.ContactMidVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * <p>Title:IContactMidRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2019/1/15 0:04</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-contact-mid")
public interface IContactMidRestService {

    /**
     * 根据参数获取ContactGroupVo多条记录。
     * <p>
     * params参数key
     *        groupId 分组编码
     *        content 姓名、电话、部门名称综合查询
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
     *        groupId 分组编码
     *        orgId 组织机构编码
     *       letter 姓名首字母大写
     *       content 姓名、电话、部门名称综合查询
     *
     * @param params 查询参数集合
     * @return ResponseEntity<RestPageImpl<ContactMemberVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<ContactMemberVo>> findPage(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 保存分组人员信息表，先删除分组人员，再添加。
     *
     * @param voList   人员vo列表
     * @param groupId 分组编码
     *
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/save/{groupId}")
    @ResponseBody
    ResponseEntity<Void> saveMid(@RequestBody List<ContactMidVo> voList,
                                 @PathVariable(value = "groupId") String groupId);

}
