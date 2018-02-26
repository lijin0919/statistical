package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.repository.DataListRepository;
import com.zhaolong.statistical.service.ExportService;
import com.zhaolong.statistical.util.EmailUtil;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ExportController {

    @Autowired
    private DataListRepository dataListRepository;

    @Autowired
    private ExportService exportService;

    @Autowired
    private HttpSession session;

    @PostMapping("/exportexcl")
    public void exportData(@RequestParam("start-date")String start,
                           @RequestParam("end-date")String end,
                           HttpServletResponse response,
                           HttpServletRequest request) throws ParseException, IOException {
        System.out.println(start);
        System.out.println(end);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = simpleDateFormat.parse(start);
        Date endDate = simpleDateFormat.parse(end);
        session.setAttribute("start",startDate);
        session.setAttribute("end",endDate);

        String path = exportService.exportExcl(startDate,endDate);


        response.setContentType("multipart/form-data");
        //设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");

        OutputStream out = response.getOutputStream();

        InputStream in = new FileInputStream(new File(path));

        int b;
        while((b=in.read())!=-1){
            out.write(b);
        }
        in.close();
        out.close();
        //*******************************************



    }



    @GetMapping("/send")
    public void send(HttpServletResponse response,HttpServletRequest request) throws ParseException, IOException, MessagingException {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = (Date) session.getAttribute("start");
        Date endDate = (Date) session.getAttribute("end");

        String path = exportService.exportExcl(startDate,endDate);


        response.setContentType("multipart/form-data");
        //设置Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");

        OutputStream out = response.getOutputStream();

        InputStream in = new FileInputStream(new File(path));

        int b;
        while((b=in.read())!=-1){
            out.write(b);
        }
        in.close();
        out.close();
        EmailUtil.sendFileEmail("yangzhaofeng@xaccp.com","关键词数据","",new File(path));
        //*******************************************



    }
}
