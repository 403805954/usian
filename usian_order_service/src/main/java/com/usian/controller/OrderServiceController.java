package com.usian.controller;


import com.usian.pojo.CartItem;
import com.usian.service.OrderService;
import com.usian.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class OrderServiceController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/goSettlement")
    public List<CartItem> goSettlement(@RequestParam("ids") String[] ids, @RequestParam("userId") String userId, @RequestParam("token") String token) {
        return orderService.goSettlement(ids, userId, token);
    }

    @RequestMapping("/insertOrder")
    String insertOrder(@RequestBody OrderVo orderVo) {
        return orderService.insertOrder(orderVo);
    }
}
