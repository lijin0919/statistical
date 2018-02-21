package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.service.KeyWordsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private HttpSession session;

    @GetMapping("/index")
    public String index() {
        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "上传推广数据";
    }


    @GetMapping("/business")
    public String businessInfo() {

        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "上传商务通数据";
    }

    @GetMapping("/dataList")
    public String dataList(){
        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "数据汇总";
    }

    @GetMapping("/export")
    public String export(){
        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "导出";
    }

    @GetMapping("/customer")
    public String uploadService(){

        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "上传客服数据";
    }

    @GetMapping("/userAdmin")
    public String userAdmin(){

        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "redirect:userList";
    }

    @GetMapping("/login")
    public String login(){
        return "index";
    }
}
