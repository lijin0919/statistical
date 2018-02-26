package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.UserInfo;
import com.zhaolong.statistical.service.BusinessService;
import com.zhaolong.statistical.util.ExcelImportUtils;
import com.zhaolong.statistical.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class BusinessController {

    @Autowired
    private HttpSession session;
    @Autowired
    private BusinessService businessService;

    @PostMapping("/uploadBusiness")
    public String upload(@RequestParam(value="filename") MultipartFile file,
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
        //批量导入
        businessService.batchImport(fileName,file);
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        LogUtil.printLog(userInfo.getUsername()+"上传了商务通数据："+fileName);
        model.addAttribute("success","上传成功");
        return "上传商务通数据";
    }
}
