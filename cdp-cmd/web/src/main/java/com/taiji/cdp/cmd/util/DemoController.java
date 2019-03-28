package com.taiji.cdp.cmd.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/demo")
public class DemoController {

    @GetMapping(path ="/export")
    public void export(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> params = new HashMap<>();
        params.put("title","这是标题");
        params.put("startWebsite","李四");
        ExportUtil.exportWord("static/wordTemp/infoCommandTemp.docx",  //导出word模板位置
                "F:/test"  //临时文件夹地址
                ,"1.docx" //导出文件名
                ,params  //替换参数 ：详细参数在 InfoCommandVo类中
                ,request,response);
    }

}
