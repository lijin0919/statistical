package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.service.BusinessService;
import com.zhaolong.statistical.util.ExcelImportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PostMapping("/uploadBusiness")
    public String upload(@RequestParam(value="filename") MultipartFile file){
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
        return "上传商务通数据";
    }
}
