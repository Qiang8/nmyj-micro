package com.taiji.file.fastdfs.controller;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.taiji.base.file.enums.FileStatusEnum;
import com.taiji.base.file.feign.IAttachmentRestService;
import com.taiji.base.file.vo.AttachmentVo;
import com.taiji.file.common.entity.Attachment;
import com.taiji.file.common.mapper.AttachmentMapper;
import com.taiji.file.common.service.AttachmentService;
import com.taiji.file.fastdfs.utils.DocReadUtils;
import com.taiji.file.fastdfs.utils.UploadUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * <p>Title:AttachmentController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/28 9:32</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/file")
public class AttachmentController extends BaseController implements IAttachmentRestService {


    private FastFileStorageClient storageClient;
    private AttachmentService     service;
    private AttachmentMapper      mapper;

    @PostMapping("/upload")
    public ResponseEntity<AttachmentVo> uploadFile(@RequestPart("file") MultipartFile file, @RequestParam("uploadModule") String uploadModule) throws Exception{

        String fileType = file.getName();

        String originalFileName = file.getOriginalFilename();
        String extension        = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName         = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        String location = UploadUtils.uploadFile(file, uploadModule, fileType, storageClient);

        Attachment attachment = new Attachment();
        attachment.setFileName(fileName);
        attachment.setOriginalName(originalFileName);
        attachment.setFileSuffix(extension);
        attachment.setLocation(location);
        attachment = service.create(attachment);

        AttachmentVo vo = mapper.entityToVo(attachment);

        return ResponseEntity.ok(vo);
    }

    /**
     * 文件下载
     *
     * @param attId
     *
     * @return
     */
    @GetMapping("/download/{attId}")
    public void downloadFile(@PathVariable("attId") String attId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        Attachment attachment = service.findOne(attId);
        String     fileName   = attachment.getOriginalName();
        String     location   = attachment.getLocation();

        UploadUtils.downloadFile(fileName, location, response, request, storageClient);
    }

    /**
     * http://127.0.0.1:9003/micro-file-fastdfs/api/file/downloads/6797d027-0167-97d4203a-0000-40280d81,6797f0fb-0167-97f69452-0000-40280d81?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDQ0ODM5NzMsInVzZXJfbmFtZSI6ImdvZCIsImF1dGhvcml0aWVzIjpbIlNVUEVSX0FETUlOIiwiUk9MRV9VU0VSIiwiQURNSU4iXSwianRpIjoiNGY2MjYzZWYtOWZhNS00ZTUzLTlmNjItOTE5OGU3ZWYxYmY3IiwiY2xpZW50X2lkIjoiY2xpZW50SWQiLCJzY29wZSI6WyJ1c2VyX2luZm8iLCJyZWFkIiwid3JpdGUiLCJzZXJ2ZXIiXX0.pnIR0TTYBe6MEQsHs1V7p5b8UKja4ssfbQhan-2sHlY
     *
     * @param attIds
     * @param response
     * @param request
     * @throws Exception
     */
    @GetMapping("/downloads/{attIds}")
    public void downloadFiles(@PathVariable("attIds") String[] attIds, HttpServletResponse response, HttpServletRequest request) throws Exception {
        List<Attachment> attachmentList = service.findAll(Arrays.asList(attIds));

        UploadUtils.downloadFiles(attachmentList, response, request,storageClient);
    }

    @GetMapping("/showImage/{attId}")
    public void showImage(@PathVariable("attId") String attId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        Attachment attachment = service.findOne(attId);
        String     fileName   = attachment.getOriginalName();
        String     location   = attachment.getLocation();

        UploadUtils.showImage(fileName, location, response, request,storageClient);
    }



    /**
     * 更新AttachmentVo，AttachmentVo不能为空。
     *
     * @param vo
     * @param id 更新AttachmentVo Id
     * @return ResponseEntity<AttachmentVo>
     */
    @Override
    public ResponseEntity<AttachmentVo> update(AttachmentVo vo, String id) {
        Attachment   tempEntity = mapper.voToEntity(vo);
        Attachment   entity     = service.update(tempEntity, id);
        AttachmentVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    @Override
    @DeleteMapping("/delete/{attId}")
    public ResponseEntity<AttachmentVo> delete(@PathVariable(value = "attId") String id) {
        Attachment   tempEntity     = service.findOne(id);
        tempEntity.setFileStatus(FileStatusEnum.DELETED.getCode());
        Attachment   entity     = service.update(tempEntity, id);
        AttachmentVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据传入的路径获取word文档内容
     *
     * @param uploadFilePaths (文件上传相对路径集合)
     * @return ResponseEntity<List < String>>
     */
    @Override
    public ResponseEntity<List<String>> readDocsContent(@RequestBody List<String> uploadFilePaths) throws Exception{
        List<String> result = DocReadUtils.readDocListContent(uploadFilePaths,storageClient);
        return ResponseEntity.ok(result);
    }

}
