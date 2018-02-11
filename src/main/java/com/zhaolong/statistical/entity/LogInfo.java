package com.zhaolong.statistical.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
public class LogInfo {

    @Id
    @GeneratedValue
    private Integer logId;
    @ManyToOne
    private UserInfo userInfo;
    @CreatedDate
    private Date uploadTime;
    private String fileName;
    private String filePath;



}
