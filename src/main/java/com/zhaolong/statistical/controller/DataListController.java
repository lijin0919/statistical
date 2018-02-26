package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.repository.DataListRepository;
import com.zhaolong.statistical.service.DateListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class DataListController {

    @Autowired
    private DateListService dateListService;

    @Autowired
    private HttpSession session;

    @PostMapping("/showData")
    public String showList(@RequestParam(value ="start-date",required = false) String start,
                           @RequestParam(value = "end-date",required = false) String end,
                           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                           Model model) throws ParseException {

        if((start == null||start.equalsIgnoreCase(""))||
                (end == null||start.equalsIgnoreCase(""))){

            Calendar cal   =   Calendar.getInstance();
            cal.add(Calendar.DATE,   -1);
            String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = simpleDateFormat.parse(yesterday);
            Date endDate = simpleDateFormat.parse(yesterday);
            session.setAttribute("start", startDate);
            session.setAttribute("end", endDate);

            session.setAttribute("page", 0);
            Sort sort = new Sort(Sort.Direction.DESC, "recordDate");
            Pageable pageable = new PageRequest(page, 10, sort);
            List<KeywordsRecord> list = dateListService.getKeyWords(startDate, endDate, pageable);
            model.addAttribute("list", list);
            System.out.println(list);
            return "数据汇总";
        }


        System.out.println(start);
        System.out.println(end);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = simpleDateFormat.parse(start);
        Date endDate = simpleDateFormat.parse(end);

        session.setAttribute("start", startDate);
        session.setAttribute("end", endDate);

        session.setAttribute("page", 0);
        Sort sort = new Sort(Sort.Direction.DESC, "recordDate");
        Pageable pageable = new PageRequest(page, 10, sort);
        List<KeywordsRecord> list = dateListService.getKeyWords(startDate, endDate, pageable);
        model.addAttribute("list", list);
        System.out.println(list);
        return "数据汇总";
    }

    @GetMapping("/next")
    public String next(Model model) {
        int p = (int) session.getAttribute("page");
        int page = p + 1;
        session.setAttribute("page", page);
        Date start = (Date) session.getAttribute("start");
        Date end = (Date) session.getAttribute("end");
        Sort sort = new Sort(Sort.Direction.DESC, "recordDate");
        Pageable pageable = new PageRequest(page, 10, sort);
        List<KeywordsRecord> list = dateListService.getKeyWords(start, end, pageable);
        model.addAttribute("list", list);

        return "数据汇总";
    }

    @GetMapping("/pre")
    public String pre(Model model){
        int p = (int) session.getAttribute("page");
        int page = p - 1;
        if(page<0){
            page = 0;
        }
        session.setAttribute("page", page);
        Date start = (Date) session.getAttribute("start");
        Date end = (Date) session.getAttribute("end");
        Sort sort = new Sort(Sort.Direction.DESC, "recordDate");
        Pageable pageable = new PageRequest(page, 10, sort);
        List<KeywordsRecord> list = dateListService.getKeyWords(start, end, pageable);
        model.addAttribute("list", list);
        return "数据汇总";
    }
    @Autowired
    private DataListRepository dataListRepository;
    @GetMapping("/deleteRecord")
    public String deleteRecord(@RequestParam("id") Integer id,
                               Model model){

        Integer mId = id;
        dataListRepository.delete(id);
        Date start = (Date) session.getAttribute("start");
        Date end = (Date) session.getAttribute("end");
        int p = (int) session.getAttribute("page");
        Sort sort = new Sort(Sort.Direction.DESC, "recordDate");
        Pageable pageable = new PageRequest(p, 10, sort);
        List<KeywordsRecord> list = dateListService.getKeyWords(start, end, pageable);
        model.addAttribute("list", list);
        return "数据汇总";
    }
}
