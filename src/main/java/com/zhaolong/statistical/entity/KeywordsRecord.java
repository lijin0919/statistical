package com.zhaolong.statistical.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class KeywordsRecord {

    @Id
    @GeneratedValue
    private Integer recordId;
    @ManyToOne
    private KeywordsCode keywordsCode;//关键词代码
    private Date recordDate;//日期
    private String searchEngine;//搜索引擎
    private Integer showTimes;//展示
    private Integer clickCount;//点击
    private Double spendMoney;//消费
    private Integer dialogueCount;//对话
    private Integer phoneCount;//电话
    private Integer visitCount;//上门
    private Integer registeredCount;//注册
}
