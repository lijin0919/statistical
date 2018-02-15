package com.zhaolong.statistical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class DataListController {

    @PostMapping("/showData")
    public String showList(@RequestParam("start-date")String start,
                           @RequestParam("end-date")String end) throws ParseException {
        System.out.println(start);
        System.out.println(end);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = simpleDateFormat.parse(start);
        Date endDate = simpleDateFormat.parse(end);




        return "数据汇总";
    }
}
