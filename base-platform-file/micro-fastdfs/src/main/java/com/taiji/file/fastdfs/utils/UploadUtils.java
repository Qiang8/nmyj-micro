package com.taiji.file.fastdfs.utils;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.taiji.file.common.entity.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>Title:UploadUtils.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/12/7 16:30</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
public class UploadUtils {

    /**
     * 文件上传
     *
     * @param file
     * @param uploadModule
     * @param uploadType
     *
     * @return
     *
     * @throws Exception
     */
    public static String uploadFile(MultipartFile file, String uploadModule, String uploadType, FastFileStorageClient fastFileStorageClient) throws Exception {

        String fileName = "";


        String   originalFilename = file.getOriginalFilename();
        String[] fileNames        = originalFilename.trim().split("\\.");

        String extension = fileNames[fileNames.length - 1];
        long   fileSize  = file.getSize();

        fileName = UUID.randomUUID() + "-" + uploadType + "." + extension;

        log.debug("originalFilename=={},fileSize=={},extension=={},newFileName=={}", originalFilename, fileSize, extension, fileName);

        StorePath storePath;
        try {
            storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(),
                                                         extension, null);
            log.info(storePath.getGroup() + "===" + storePath.getPath() + "======" + storePath.getFullPath());

            if (null != storePath) {
                return storePath.getFullPath();
            } else {
                return "";
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);

            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param path
     * @param response
     * @param request
     *
     * @throws Exception
     */
    public static void downloadFile(String fileName, String path, HttpServletResponse response, HttpServletRequest request, FastFileStorageClient fastFileStorageClient) throws Exception {
        response = downloadSetting(fileName, response, request);
        dealFileToResponse(path, response, fastFileStorageClient);
    }

    /**
     * 多附件下载
     *
     * @param attachmentList
     * @param response
     * @param request
     *
     * @throws Exception
     */
    public static void downloadFiles(List<Attachment> attachmentList, HttpServletResponse response, HttpServletRequest request, FastFileStorageClient fastFileStorageClient) {
        String downloadFileName = UUID.randomUUID().toString() + ".zip";
        response = downloadSetting(downloadFileName, response, request);

        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(response.getOutputStream());

            for (Attachment attachment : attachmentList) {
                String fileName = attachment.getOriginalName();
                String location = attachment.getLocation();

                String group = location.substring(0, location.indexOf("/"));
                String path  = location.substring(location.indexOf("/") + 1);


                zos.putNextEntry(new ZipEntry(UUID.randomUUID().toString() + "-" + fileName));

                ZipOutputStream finalZos = zos;
                fastFileStorageClient.downloadFile(group, path, inputStream -> {
                    BufferedOutputStream bos = null;
                    try {
                        bos = new BufferedOutputStream(finalZos);
                        FileCopyUtils.copy(inputStream, bos);
                    } catch (IOException e) {
                        log.debug(e.getMessage(), e);
                    } finally {
                        if (null != inputStream) {
                            inputStream.close();
                        }
                    }
                    return null;
                });
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);

            throw new RuntimeException("zip error from UploadUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public static void showFile(String fileName, String path, HttpServletResponse response, HttpServletRequest request, FastFileStorageClient fastFileStorageClient) throws IOException {
        response = showFileSetting(fileName, response, request);
        dealFileToResponse(path, response, fastFileStorageClient);
    }

    public static void showImage(String fileName, String path, HttpServletResponse response, HttpServletRequest request, FastFileStorageClient fastFileStorageClient) throws IOException {
        response = showImageSetting(fileName, response, request);
        dealFileToResponse(path, response, fastFileStorageClient);
    }

    /**
     * 设置报文
     *
     * @param fileName
     * @param response
     * @param request
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpServletResponse downloadSetting(String fileName, HttpServletResponse response, HttpServletRequest request) {

        fileName = getFileName(fileName, request);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentType("application/octet-stream,charset=UTF-8");

        return response;
    }


    public static HttpServletResponse showFileSetting(String fileName, HttpServletResponse response, HttpServletRequest request) {

        fileName = getFileName(fileName, request);
        response.reset();
        response.setHeader("Content-Disposition", "filename=" + fileName);
        response.setContentType("application/octet-stream,charset=UTF-8");

        return response;
    }

    /**
     * 设置报文
     *
     * @param fileName
     * @param response
     * @param request
     *
     * @return
     *
     * @throws Exception
     */
    public static HttpServletResponse showImageSetting(String fileName, HttpServletResponse response, HttpServletRequest request) {

        fileName = getFileName(fileName, request);
        response.reset();
        response.setHeader("Content-Disposition", "filename=" + fileName);
        response.setContentType("image/*");

        return response;
    }

    private static void dealFileToResponse(String fileUrl, HttpServletResponse response, FastFileStorageClient fastFileStorageClient) throws IOException {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path  = fileUrl.substring(fileUrl.indexOf("/") + 1);

        fastFileStorageClient.downloadFile(group, path, inputStream -> {
            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(response.getOutputStream());
                FileCopyUtils.copy(inputStream, bos);
            } catch (IOException e) {
                log.debug(e.getMessage(), e);
            } finally {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != bos) {
                    bos.close();
                }
            }
            return null;
        });
    }

    private static String getFileName(String fileName, HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        try {
            if (header.toLowerCase().indexOf("firefox") > 0) {
                fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
            } else if (header.toUpperCase().indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            log.debug("文件名转码失败！", e);
        }
        return fileName;
    }

    public static boolean checkRequestRefer(String refer, String allowIP) {
        String[] allowIPs = allowIP.split(",");
        boolean  allowIn  = false;
        for (String allow : allowIPs) {
            if (refer != null && refer.indexOf(allow) > -1) {
                allowIn = true;
                break;
            }
        }

        return allowIn;
    }
}
