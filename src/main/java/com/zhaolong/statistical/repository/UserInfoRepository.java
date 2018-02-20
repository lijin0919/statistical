package com.zhaolong.statistical.repository;

import com.zhaolong.statistical.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer>{

    List<UserInfo> findByUsernameAndPassword(String userName,String pwd);
}
