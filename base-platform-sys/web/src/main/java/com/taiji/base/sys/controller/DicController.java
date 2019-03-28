package com.taiji.base.sys.controller;

import com.taiji.base.sys.service.DicGroupService;
import com.taiji.base.sys.service.DicItemService;
import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.DicGroupVo;
import com.taiji.base.sys.vo.ItemMapDto;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * <p>Title:DicController.java</p >
 * <p>Description: 数据字典管理控制类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/dic")
public class DicController extends BaseController{

    DicGroupService groupService;

    DicItemService itemService;

    /**
     * 新增字典项
     * @param dicGroupVo
     * @return
     */
    @PostMapping(path = "/groups")
    public ResultEntity addGroup(
            @RequestBody
            @Valid
            @NotNull(message = "UserVo不能为null") DicGroupVo dicGroupVo){
        groupService.create(dicGroupVo);

        return ResultUtils.success();
    }

    /**
     * 获取单个字典项信息
     * @param id
     * @return
     */
    @GetMapping(path = "/groups/{id}")
    public ResultEntity findGroupById(@NotEmpty(message = "id不能为空")
                                     @PathVariable(name = "id") String id){
        DicGroupVo userVo = groupService.findById(id);

        return ResultUtils.success(userVo);
    }

    /**
     * 修改字典项信息
     * @param id
     * @param dicGroupVo
     * @return
     */
    @PutMapping(path = "/groups/{id}")
    public ResultEntity updateGroup(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id,
                                   @NotNull(message = "UserVo不能为null") @RequestBody DicGroupVo dicGroupVo){
        groupService.update(dicGroupVo,id);

        return ResultUtils.success();
    }

    /**
     * 删除字典项
     * @param id
     * @return
     */
    @DeleteMapping(path = "/groups/{id}")
    public ResultEntity deleteGroup(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id){
        groupService.delete(id);

        return ResultUtils.success();
    }

    /**
     * 查询字典项列表——分页
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/groups/search")
    public ResultEntity findGroups(@RequestBody Map<String, Object> paramsMap){

        if(paramsMap.containsKey("page") && paramsMap.containsKey("size")){
            RestPageImpl<DicGroupVo> pageList = groupService.findGroups(paramsMap);

            return ResultUtils.success(pageList);
        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 查询字典项列表——不分页
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/groups/searchAll")
    public ResultEntity findGroupsAll( @RequestBody Map<String, Object> paramsMap){

        List<DicGroupVo> allList = groupService.findGroupsAll(paramsMap);

        return ResultUtils.success(allList);

    }

    /**
     * 检查字典项编码的唯一性
     * @param paramsMap
     * @return
     */
    @PostMapping(path = "/groups/checkCode")
    public ResultEntity checkCode(@RequestBody Map<String, String> paramsMap){

        if(paramsMap.containsKey("dicCode")){
            Boolean result = groupService.checkDicCode(paramsMap.get("dicCode"));

            return ResultUtils.success(result);

        }else{
            return ResultUtils.fail(ResultCodeEnum.PARAMETER_ERROR);
        }
    }

    /**
     * 获取数据字典列表
     * @param dicCode
     * @return
     */
    @GetMapping(path = "/items")
    public ResultEntity findItemsList(@RequestParam(name = "dicCode") String dicCode){

        Map<String,Object> params = new HashMap<>();
        params.put("dicCode",dicCode);

        DicGroupVo dicGroupVo = groupService.findByDicCode(dicCode);

        List<DicGroupItemVo> itemVos = itemService.findItemsAll(params);

        DicGroupItemVo top = new DicGroupItemVo();
        top.setId("-1");
        top.setParentId("-1");
        top.setItemName(dicGroupVo.getDicName());
        top.setType(dicGroupVo.getType());
        itemVos.add(top);

        List<DicGroupItemVo> root = AssemblyTreeUtils.assemblyTree(itemVos);

        return ResultUtils.success(root);

    }

    /**
     * 新增数据字典
     * @param dicItemVo
     * @return
     */
    @PostMapping(path = "/items")
    public ResultEntity addItem(
            @RequestBody
            @Validated
            @NotNull(message = "DicGroupItemVo不能为null") DicGroupItemVo dicItemVo){

        if(dicItemVo.getOrders() == null)
        {
            dicItemVo.setOrders(999);
        }

        itemService.create(dicItemVo);

        return ResultUtils.success();
    }

    /**
     * 获取单个数据字典
     * @param id
     * @return
     */
    @GetMapping(path = "/items/{id}")
    public ResultEntity findItemById(@NotEmpty(message = "id不能为空")
                                      @PathVariable(name = "id") String id){
        DicGroupItemVo userVo = itemService.findById(id);

        return ResultUtils.success(userVo);
    }

    /**
     * 修改数据字典
     * @param id
     * @param dicItemVo
     * @return
     */
    @PutMapping(path = "/items/{id}")
    public ResultEntity updateItem(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id,
                                    @NotNull(message = "DicGroupItemVo不能为null") @RequestBody DicGroupItemVo dicItemVo){
        itemService.update(dicItemVo,id);

        return ResultUtils.success();
    }

    /**
     * 删除数据字典
     * @param id
     * @return
     */
    @DeleteMapping(path = "/items/{id}")
    public ResultEntity deleteItem(@NotEmpty(message = "id不能为空") @PathVariable(name = "id") String id){
        itemService.delete(id);

        return ResultUtils.success();
    }

    /**
     * 获取下拉字典选择项
     * @param dicCode
     * @return
     */
    @GetMapping(path = "/options")
    public ResultEntity findOptions(@NotEmpty(message = "dicCode不能为空")
                                     @RequestParam(name = "dicCode") String dicCode){

        Map<String,Object> params = new HashMap<>();
        params.put("dicCode",dicCode);
        List<DicGroupItemVo> itemVos = itemService.findItemsAll(params);

        //由于从redis中获取到是没有根据order排序的，因此在这里排序 -- add by qizj 2018年12月23日16:02:30
        itemVos.sort(new Comparator<DicGroupItemVo>() {
            @Override
            public int compare(DicGroupItemVo o1, DicGroupItemVo o2) {
                try {
                    return o1.getOrders() - o2.getOrders();
                } catch (Exception var3) {
                    return 0;
                }
            }
        });
        //---排序结束

        List<ItemMapDto> dtos = new ArrayList<>();
        for(DicGroupItemVo vo : itemVos){
            ItemMapDto dto = new ItemMapDto();
            dto.setKey(vo.getId());
            dto.setValue(vo.getItemName());

            dtos.add(dto);
        }

        return ResultUtils.success(dtos);
    }

    /**
     * 获取树型字典选择项
     * @param dicCode
     * @return
     */
    @GetMapping(path = "/tree")
    public ResultEntity findTree(@NotEmpty(message = "dicCode不能为空")
                                    @RequestParam(name = "dicCode") String dicCode){
        Map<String,Object> params = new HashMap<>();
        params.put("dicCode",dicCode);
        List<DicGroupItemVo> itemVos = itemService.findItemsAll(params);

        List<DicGroupItemVo> root = AssemblyTreeUtils.assemblyTree(itemVos);

        return ResultUtils.success(root);
    }
}
