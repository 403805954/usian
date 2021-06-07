package com.usian.service.impl;

import com.usian.mapper.TbUserMapper;
import com.usian.pojo.TbUser;
import com.usian.service.SsoService;
import com.usian.util.*;
import org.apache.catalina.webresources.JarWarResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class SsoServiceImpl implements SsoService {


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private StringUtils stringUtils;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public boolean checkUserInfo(String checkValue, Integer checkFlag) {
        //根据状态判断查询校验用户名 or 手机号
        TbUser user = new TbUser();
        if (checkFlag == 1) {
            user.setUsername(checkValue);
        } else {
            user.setPhone(checkFlag.toString());
        }
        List<TbUser> select = tbUserMapper.select(user);
        if (select.size() > 0 || select != null) {
            return true;
        }
        return false;
    }

    //    用户注册
    @Override
    public void userRegister(TbUser tbUser) {
        tbUser.setCreated(new Date());
        String digest = MD5Utils.digest(tbUser.getPassword());
        tbUser.setPassword(digest);
        tbUserMapper.insertSelective(tbUser);
    }

    //    登录
    @Override
    public Map userLogin(String username, String password) {
        Map map = new HashMap();
        TbUser tbUser = new TbUser();
        tbUser.setUsername(username);
        List<TbUser> select = tbUserMapper.select(tbUser);
        if (select != null || select.size() > 0) {
            if (select.get(0).getPassword().equals(MD5Utils.digest(password))) {
                String public_key = (String) redisClient.get("PRIVATE_KEY");
                byte[] bytes = stringUtils.toByteArray(public_key);
                try {
                    String token = jwtUtils.generateToken(select.get(0), bytes, 60 * 60);
                    map.put("token", token);
                    map.put("userid", select.get(0).getId());
                    map.put("username", select.get(0).getUsername());
                    map.put("flag", true);
                    return map;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                map.put("flag", "密码错误");
            }
        } else {
            map.put("flag", false);
            map.put("msg", "用户名重复");
        }
        return null;
    }

    /**
     * 根据token判断用户是否过期
     *
     * @param token
     * @return TbUser tbUser = JwtUtils.getInfoFromToken(token, StringUtils.toByteArray( publicKey));
     */
    @Override
    public Boolean getUserByToken(String token) {
        String publicKey = (String) redisClient.get("PUBLIC_KEY");
        try {
            TbUser user = jwtUtils.getInfoFromToken(token, stringUtils.toByteArray(publicKey));
            if (user == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
