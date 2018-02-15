package com.zhaolong.statistical.entity;

import lombok.Data;

@Data
public class ExportInfo {

    private String key;
    private Double totalMoney;
    private Integer click;
    private Integer dialog;
    private Integer phone;
    private Integer visit;
    private Integer register;

}
