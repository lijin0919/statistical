package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.service.KeyWordsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class KeyWordsController {

    @Autowired
    private HttpSession session;
    @Autowired
    private KeyWordsCodeService keyWordsCodeService;

    @PostMapping("savekeyword")
    public String saveKeyWords(@RequestParam("keywords") String keyword,
                               @RequestParam("keycode") String keycode,
                               @RequestParam("keychannel") String keychannel) {
        keyWordsCodeService.saveKeyWords(keyword, keycode, keychannel);
        return "redirect:keyWords";
    }

    @GetMapping("/keyWords")
    public String keyWords(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                           Model model) {


        if (page < 0) {
            page = 0;
        }
        Pageable pageable = new PageRequest(page, 10);
        List<KeywordsCode> list = keyWordsCodeService.getKeywordsCodeList(pageable);
        model.addAttribute("list", list);
        model.addAttribute("keypage", page);
        return "关键词维护";
    }

    @GetMapping("/delete")
    public String deleteKey(@RequestParam("id") Integer id){

        keyWordsCodeService.deleteKey(id);
        return "redirect:keyWords";
    }
}
