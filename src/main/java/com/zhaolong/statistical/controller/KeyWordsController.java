package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.service.KeyWordsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class KeyWordsController {

    @Autowired
    private KeyWordsCodeService keyWordsCodeService;

    @PostMapping("savekeyword")
    public String saveKeyWords(@RequestParam("keywords") String keyword,
                               @RequestParam("keycode") String keycode,
                               @RequestParam("keychannel") String keychannel){
        keyWordsCodeService.saveKeyWords(keyword,keycode,keychannel);
        return "redirect:keyWords";
    }

    @GetMapping("/keyWords")
    public String keyWords(Model model){

        List<KeywordsCode> list = keyWordsCodeService.getKeywordsCodeList();
        model.addAttribute("list",list);
        return "关键词维护";
    }
}
