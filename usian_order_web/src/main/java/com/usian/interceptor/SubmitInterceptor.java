package com.usian.interceptor;

import com.usian.config.SubmitConfig;
import com.usian.feign.SsoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class SubmitInterceptor implements HandlerInterceptor {

    @Autowired
    private SubmitConfig submitConfig;

    @Autowired
    private SsoFeign ssoFeign;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断用户是否登陆过
        //从Request中获取登录痕迹 token
        String token = request.getHeader("token");
        final String[] cookieToken = new String[1];
        if (token == null || token.equals("")) {
            token = request.getParameter("token");
            if (token == null || token.equals("")) {
                Cookie[] cookies = request.getCookies();

                Arrays.asList(cookies).forEach(cookie -> {
                    if (cookie.getName().equals("token")) {
                        cookieToken[0] = cookie.getValue();
                    }
                });
                token = cookieToken[0];
            }
        }

        //用户未携带token 未登录
        if (token == null || token.equals("")) {
            return false;
        }

        //已登录 放行
        Boolean userByToken = ssoFeign.getUserByToken(token);
        if (userByToken) {
            return true;
        }
        return false;
    }
}
