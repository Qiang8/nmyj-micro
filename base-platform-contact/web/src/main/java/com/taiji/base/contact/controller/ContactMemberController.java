package com.taiji.base.contact.controller;

import com.taiji.base.contact.service.ContactMemberService;
import com.taiji.base.contact.service.ContactMidService;
import com.taiji.base.contact.vo.ContactMemberVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <p>Title:ContactMemberController.java</p >
 * <p>Description: 通讯录人员控制类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2019/1/13 5:29</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/contact/members")
public class ContactMemberController extends BaseController{

    ContactMemberService memberService;

    ContactMidService midService;

    /**
     * 新增人员
     * @param memberVo
     * @return
     */
    @PostMapping
    public ResultEntity addMember(@RequestBody ContactMemberVo memberVo){

        memberService.create(memberVo);

        return ResultUtils.success();
    }

    /**
     * 获取单个人员信息
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findMemberById(@NotEmpty(message = "id不能为空")
                                     @PathVariable(name = "id") String id){
        ContactMemberVo memberVo = memberService.findById(id);

        return ResultUtils.success(memberVo);
    }

    /**
     * 修改人员信息
     * @param id
     * @param memberVo
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateMember(@NotEmpty(message = "id不能为空")
                                   @PathVariable(name = "id") String id,
                                   @NotNull(message = "ContactMemberVo不能为null")
                                   @RequestBody ContactMemberVo memberVo){
        memberService.update(memberVo,id);

        return ResultUtils.success();
    }

    /**
     * 删除人员
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteMember(@NotEmpty(message = "id不能为空")
                                         @PathVariable(name = "id") String id){
        memberService.delete(id);

        return ResultUtils.success();
    }

    /**
     * 查询人员列表——分页
     *
     * @param paramsMap
     *    key值包括
     *        orgCode 组织机构编码
     *        groupId 分组编码
     *       letter 姓名首字母大写
     *       content 姓名、电话、部门名称综合查询
     *       name   姓名  与content不能同时使用
     *       page+size 分页信息
     * @return
     */
    @PostMapping(path = "/search")
    public ResultEntity findMembers(@RequestBody Map<String, Object> paramsMap){

        if(paramsMap.containsKey("page") && paramsMap.containsKey("size")){
            RestPageImpl<ContactMemberVo> pageList = new RestPageImpl<>();
            if(paramsMap.containsKey("groupId") && StringUtils.hasText((String)paramsMap.get("groupId"))){
               pageList = midService.findMembers(paramsMap);
            }else{
                pageList = memberService.findMembers(paramsMap);
            }

            return ResultUtils.success(pageList);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 查询用户列表——不分页
     *
     * @param paramsMap
     *  key值包括
     *       groupId 分组编码
     *      content 姓名、电话、部门名称综合查询
     *
     * @return
     */
    @PostMapping(path = "/searchAll")
    public ResultEntity findMembersAll(@RequestBody Map<String, Object> paramsMap){

        List<ContactMemberVo> allList = new ArrayList<>();
        if(paramsMap.containsKey("groupId")&& StringUtils.hasText((String)paramsMap.get("groupId"))){
            allList = midService.findMembersAll(paramsMap);
        }else{
            allList = memberService.findMembersAll(paramsMap);
        }

        return ResultUtils.success(allList);

    }

}
