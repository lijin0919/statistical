package com.zhaolong.statistical.controller;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.service.DateListService;
import com.zhaolong.statistical.service.KeyWordsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private HttpSession session;

    @Autowired
    private DateListService dateListService;
    @GetMapping("/index")
    public String index() {
        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "上传推广数据";
    }


    @GetMapping("/business")
    public String businessInfo() {

        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "上传商务通数据";
    }

    @GetMapping("/dataList")
    public String dataList(@RequestParam(value ="start-date",required = false) String start,
                           @RequestParam(value = "end-date",required = false) String end,
                           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                           Model model) throws ParseException {
        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
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
        return "数据汇总";
    }

    @GetMapping("/export")
    public String export(){
        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "导出";
    }

    @GetMapping("/customer")
    public String uploadService(){

        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "上传客服数据";
    }

    @GetMapping("/userAdmin")
    public String userAdmin(){

        if(session.getAttribute("user")==null){
            return "redirect:login";
        }
        return "redirect:userList";
    }

    @GetMapping("/login")
    public String login(){
        return "index";
    }
}
