package com.zhaolong.statistical.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ExcelInfo {

    private Date date;
    private String keyWords;
    private Integer show;
    private Integer click;
    private Double spend;

}
