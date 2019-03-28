package com.taiji.base.sys.feign;

import com.taiji.base.sys.vo.DicGroupVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IDicGroupRestService.java</p >
 * <p>Description: 字典管理feign接口</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:20</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-dic-group")
public interface IDicGroupRestService {

    /**
     * 根据参数获取DicGroupVo多条记录。
     *
     * params参数key为dicName（可选），status（可选）。
     *
     * @param params
     * @return ResponseEntity<List<DicGroupVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<DicGroupVo>> findList(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 根据参数获取DicGroupVo多条记录。
     *
     * params参数key为dicName（可选  模糊查询），status（可选）。
     *
     * @param params
     * @return ResponseEntity<RestPageImpl<DicGroupVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<DicGroupVo>> findPage(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 根据DicGroupVo获取一条记录。
     *
     * @param id
     * @return ResponseEntity<DicGroupVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<DicGroupVo> find(@PathVariable("id") String id);

    /**
     * 根据DicGroupVo获取一条记录。
     *
     * @param params 参数key为dicCode（必选）
     * @return ResponseEntity<DicGroupVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findOne")
    @ResponseBody
    ResponseEntity<DicGroupVo> findOne(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 新增DicGroupVo，DicGroupVo不能为空。
     *
     * @param vo
     * @return ResponseEntity<DicGroupVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<DicGroupVo> create(@RequestBody DicGroupVo vo);

    /**
     * 更新DicGroupVo，DicGroupVo不能为空。
     *
     * @param vo
     * @param id 更新DicGroupVo Id
     * @return ResponseEntity<DicGroupVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<DicGroupVo> update(@RequestBody DicGroupVo vo, @PathVariable(value = "id") String id);


    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);

    /**
     * 根据参数判断字典标识是否被占用。
     *
     * @param id       （可选）
     * @param dicCode （必选）
     * @return ResponseEntity<Boolean>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/checkUnique")
    @ResponseBody
    ResponseEntity<Boolean> checkUnique(@RequestParam(name = "id") String id,@RequestParam(name = "dicCode") String dicCode);

}
