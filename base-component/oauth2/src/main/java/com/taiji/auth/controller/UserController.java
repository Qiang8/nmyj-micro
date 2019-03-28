package com.taiji.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * <p>Title:RevokeTokenEndpoint.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/6 17:59</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Controller
public class UserController {
    @RequestMapping(value = "/user/me", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Principal user(Principal principal) {
        System.out.println(principal);
        return principal;
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String login(ModelMap modelMap, HttpServletRequest request) {
//        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//        modelMap.addAttribute("tokenParameter", token.getParameterName());
//        modelMap.addAttribute("tokenValue", token.getToken());
        modelMap.addAttribute("contextPath",request.getContextPath());
        return "login";
    }
}
