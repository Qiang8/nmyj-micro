package com.taiji.base.sys.controller;

import com.taiji.base.sys.service.OrgService;
import com.taiji.base.sys.vo.OrgVo;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.utils.AssemblyTreeUtils;
import com.taiji.micro.common.utils.ITreeNode;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:OrgController.java</p >
 * <p>Description: 机构管理控制类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/orgs")
public class OrgController extends BaseController{

    OrgService orgService;

    /**
     * 获取机构树
     * @param parentId
     * @param orgName
     * @return
     */
    @GetMapping
    public ResultEntity findOrgTree(@RequestParam(name = "parentId",required = false) String parentId,
                                    @RequestParam(name = "orgName",required = false) String orgName){

        Map<String,Object> params = new HashMap<>();
        params.put("parentId",parentId);
        params.put("orgName",orgName);

        List<OrgVo> orgVoList = orgService.findOrgAll(params);

        OrgVo top = new OrgVo();
        top.setId("-1");
        top.setParentId("-1");
        top.setOrgName("组织机构");
        orgVoList.add(top);

        List<OrgVo> root = AssemblyTreeUtils.assemblyTree(orgVoList);

        return ResultUtils.success(root);
    }

    /**
     * 新增机构信息
     * @param orgVo
     * @return
     */
    @PostMapping
    public ResultEntity addOrg(
            @RequestBody  OrgVo orgVo){

        if(orgVo.getOrders() == null) {
            orgVo.setOrders(999);
        }

        orgService.create(orgVo);

        return ResultUtils.success();
    }

    /**
     * 获取单个机构
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResultEntity findOrgById(@NotEmpty(message = "id不能为空")
                                     @PathVariable(name = "id") String id){
        OrgVo orgVo = orgService.findById(id);

        return ResultUtils.success(orgVo);
    }

    /**
     * 修改机构信息
     * @param id
     * @param orgVo
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResultEntity updateOrg(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id,
                                   @NotNull(message = "orgVo不能为null") @RequestBody OrgVo orgVo){
        orgService.update(orgVo,id);

        return ResultUtils.success();
    }

    /**
     * 删除机构
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResultEntity deleteOrg(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id){

        orgService.delete(id);

        return ResultUtils.success();
    }
}
