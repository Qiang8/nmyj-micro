package com.taiji.cdp.base.midAtt.feign;

import com.taiji.cdp.base.midAtt.vo.AttachmentVo;
import com.taiji.cdp.base.midAtt.vo.MidAttSaveVo;
import com.taiji.cdp.base.midAtt.vo.MidAttVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 附件中间表操作服务接口
 * @author qizhijie-pc
 * @date 2019年1月7日14:25:35
 */
@FeignClient(value = "micro-midAtt")
public interface IMidAttRestService {

    /**
     * 创建单个中间表对象vo
     * @param vo 中间表对象
     * @return ResponseEntity<MidAttVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/one")
    @ResponseBody
    ResponseEntity<MidAttVo> createOne(@RequestBody MidAttVo vo);

    /**
     * 创建多个中间表对象vo
     * @param vos
     * @return ResponseEntity<MidAttVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/list")
    @ResponseBody
    ResponseEntity<List<MidAttVo>> createList(@RequestBody List<MidAttVo> vos);

    /**
     * 通过附件id删除单个中间表对象vo
     * @param attId 附件id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/one")
    @ResponseBody
    ResponseEntity<Void> deleteByAttId(@RequestParam(value = "attId") String attId);

    /**
     * 通过附件ids删除多个中间表对象vo
     * @param attIds 附件id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/delete/list")
    @ResponseBody
    ResponseEntity<Void> deleteByAttIds(@RequestBody List<String> attIds);


    /**
     * 根据业务实体id获取所有附件对象vo
     * @param entityId 业务实体id
     * @return ResponseEntity<List<AttachmentVo>>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{entityId}")
    @ResponseBody
    ResponseEntity<List<AttachmentVo>> findAttsByEntityId(@PathVariable(value = "entityId") String entityId);

    /**
     * 业务提交保存时调用，保存附件信息
     * @param midAttSaveVo 业务带附件提交对象
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/saveMidAtts")
    @ResponseBody
    ResponseEntity<Void> saveMidAtts(@RequestBody MidAttSaveVo midAttSaveVo);
}
