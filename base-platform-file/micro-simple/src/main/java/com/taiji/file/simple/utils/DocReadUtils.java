package com.taiji.file.simple.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
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
    public static List<String> readDocListContent(List<String> uploadFilePaths,String uploadServerPath) throws Exception{
        List<String> contents = new ArrayList<>();
        if(!CollectionUtils.isEmpty(uploadFilePaths)){
            for(String uploadFilePath : uploadFilePaths){
                String savePath = uploadServerPath + "/" + uploadFilePath;
                File savePathCatergory = new File(savePath);
                if (!savePathCatergory.exists()) {
                    continue;
                }
                String buffer = "";
                Boolean isDoc = uploadFilePath.endsWith(".doc");
                Boolean isDocx = uploadFilePath.endsWith(".docx");
                if (isDoc) {
                    InputStream is = new FileInputStream(savePathCatergory);
                    WordExtractor ex = new WordExtractor(is);
                    buffer = ex.getText();
                    ex.close();
                } else if (isDocx) {
                    OPCPackage opcPackage = POIXMLDocument.openPackage(savePath);
                    POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                    buffer = extractor.getText();
                    extractor.close();
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
