package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.repository.DataListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ExportController {

    @Autowired
    private DataListRepository dataListRepository;

    @PostMapping("/exportexcl")
    public String exportData(@RequestParam("start-date")String start,
                             @RequestParam("end-date")String end) throws ParseException {
        System.out.println(start);
        System.out.println(end);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = simpleDateFormat.parse(start);
        Date endDate = simpleDateFormat.parse(end);
        List<KeywordsRecord> list = dataListRepository.findByRecordDateBetween(startDate,endDate);

        return "";
    }
}
