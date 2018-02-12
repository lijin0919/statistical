package com.zhaolong.statistical.entity;

import lombok.Data;

import java.util.Date;

/**
 * 商务通数据
 */
@Data
public class BusinessInfo {
    private String key;
    private String channel;
    private Date date;
}
