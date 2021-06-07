package com.usian.controller;

import com.usian.pojo.CartItem;
import com.usian.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class CartServiceController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/addItem")
    public void addItem(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId) {
        cartService.addItem(userId, itemId);
    }

    @RequestMapping("/showCart")
    public List<CartItem> showCart(@RequestParam("userId") Long userId) {
        return cartService.showCart(userId);
    }

    @RequestMapping("/addCartRedis")
    void addCartRedis(@RequestParam  String cookieValue, @RequestParam String username) {
        cartService.addCartRedis(cookieValue, username);
    }

    @RequestMapping("/updateItemNum")
    public void updateItemNum(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId, @RequestParam("num") Integer num) {
        cartService.updateItemNum(userId, itemId, num);
    }

    @RequestMapping("/deleteItemFromCart")
    public void deleteItemFromCart(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId) {
        cartService.deleteItemFromCart(userId, itemId);
    }


}
