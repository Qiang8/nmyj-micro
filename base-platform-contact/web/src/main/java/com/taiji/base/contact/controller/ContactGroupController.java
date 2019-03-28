package com.taiji.base.contact.controller;

import com.taiji.base.common.utils.SecurityUtils;
import com.taiji.base.contact.service.ContactGroupService;
import com.taiji.base.contact.service.ContactMidService;
import com.taiji.base.contact.vo.ContactGroupVo;
import com.taiji.base.contact.vo.ContactMidVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <p>Title:ContactGroupController.java</p >
 * <p>Description: 通讯录分组控制类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2019/1/13 5:29</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/contact/groups")
public class ContactGroupController extends BaseController {

    ContactGroupService groupService;

    ContactMidService midService;

    /**
     * 新增分组
     * @param groupVo
     * @return
     */
    @PostMapping
    public ResultEntity addGroup(OAuth2Authentication principal,
                                 @RequestBody ContactGroupVo groupVo){

        LinkedHashMap<String,Object> principalMap = SecurityUtils.getPrincipalMap(principal);
        String userId = (String)principalMap.get("id");

        List<ContactGroupVo> allList = groupService.findGroupsAll(userId);
        groupVo.setOrders(CollectionUtils.isEmpty(allList)? 0 :allList.size());
        groupVo.setUserId(userId);

        ContactGroupVo vo = groupService.create(groupVo);

        return ResultUtils.success(vo);
    }

    /**
     * 获取单个分组信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findGroupById(@NotEmpty(message = "id不能为空")
                                     @PathVariable(name = "id") String id){
        ContactGroupVo groupVo = groupService.findById(id);

        return ResultUtils.success(groupVo);
    }

    /**
     * 修改分组信息
     * @param id
     * @param groupVo
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateGroup(@NotEmpty(message = "id不能为空")
                                   @PathVariable(name = "id") String id,
                                   @NotNull(message = "ContactGroupVo不能为null")
                                   @RequestBody ContactGroupVo groupVo){
        groupService.update(groupVo,id);

        return ResultUtils.success();
    }

    /**
     * 删除分组
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteGroup(@NotEmpty(message = "id不能为空")
                                        @PathVariable(name = "id") String id){
        groupService.delete(id);

        return ResultUtils.success();
    }

    /**
     * 查询分组列表——不分页
     *
     * @return
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findGroupsAll(OAuth2Authentication principal){

        LinkedHashMap<String,Object> principalMap = SecurityUtils.getPrincipalMap(principal);
        String userId = (String)principalMap.get("id");
        List<ContactGroupVo> allList = groupService.findGroupsAll(userId);

        return ResultUtils.success(allList);

    }

    /**
     * 保存分组人员信息
     *
     * @param paramsMap
     *
     * @return
     */
    @PostMapping(path = "/members")
    public ResultEntity saveGroupMembers(@RequestBody Map<String, Object> paramsMap) {

        if(paramsMap.containsKey("groupId")&&
                paramsMap.containsKey("memberIds")){
            String groupId = (String) paramsMap.get("groupId");
            List<String> memberIds = (List<String>)paramsMap.get("memberIds");

            List<ContactMidVo> midVos = new ArrayList<>();
            for(String memberId : memberIds){
                ContactMidVo vo = new ContactMidVo(memberId,groupId);

                midVos.add(vo);
            }

            midService.saveContactMids(midVos,groupId);

            return ResultUtils.success();
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 保存
     *
     * @param groupList
     *
     * @return
     */
    @PostMapping(path = "/sort")
    public ResultEntity saveGroupSort(@RequestBody List<ContactGroupVo> groupList) {

        if(!CollectionUtils.isEmpty(groupList)){
            for(int i = 0,count = groupList.size(); i< count; i++){

                ContactGroupVo vo = groupList.get(i);
                vo.setOrders(i);
                groupService.update(vo,vo.getId());
            }
        }

        return ResultUtils.success();
    }
}
