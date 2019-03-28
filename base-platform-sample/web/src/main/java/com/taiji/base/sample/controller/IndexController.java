package com.taiji.base.sample.controller;

import com.taiji.base.sample.vo.MenuVo;
import com.taiji.base.sample.vo.MessageVo;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录后首页
 *
 * @author scl
 * @date 2018-03-06
 */
@Controller
public class IndexController{
    /**
     * 首页
     */
    @RequestMapping("/index.action")
    public String index(ModelMap modelMap, HttpServletRequest request){
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

        modelMap.addAttribute("basePath",basePath);

        List<MessageVo> messageList = new ArrayList<>(4);
        messageList.add(new MessageVo("js/ace/assets/avatars/avatar.png","Alex's Avatar","Alex","Ciao sociis natoque penatibus et auctor ..."));
        messageList.add(new MessageVo("js/ace/assets/avatars/avatar3.png","Susan's Avatar","Susan","Vestibulum id ligula porta felis euismod ..."));
        messageList.add(new MessageVo("js/ace/assets/avatars/avatar2.png","Bob's Avatar","Bob","Nullam quis risus eget urna mollis ornare ..."));
        messageList.add(new MessageVo("js/ace/assets/avatars/avatar5.png","Kate's Avatar","Kate","Ciao sociis natoque eget urna mollis ornare ..."));

        modelMap.addAttribute("messageCount",messageList.size());
        modelMap.addAttribute("messages",messageList);

        List<MenuVo> menuList = new ArrayList<>();
        menuList.add(new MenuVo("page/index-main.action","page/index-main.action","工作台","fa fa-tachometer",""));

        MenuVo menuVo = new MenuVo("","","Tables","fa fa-list","");
        MenuVo gridMenuVo = new MenuVo("page/sample/sample-list.action","page/sample/sample-list.action","jqGrid 样例","fa fa-desktop","");

        menuVo.addMenu(gridMenuVo);
        menuList.add(menuVo);

        modelMap.addAttribute("menus",menuList);

        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        modelMap.addAttribute("tokenParameter", token.getParameterName());
        modelMap.addAttribute("tokenValue", token.getToken());
        modelMap.addAttribute("tokenHeaderName", token.getHeaderName());

        return "index";
    }

    /**
     * 首页局部刷新部分
     */
    @RequestMapping("/index-main.action")
    public String indexMain(ModelMap modelMap){
        return "index-main";
    }
}
