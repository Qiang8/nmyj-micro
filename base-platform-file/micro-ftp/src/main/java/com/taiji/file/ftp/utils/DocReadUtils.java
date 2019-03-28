package com.taiji.file.ftp.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取文档内容工具类
 */
@Slf4j
public class DocReadUtils {

    /**
     * @param uploadFilePaths 文件路径集合
     * @param uploadServerPath 服务器上传文件基础路径
     * @return List<String> 读取文档内容的集合
     */
    public static List<String> readDocListContent(List<String> uploadFilePaths,String uploadServerPath,FtpRemoteFileTemplate template) throws Exception{
        List<String> contents = new ArrayList<>();
        if(!CollectionUtils.isEmpty(uploadFilePaths)){
            for(String uploadFilePath : uploadFilePaths){
                String savePath = "/" +uploadServerPath + "/" + uploadFilePath;
                Boolean isExist = template.exists(savePath);
                if (!isExist) {
                    continue;
                }
                String buffer = "";
                Boolean isDoc = uploadFilePath.endsWith(".doc");
                Boolean isDocx = uploadFilePath.endsWith(".docx");
                InputStream inputStream = template.getSession().readRaw(savePath);
                if (isDoc) {
                    WordExtractor ex = new WordExtractor(inputStream);
                    buffer = ex.getText();
                    ex.close();
                    inputStream.close();
                } else if (isDocx) {
                    XWPFDocument xdoc = new XWPFDocument(inputStream);
                    XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                    buffer = extractor.getText();
                    extractor.close();
                    inputStream.close();
                } else {
                    continue;
                }
                contents.add(buffer);
            }
        }else{
            return null;
        }
        return contents;
    }

}
