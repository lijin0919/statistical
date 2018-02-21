package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.UserInfo;
import com.zhaolong.statistical.service.*;
import com.zhaolong.statistical.util.ExcelImportUtils;
import com.zhaolong.statistical.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UploadController {

    @Autowired
    private UploadBaiduPcService uploadBaiduPcService;

    @Autowired
    private HttpSession session;

    @Autowired
    private BaiduPhoneService baiduPhoneService;

    @Autowired
    private Pc360Service pc360Service;

    @Autowired
    private Phone360Service phone360Service;

    @Autowired
    private SmPcService smPcService;

    @Autowired
    private SougouPcService sougouPcService;

    @PostMapping("/batchImport")
    public String baiduPc(@RequestParam(value="filename") MultipartFile file){

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
        String channel = (String) session.getAttribute("channel");
        switch (channel){
            case "百度PC":
                uploadBaiduPcService.batchImport(fileName,file);
                break;
            case "百度移动":
                baiduPhoneService.batchImport(fileName,file);
                break;
            case "360PC":
                pc360Service.batchImport(fileName,file);
                break;
            case "360移动":
                phone360Service.batchImport(fileName,file);
                break;
            case "搜狗PC":
                sougouPcService.batchImport(fileName,file);
                break;
            case "搜狗移动":
                break;
            case "神马":
                smPcService.batchImport(fileName,file);
                break;

        }
        //批量导入

        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        LogUtil.printLog(userInfo.getUsername()+"上传了"+fileName);
        return "上传推广数据";

    }

    /**
     * 上传推广数据选择的渠道
     * @param channel
     * @param request
     * @return
     */
    @GetMapping("/channel")
    @ResponseBody
    public String getChannelType(@RequestParam("channel")String channel, HttpServletRequest request){
        request.getSession().setAttribute("channel",channel);
        System.out.println(channel);
        return "success";
    }


}
