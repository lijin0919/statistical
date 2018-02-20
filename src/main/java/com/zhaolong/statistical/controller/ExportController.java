package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.repository.DataListRepository;
import com.zhaolong.statistical.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
}
