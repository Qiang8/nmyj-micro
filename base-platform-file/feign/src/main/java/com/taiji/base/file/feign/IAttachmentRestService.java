package com.taiji.base.file.feign;

import com.taiji.base.file.vo.AttachmentVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>Title:IAttachmentRestService.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/23 10:32</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@FeignClient(value = "micro-attachment")
public interface IAttachmentRestService {

    /**
     * 更新AttachmentVo，AttachmentVo不能为空。
     *
     * @param vo
     * @param id 更新AttachmentVo Id
     * @return ResponseEntity<AttachmentVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<AttachmentVo> update(@RequestBody AttachmentVo vo, @PathVariable(value = "id") String id);

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{attId}")
    ResponseEntity<AttachmentVo> delete(@PathVariable(value = "attId") String id);

    /**
     * 根据传入的路径获取word文档内容
     *
     * @param uploadFilePaths (文件上传相对路径集合)
     * @return ResponseEntity<List < String>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/readDocs")
    @ResponseBody
    ResponseEntity<List<String>> readDocsContent(@RequestBody List<String> uploadFilePaths)throws Exception;
}
