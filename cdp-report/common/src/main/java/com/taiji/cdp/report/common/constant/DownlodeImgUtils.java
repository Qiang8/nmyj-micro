package com.taiji.cdp.report.common.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class DownlodeImgUtils implements Runnable {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private String url;
    private String fileName;
    //路径
    private String path;

    public DownlodeImgUtils(String url, String fileName, String path) {
        super();
        this.url = url;
        this.fileName = fileName;
        this.path = path;
    }


    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        LOGGER.debug(name + "  开始下载图片...");
        try {
            URL imageURL = new URL(url);

            InputStream is = imageURL.openStream();
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }

            File imgFile = new File(f, fileName);
            FileOutputStream fos = new FileOutputStream(imgFile);

            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            byte[] tmp = new byte[1024];
            int len = -1;
            while ((len = bis.read(tmp)) != -1) {
                bos.write(tmp, 0, len);
            }
            bos.close();
            bis.close();
            fos.close();

            LOGGER.debug(name + " 图片下载结束...");
        } catch (MalformedURLException e) {
            LOGGER.error("图片下载失败，地址为 = " + url);
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("图片下载失败，地址为 = " + url);
            e.printStackTrace();
        }

    }
}
