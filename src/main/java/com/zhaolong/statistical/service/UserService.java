package com.zhaolong.statistical.service;

import com.zhaolong.statistical.entity.UserInfo;
import com.zhaolong.statistical.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;


    public UserInfo login(String userName, String pwd){
        List<UserInfo> list = userInfoRepository.findByUsernameAndPassword(userName,pwd);
        if(list.size()>0){
            return list.get(0);
        }else {
            return null;
        }

    }
}
