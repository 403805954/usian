package com.usian.controller;

import com.netflix.discovery.converters.Auto;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.usian.constant.Constant;
import com.usian.feign.CartFeign;
import com.usian.feign.SsoFeign;
import com.usian.pojo.CartItem;
import com.usian.pojo.TbUser;
import com.usian.util.CookieUtils;
import com.usian.util.JsonUtils;
import com.usian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/frontend/sso")
public class SsoController {

    @Autowired
    private SsoFeign ssoFeign;

    @Autowired
    private CartFeign cartFeign;

    /**
     * 校验手机号或用户名是否存在
     *
     * @param checkValue
     * @param checkFlag
     * @return
     */
    @RequestMapping("/checkUserInfo/{checkValue}/{checkFlag}")
    public Result checkUserInfo(@PathVariable(name = "checkValue") String checkValue, @PathVariable(name = "checkFlag") Integer checkFlag) {
        try {
            Boolean a = ssoFeign.checkUserInfo(checkValue, checkFlag);
            if (a) {
                return Result.ok();
            } else {
                return Result.error("校验失败");
            }

        } catch (Exception e) {
            return Result.error("×");
        }
    }


    //用户注册
    @RequestMapping("/userRegister")
    public Result userRegister(TbUser tbUser) {
        try {
            ssoFeign.userRegister(tbUser);
            return Result.ok();
        } catch (Exception e) {
            return Result.error("注册失败");
        }
    }


    //  登录接口
    @RequestMapping("/userLogin")
    public Result userLogin(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, HttpServletRequest request) {
//        try {
        Map user = ssoFeign.userLogin(username, password);
        if (user != null) {
            String cookieValue = CookieUtils.getCookieValue(request, Constant.COOKIENAME, true);
            if (cookieValue != null || !cookieValue.equals("")) {


                //Map<Object, CartItem> map = JsonUtils.jsonToMap(cookieValue, String.class, CartItem.class);
                cartFeign.addCartRedis(cookieValue, username);
            }
            return Result.ok(user);
        } else {
            return Result.error("登录失败");
        }

//        } catch (Exception e) {
//            return Result.error("登录失败");
//        }
    }


    /**
     * 查询用户登录是否过期
     *
     * @param token
     * @return
     */
    @RequestMapping("/getUserByToken/{token}")
    public Result getUserByToken(@PathVariable String token) {
        Boolean user = ssoFeign.getUserByToken(token);
        if (user) {
            return Result.ok();
        }
        return Result.error("用户已过期");
    }


    @RequestMapping("/logOut")
    public Result logOut(@RequestParam("token") String token) {
        try {
//            ssoFeign.logOut(token);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("退出失败");
        }
    }

}