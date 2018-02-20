package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.UserInfo;
import com.zhaolong.statistical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @PostMapping("/userLogin")
    public String login(@RequestParam("username")String userName,
                        @RequestParam("password")String pwd){
        UserInfo userInfo = userService.login(userName,pwd);
        if (userInfo == null){
            return "redirect:login";
        }else {
            session.setAttribute("user",userInfo);
            return "redirect:index";
        }
    }

    @GetMapping("/logout")
    public String logout(){
        session.setAttribute("user",null);
        return "redirect:login";
    }
}
