package com.usian.controller;


import com.usian.pojo.TbUser;
import com.usian.service.SsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ssoservice")
public class SsoServiceController {

    @Autowired
    private SsoService ssoService;

    @RequestMapping("/checkUserInfo/{checkValue}/{checkFlag}")
    public boolean checkUserInfo(@PathVariable(name = "checkValue") String checkValue, @PathVariable(name = "checkFlag") Integer checkFlag) {
        return ssoService.checkUserInfo(checkValue, checkFlag);
    }

    @RequestMapping("/userRegister")
    public void userRegister(@RequestBody TbUser tbUser) {
        ssoService.userRegister(tbUser);
    }

    @RequestMapping("/userLogin")
    public Map userLogin(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        return ssoService.userLogin(username, password);
    }

    @RequestMapping("/getUserByToken/{token}")
    public Boolean getUserByToken(@PathVariable String token) {
        return ssoService.getUserByToken(token);
    }

//    @RequestMapping("/ssoservice/logOut")
//    public void logOut(@RequestParam("token") String token){
////        ssoService.logOut(token);
//    }
}
