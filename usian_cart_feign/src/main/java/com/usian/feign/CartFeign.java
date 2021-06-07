package com.usian.feign;

import com.usian.pojo.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "usian-cart-service")
public interface CartFeign {

    @RequestMapping("/addItem")
    public void addItem(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId);

    @RequestMapping("/showCart")
    public List<CartItem> showCart(@RequestParam("userId") Long userId);

    @RequestMapping("/updateItemNum")
    public void updateItemNum(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId, @RequestParam("num") Integer num);

    @RequestMapping("/deleteItemFromCart")
    public void deleteItemFromCart(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId);

    @RequestMapping("/addCartRedis")
    void addCartRedis(@RequestParam String cookieValue, @RequestParam String username);

//    @RequestMapping("/addCartRedis")
//    public void addCartRedis(@RequestParam Map<Long,CartItem> map,@RequestParam String username);
}
