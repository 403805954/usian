package com.usian.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.sun.org.apache.regexp.internal.RE;
import com.usian.constant.Constant;
import com.usian.mapper.TbItemMapper;
import com.usian.mapper.TbUserMapper;
import com.usian.pojo.CartItem;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbUser;
import com.usian.util.JsonUtils;
import com.usian.util.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {
    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private RedisClient redisClient;

    public void addItem(Long userId, Long itemId) {

        Boolean exists = redisClient.exists(Constant.COOKIENAME + userId);
        //判断当前用户 是否添加过商品
        if (exists) {
            //已登录 根据 商品ID查商品信息 存Redis Hash
            CartItem cartItem1 = GetCart(itemId);
            //查询当前用户是否已经添加过该商品
            CartItem cartItem = (CartItem) redisClient.hget(Constant.COOKIENAME + userId, itemId + "");
            if (cartItem != null) {
                cartItem.setNum(cartItem1.getNum() + 1);
                redisClient.hset(Constant.COOKIENAME + userId, itemId + "", cartItem);
            } else {
                //当前用户没有添加过该商品到购物车
                redisClient.hset(Constant.COOKIENAME + userId, itemId + "", cartItem1);
            }
            // key Cart+userID value :查出的CartItem 对象
        } else {
            redisClient.hset(Constant.COOKIENAME + userId, itemId + "", GetCart(itemId));
        }
    }


    public CartItem GetCart(Long itemId) {
        CartItem cartItem = new CartItem();
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        cartItem.setId(tbItem.getId());
        cartItem.setImage(tbItem.getImage());
        cartItem.setNum(1);
        cartItem.setPrice(tbItem.getPrice());
        cartItem.setSellPoint(tbItem.getSellPoint());
        cartItem.setTitle(tbItem.getTitle());
        return cartItem;
    }


    /**
     * 根据 用户ID查Redis 返回
     *
     * @param userId
     * @return
     */
    public List<CartItem> showCart(Long userId) {
        return redisClient.hGetAll(Constant.COOKIENAME+userId.toString());
    }


    /**
     * 登录后将 未登录时Cookid中的购物车信息 加入Redis
     *
     * @param cookieValue
     */
    public void addCartRedis(String cookieValue, String username) {
        Map<Object, CartItem> map = JsonUtils.jsonToMap(cookieValue, String.class, CartItem.class);
        Collection<CartItem> values = map.values();
        TbUser tbUser = new TbUser();
        tbUser.setUsername(username);
        TbUser tbUser1 = tbUserMapper.selectOne(tbUser);
        for (CartItem value : values) {
            redisClient.hset(Constant.COOKIENAME + tbUser1.getId(), value.getId() + "", value);

        }

//        for (CartItem value : values) {
//
//        }
    }


    /**
     * 修改购物车数量
     *
     * @param userId
     * @param itemId
     * @param num
     */
    public void updateItemNum(Long userId, Long itemId, Integer num) {
        CartItem cartItem = (CartItem) redisClient.hget(Constant.COOKIENAME + userId, itemId + "");
        cartItem.setNum(num);
        redisClient.hset(Constant.COOKIENAME + userId, itemId + "", cartItem);
    }


    /**
     * 删除Redis中的购物车中的商品
     *
     * @param userId
     * @param itemId
     */
    public void deleteItemFromCart(Long userId, Long itemId) {
        redisClient.hdel(Constant.COOKIENAME + userId, itemId + "");
    }
}
