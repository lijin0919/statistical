package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.UserInfo;
import com.zhaolong.statistical.service.CustomerService;
import com.zhaolong.statistical.util.ExcelImportUtils;
import com.zhaolong.statistical.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@Controller
public class CustomerController {

    @Autowired
    private HttpSession session;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/uploadCustomer")
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
        customerService.readExcel(fileName,file);
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        LogUtil.printLog(userInfo.getUsername()+"上传了客服数据"+fileName);
        model.addAttribute("success","上传成功");
        return "上传客服数据";
    }

    @GetMapping("/downCustomer")
    public void download(HttpServletResponse response) throws IOException {
        response.setContentType("multipart/form-data");
        //设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=template.xlsx");

        OutputStream out = response.getOutputStream();

        String path = "E://客服数据.xlsx";
        InputStream in = new FileInputStream(new File(path));

        int b;
        while((b=in.read())!=-1){
            out.write(b);
        }
        in.close();
        out.close();
    }
}
