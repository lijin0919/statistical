package com.zhaolong.statistical.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Data
public class KeywordsCode {

    @Id
    @GeneratedValue
    private Integer codeId;
    private String keyWords;
    private String code;
    private String searchEngine;
    private Integer state = 1;
    @Transient
    private Integer no;
    @CreatedDate
    private Date date;
}
