package com.taiji.file.ftp.utils;

import com.taiji.file.common.entity.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.integration.file.remote.SessionCallback;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
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
     * @param uploadServerPath
     *
     * @return
     *
     * @throws Exception
     */
    public static String uploadFile(MultipartFile file, String uploadModule, String uploadType, String uploadServerPath, FtpRemoteFileTemplate template) throws Exception {

        String fileName = "";
        Date   date     = new Date();
        String yyyyMMdd = DateFormatUtils.format(date, "yyyyMMdd");
        String savePath = uploadModule + "/" + yyyyMMdd;

        String   originalFilename = file.getOriginalFilename();
        String[] fileNames        = originalFilename.trim().split("\\.");

        String extension = fileNames[fileNames.length - 1];
        long   fileSize  = file.getSize();

        fileName = savePath + "/" + UUID.randomUUID() + "-" + uploadType + "." + extension;

        log.debug("originalFilename=={},fileSize=={},extension=={},newFileName=={}", originalFilename, fileSize, extension, fileName);

        StringBuffer uploadFileNameStringBuffer      = new StringBuffer();
        StringBuffer uploadFileDirectoryStringBuffer = new StringBuffer();
        final String uploadFileDirectory             = uploadFileDirectoryStringBuffer.append(uploadServerPath).append("/").append(savePath).toString();
        final String uploadFileName                  = uploadFileNameStringBuffer.append(uploadServerPath).append("/").append(fileName).toString();

        if (template != null) {
            template.execute((SessionCallback<FTPFile, Void>) session -> {
                InputStream inputStream = null;
                try {
                    String[] dirs = uploadFileDirectory.split("/");

                    StringBuffer sb = new StringBuffer();
                    for (String dir : dirs) {
                        sb.append(dir);
                        if (!session.exists(sb.toString())) {
                            session.mkdir(sb.toString());
                        }
                        ;
                        sb.append("/");
                    }

                    inputStream = file.getInputStream();
                    session.write(inputStream, uploadFileName);
                    return null;
                } catch (IOException e) {
                    log.debug(e.getMessage(), e);
                    return null;
                } finally {
                    if (null != inputStream) {
                        inputStream.close();
                    }
                    if (null != session) {
                        session.close();
                    }
                }
            });
        } else {
            throw new RuntimeException("FtpRemoteFileTemplate is null");
        }

        return fileName;
    }

    /**
     * 文件下载
     *
     * @param fileName
     * @param path
     * @param response
     * @param request
     * @param uploadServerPath
     *
     * @throws Exception
     */
    public static void downloadFile(String fileName, String path, HttpServletResponse response, HttpServletRequest request, String uploadServerPath, FtpRemoteFileTemplate template) throws Exception {
        response = downloadSetting(fileName, response, request);
        dealFileToResponse(path, response, uploadServerPath, template);
    }

    /**
     * 多附件下载
     *
     * @param attachmentList
     * @param response
     * @param request
     * @param uploadServerPath
     *
     * @throws Exception
     */
    public static void downloadFiles(List<Attachment> attachmentList, HttpServletResponse response, HttpServletRequest request, String uploadServerPath, FtpRemoteFileTemplate template) {
        String downloadFileName = UUID.randomUUID().toString() + ".zip";
        response = downloadSetting(downloadFileName, response, request);

        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(response.getOutputStream());

            for (Attachment attachment : attachmentList) {
                String fileName = attachment.getOriginalName();
                String location = attachment.getLocation();

                zos.putNextEntry(new ZipEntry(UUID.randomUUID().toString() + "-" + fileName));

                ZipOutputStream finalZos = zos;
                template.get(uploadServerPath + "/" + location,
                             inputStream -> {
                                 BufferedOutputStream bos = null;
                                 try {
                                     bos = new BufferedOutputStream(finalZos);
                                     FileCopyUtils.copy(inputStream, finalZos);
                                 } catch (IOException e) {
                                     log.debug(e.getMessage(), e);
                                 } finally {
                                     if (null != inputStream) {
                                         inputStream.close();
                                     }
                                 }
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

    public static void showFile(String fileName, String path, HttpServletResponse response, HttpServletRequest request, String uploadServerPath, FtpRemoteFileTemplate template) throws IOException {
        response = showFileSetting(fileName, response, request);
        dealFileToResponse(path, response, uploadServerPath, template);
    }

    public static void showImage(String fileName, String path, HttpServletResponse response, HttpServletRequest request, String uploadServerPath, FtpRemoteFileTemplate template) throws IOException {
        response = showImageSetting(fileName, response, request);
        dealFileToResponse(path, response, uploadServerPath, template);
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

    private static void dealFileToResponse(String path, HttpServletResponse response, String uploadServerPath, FtpRemoteFileTemplate template) throws IOException {
        template.get(uploadServerPath + "/" + path,
                     inputStream -> {
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
