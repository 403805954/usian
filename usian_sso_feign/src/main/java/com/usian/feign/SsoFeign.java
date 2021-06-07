package com.usian.feign;

import com.usian.pojo.TbUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "usian-sso-service")
public interface SsoFeign {


    @RequestMapping("/ssoservice/checkUserInfo/{checkValue}/{checkFlag}")
    public boolean checkUserInfo(@PathVariable(name = "checkValue") String checkValue, @PathVariable(name = "checkFlag") Integer checkFlag);

    @RequestMapping("/ssoservice/userRegister")
    public void userRegister(@RequestBody TbUser tbUser);

    @RequestMapping("/ssoservice/userLogin")
    public Map userLogin(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password);

    @RequestMapping("/ssoservice/getUserByToken/{token}")
    public Boolean getUserByToken(@PathVariable String token);

//    @RequestMapping("/ssoservice/logOut")
//    public void logOut(@RequestParam("token") String token);
}
