package com.usian.controller;


import com.fasterxml.jackson.databind.node.LongNode;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.sun.org.apache.regexp.internal.RE;
import com.usian.constant.Constant;
import com.usian.feign.CartFeign;
import com.usian.feign.ItemFeign;
import com.usian.pojo.CartItem;
import com.usian.pojo.TbItem;
import com.usian.util.CookieUtils;
import com.usian.util.JsonUtils;
import com.usian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/frontend/cart")
public class CartWebController {

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private ItemFeign itemFeign;

    /**
     * 查询购物车商品详情
     *
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping("/showCart")
    public Result showCart(@RequestParam("userId") Long userId, HttpServletRequest request) {
        if (userId == null) {
            Collection values = null;
            //未登录 从Cookie取
            String cookieValue = CookieUtils.getCookieValue(request, Constant.COOKIENAME, true);
            if (cookieValue!=null || !cookieValue.equals("")){
                Map map = JsonUtils.jsonToMap(cookieValue, Long.class, CartItem.class);
                 values = map.values();
                return Result.ok(values);
            }else {
                return Result.ok(values);
            }

        } else {
            //已登录 cong Redis 获取
            List<CartItem> cartItems = cartFeign.showCart(userId);
            return Result.ok(cartItems);
        }
    }


    /**
     * 添加购物车
     *
     * @param userId
     * @param itemId
     * @return
     */
    @RequestMapping("/addItem")
    public Result addItem(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId, HttpServletResponse response, HttpServletRequest request) {
        try {
            //判断是否登录 如果没登录存coockie 如果登录存redis;
            //根据userid判断
            if (userId == null) {//没有登录 存cookie
                //判断用户是否为第一次登录
                String cart = CookieUtils.getCookieValue(request, Constant.COOKIENAME, true);
                if (cart == null || cart.equals("")) {
                    //是第一次 登录
                    Map<Long, CartItem> map = new HashMap<>();
                    addCart(map, itemId);
                    CookieUtils.setCookie(request, response, Constant.COOKIENAME, JsonUtils.objectToJson(map), true);
                } else { //不是第一次登录  获取 之前的 Cookie  转换成Map 往里面追加信息就可以
                    Map<Long, CartItem> oldCar = JsonUtils.jsonToMap(cart, Long.class, CartItem.class);

                    //判断 当前商品是否存入过Cookei
                    CartItem cartItem = oldCar.get(itemId);
                    if (cartItem == null) {
                        addCart(oldCar, itemId);
                    } else {
                        cartItem.setNum(cartItem.getNum() + 1);
                    }
                    //最后将信息存入Cookie
                    CookieUtils.setCookie(request, response, Constant.COOKIENAME, JsonUtils.objectToJson(oldCar), true);
                }

            } else {//已登录 去Service存Redis
                cartFeign.addItem(userId, itemId);
                return Result.ok();
            }
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("加入购物车失败");
        }
    }


    /**
     * 向Cookie中存入指定用户的信息
     *
     * @param map
     * @param itemId
     */
    public void addCart(Map map, Long itemId) {
        CartItem cartItem = new CartItem();
        TbItem tbItem = itemFeign.selectItemInfo(itemId);
        cartItem.setId(tbItem.getId());
        cartItem.setImage(tbItem.getImage());
        cartItem.setNum(1);
        cartItem.setPrice(tbItem.getPrice());
        cartItem.setSellPoint(tbItem.getSellPoint());
        cartItem.setTitle(tbItem.getTitle());
        map.put(tbItem.getId(), cartItem);
    }


    /**
     * 更改购物车商品
     *
     * @param userId
     * @param itemId
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("/updateItemNum")
    public Result updateItemNum(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId, @RequestParam("num") Integer num, HttpServletResponse response, HttpServletRequest request) {
        //判断是否为登陆状态
        if (userId == null) {
            //未登录 修改 Cookie
            String cookieValue = CookieUtils.getCookieValue(request, Constant.COOKIENAME, true);
            Map<Long, CartItem> map = JsonUtils.jsonToMap(cookieValue, Long.class, CartItem.class);
            CartItem cartItem = map.get(itemId);
            cartItem.setNum(num);
            CookieUtils.setCookie(request, response, Constant.COOKIENAME, JsonUtils.objectToJson(map), true);
            return Result.ok();
        } else {
            //已登录去Redis修改
            cartFeign.updateItemNum(userId, itemId, num);
        }
        return Result.ok();

    }

    /**
     * 删除购物车接口
     *
     * @param userId
     * @param itemId
     * @return
     */
    @RequestMapping("/deleteItemFromCart")
    public Result deleteItemFromCart(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId, HttpServletResponse response, HttpServletRequest request) {
        if (userId == null) {

            String cart = CookieUtils.getCookieValue(request, Constant.COOKIENAME, true);

            Map<Long, CartItem> oldCar = JsonUtils.jsonToMap(cart, Long.class, CartItem.class);
            CartItem cartItem = oldCar.get(itemId);
            Map<Long, CartItem> map = new HashMap<>();
            map.put(itemId, cartItem);
            CookieUtils.setCookie(request, response, Constant.COOKIENAME, JsonUtils.objectToJson(map), -1, true);
            if (map.size() == 1) {
                //未登录 删Cookie
                CookieUtils.deleteCookie(request, response, Constant.COOKIENAME);
                return Result.ok();
            }

        } else {
            //已登录 删Redids
            cartFeign.deleteItemFromCart(userId, itemId);
        }
        return Result.ok();

    }


}
