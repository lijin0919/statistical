package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.service.KeyWordsCodeService;
import com.zhaolong.statistical.util.ExcelImportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        Sort sort = new Sort(Sort.Direction.DESC, "codeId");
        Pageable pageable = new PageRequest(page, 10,sort);
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

    @PostMapping("/uploadKey")
    public String uploadKey(@RequestParam(value="filename") MultipartFile file,
                            Model model){
        //判断文件是否为空
        if(file==null){

        }
        //获取文件名
        String fileName=file.getOriginalFilename();
        //验证文件名是否合格
        if(!ExcelImportUtils.validateExcel(fileName)){

        }

        //进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if(StringUtils.isEmpty(fileName) || size==0){

        }
        keyWordsCodeService.uploadKey(fileName,file);
        model.addAttribute("success","上传成功");
        return "redirect:keyWords";
    }
}
