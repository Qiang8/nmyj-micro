package com.taiji.base.sys.feign;

import com.taiji.base.sys.vo.DicGroupItemVo;
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
@FeignClient(value = "micro-dic-group-item")
public interface IDicGroupItemRestService {


    /**
     * 根据DicGroupItemVo获取一条记录。
     *
     * @param id    字典项id
     * @return ResponseEntity<DicGroupItemVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<DicGroupItemVo> find(@PathVariable("id") String id);

    /**
     * 根据参数获取DicGroupItemVo多条记录。
     *
     * params参数key为dicCode（可选）。
     *
     * @param params    查询参数集合
     * @return ResponseEntity<List<DicGroupItemVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<DicGroupItemVo>> findList(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 新增DicGroupItemVo，DicGroupItemVo不能为空。
     *
     * @param vo    字典项vo
     * @return ResponseEntity<DicGroupItemVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<DicGroupItemVo> create(@RequestBody DicGroupItemVo vo);

    /**
     * 更新DicGroupItemVo，DicGroupItemVo不能为空。
     *
     * @param vo    字典项vo
     * @param id 更新DicGroupItemVo Id
     * @return ResponseEntity<DicGroupItemVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<DicGroupItemVo> update(@RequestBody DicGroupItemVo vo, @PathVariable(value = "id") String id);


    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id        字典项id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);

    /**
     * 根据字典项id获取名称
     *
     * @param id    字典项id
     * @return ResponseEntity<String> -- 字典名
     */
    @RequestMapping(method = RequestMethod.GET, path = "/findItemNameById")
    @ResponseBody
    ResponseEntity<String> findItemNameById(@RequestParam("id") String id);

    /**
     * 根据字典项id List获取名称字符串(英文逗号连接)
     *
     * @param itemIds    字典项id
     * @return ResponseEntity<String> -- 字典名
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findItemNamesByIds")
    @ResponseBody
    ResponseEntity<String> findItemNamesByIds(@RequestBody List<String> itemIds);

}
