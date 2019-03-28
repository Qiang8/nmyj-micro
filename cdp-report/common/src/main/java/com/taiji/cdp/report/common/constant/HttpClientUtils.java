package com.taiji.cdp.report.common.constant;


import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 后台发送http请求
 *
 * @author xuweikai-pc
 * @date 2019年1月16日16:37:25
 */
public class HttpClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 以form表单形式提交数据，发送post请求
     *
     * @param url       请求地址
     * @param paramsMap 具体数据
     * @return 服务器返回数据
     * @explain 1.请求头：httppost.setHeader("Content-Type","application/x-www-form-urlencoded")
     * 2.提交的数据格式：key1=value1&key2=value2...
     */
    public static Map<String, String> httpPostWithForm(String url, Map<String, String> paramsMap) {
        // 用于接收返回的结果
        String resultData = "";
        try {
            HttpPost post = new HttpPost(url);
            List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
            // 迭代Map-->取出key,value放到BasicNameValuePair对象中-->添加到list中
            for (String key : paramsMap.keySet()) {
                pairList.add(new BasicNameValuePair(key, paramsMap.get(key)));
            }
            UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(pairList, "utf-8");
            post.setEntity(uefe);
            // 创建一个http客户端
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            // 发送post请求
            HttpResponse response = httpClient.execute(post);

            // 状态码为：200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 返回数据：
                resultData = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                logger.error("Http请求post接口失败! Content-Type = application/x-www-form-urlencoded");
                throw new RuntimeException("接口请求失败！");
            }
        } catch (Exception e) {
            logger.error("创建HTTP连接失败!");
            throw new RuntimeException("创建HTTP连接失败！");
        }
        return JSON.parseObject(resultData, Map.class);
    }
}
