package com.taiji.base.sys.feign;

import com.taiji.base.sys.vo.RoleTypeMenuVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Title:IRoleTypeMenuRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/30 11:48</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-role-type-menu")
public interface IRoleTypeMenuRestService {

    /**
     * 根据参数获取RoleTypeMenuVo多条记录。
     * <p>
     * params参数key为roleType（必选）。
     *
     * @param params 查询参数集合
     * @return ResponseEntity<List<RoleTypeMenuVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<RoleTypeMenuVo>> findList(@RequestParam MultiValueMap<String, Object> params);

    /**
     * 保存多个角色类型资源vo,保存前会先根据roleType删除数据库相应的记录再保存voList。
     *
     * @param voList    角色类型资源vo
     *
     * @param roleType  角色类型
     *
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/save/{roleType}")
    @ResponseBody
    ResponseEntity<Void> save(@RequestBody List<RoleTypeMenuVo> voList, @PathVariable(value = "roleType") String roleType);
}
