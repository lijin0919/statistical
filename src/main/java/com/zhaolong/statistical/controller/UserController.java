package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.UserInfo;
import com.zhaolong.statistical.repository.UserInfoRepository;
import com.zhaolong.statistical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    @GetMapping("/userList")
    public String getUserList(Model model){
        List<UserInfo> list = userService.getUserList();
        model.addAttribute("userList",list);
        return "账户管理";
    }

    @PostMapping("/userInfoRegister")
    public String register(@RequestParam("username")String name,
                           @RequestParam("password")String pwd,
                           Model model){
        String result = userService.register(name,pwd);
        model.addAttribute("result",result);
        List<UserInfo> list = userService.getUserList();
        model.addAttribute("userList",list);
        return "账户管理";
    }

    @Autowired
    private UserInfoRepository userInfoRepository;
    @GetMapping("/deleteUser")
    public String delete(@RequestParam("id")Integer id){
        userInfoRepository.delete(id);
        return "redirect:userList";
    }
}
