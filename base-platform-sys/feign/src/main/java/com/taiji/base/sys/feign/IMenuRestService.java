package com.taiji.base.sys.feign;

import com.taiji.base.sys.vo.MenuVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IMenuRestService.java</p >
 * <p>Description: 菜单管理feign接口</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/23 17:24</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-menu")
public interface IMenuRestService {

    /**
     * 根据参数获取MenuVo多条记录。
     *
     * params参数key为parentId（可选），roleType（可选）。
     *
     * @return ResponseEntity<MenuVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<MenuVo>> findList(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 根据MenuVo id获取一条记录。
     *
     * @param id
     * @return ResponseEntity<MenuVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<MenuVo> find(@PathVariable("id") String id);

    /**
     * 新增MenuVo，MenuVo不能为空。
     *
     * @param vo
     * @return ResponseEntity<MenuVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<MenuVo> create(@RequestBody MenuVo vo);

    /**
     * 更新MenuVo，MenuVo不能为空。
     *
     * @param vo
     * @param id 更新MenuVo Id
     * @return ResponseEntity<MenuVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<MenuVo> update(@RequestBody MenuVo vo, @PathVariable(value = "id") String id);


    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);
}
