package com.zhaolong.statistical.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerInfo {

    private Date date;
    private String key;
    private String channel;
    private Integer phone;
    private Integer visit;
    private Integer register;
}
