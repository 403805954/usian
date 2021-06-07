package com.usian.service;

import com.usian.pojo.TbUser;

import java.util.Map;

public interface SsoService {
    boolean checkUserInfo(String checkValue, Integer checkFlag);

    void userRegister(TbUser tbUser);

    Map userLogin(String username, String password);

    Boolean getUserByToken(String token);


}
