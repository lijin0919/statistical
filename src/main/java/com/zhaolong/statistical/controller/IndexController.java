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
        return "上传推广数据";
    }


    @GetMapping("/business")
    public String businessInfo() {

        return "上传商务通数据";
    }

}
