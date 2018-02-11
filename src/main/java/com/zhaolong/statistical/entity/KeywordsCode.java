package com.zhaolong.statistical.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class KeywordsCode {

    @Id
    @GeneratedValue
    private Integer codeId;
    private String keyWords;
    private String code;
    private String searchEngine;
}