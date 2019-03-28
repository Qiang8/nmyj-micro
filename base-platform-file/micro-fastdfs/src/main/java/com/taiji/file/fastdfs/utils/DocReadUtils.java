package com.taiji.file.fastdfs.utils;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.CollectionUtils;

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
     * @return List<String> 读取文档内容的集合
     */
    public static List<String> readDocListContent(List<String> uploadFilePaths,FastFileStorageClient storageClient) throws Exception{
        List<String> contents = new ArrayList<>();
        if(!CollectionUtils.isEmpty(uploadFilePaths)){
            for(String uploadFilePath : uploadFilePaths){

                String group = uploadFilePath.substring(0, uploadFilePath.indexOf("/")); //groupName
                String path  = uploadFilePath.substring(uploadFilePath.indexOf("/") + 1); //filePath
                InputStream stream = storageClient.downloadFile(group,path,inputStream -> {
                    return inputStream;
                });
                Boolean isDoc = uploadFilePath.endsWith(".doc");
                Boolean isDocx = uploadFilePath.endsWith(".docx");
                String buffer = "";
                if(isDoc){
                    WordExtractor ex = new WordExtractor(stream);
                    buffer = ex.getText();
                    ex.close();
                    stream.close();
                }else if(isDocx){
                    XWPFDocument xdoc = new XWPFDocument(stream);
                    XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                    buffer = extractor.getText();
                    extractor.close();
                    stream.close();
                }else{
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
