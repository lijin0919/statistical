package com.zhaolong.statistical.entity;

import lombok.Data;

@Data
public class ExportInfo {

    private String key;//核心词
    private Double totalMoney;//费用
    private Integer click;//点击
    private Integer dialog;//对话
    private Integer phone;//信息
    private Integer visit;//上门
    private Integer register;//注册
    private Integer show;

}
