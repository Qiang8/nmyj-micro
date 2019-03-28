package com.taiji.base.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录验证
 *
 * @author scl
 * @date 2018-03-01
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/to-login.action", method = RequestMethod.GET)
    public String $(Model model) {
        model.addAttribute("applictionTitle", "样例应用");
        return "login";
    }

    /**
     * 用户登录
     */
//    @RequestMapping(value="/login.action", method=RequestMethod.POST)
//    public String login(HttpServletRequest request){
//        String username = request.getParameter("j_username");
//        String password = request.getParameter("j_password");
//
//        //成功登录
//        if (username != null) {
//            //重定向
//            return "redirect:/index.action";
//        } else {
//            return "login";
//        }
//    }

    /**
     * 用户登出
     */
//    @RequestMapping("/logout.action")
//    public String logout(){
//        return "redirect:/";
//    }
}
