package com.zhaolong.statistical.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
public class UserChannel {

    @Id
    @GeneratedValue
    private Integer channelId;
    private String channel;
    @ManyToOne
    private UserInfo userInfo;
}
