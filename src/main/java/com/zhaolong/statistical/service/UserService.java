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

    /**
     * 注册
     * @param name
     * @param pwd
     * @return
     */
    public String register(String name,String pwd){
        List<UserInfo> userInfoList = userInfoRepository.findByUsername(name);
        if(userInfoList.size()>0){
            return "用户名已存在";
        }else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(name);
            userInfo.setPassword(pwd);
            userInfoRepository.save(userInfo);
            return "注册成功";
        }
    }

    /**
     * 账户信息
     * @return
     */
    public List<UserInfo> getUserList(){
        return userInfoRepository.findAll();
    }

    /**
     * 登录
     * @param userName
     * @param pwd
     * @return
     */
    public UserInfo login(String userName, String pwd){
        List<UserInfo> list = userInfoRepository.findByUsernameAndPassword(userName,pwd);
        if(list.size()>0){
            return list.get(0);
        }else {
            return null;
        }

    }
}
