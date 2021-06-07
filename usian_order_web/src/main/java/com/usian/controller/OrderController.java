package com.usian.controller;


import com.usian.feign.OrderFeign;
import com.usian.pojo.CartItem;
import com.usian.pojo.TbOrder;
import com.usian.pojo.TbOrderShipping;
import com.usian.util.Result;
import com.usian.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/frontend/order")
public class OrderController {
    @Autowired
    private OrderFeign orderFeign;

    @RequestMapping("/goSettlement")
    public Result goSettlement(@RequestParam("ids") String[] ids, @RequestParam("userId") String userId, @RequestParam("token") String token) {
        List<CartItem> Settlementgoods = orderFeign.goSettlement(ids, userId, token);
        return Result.ok(Settlementgoods);
    }

    @RequestMapping("/insertOrder")
    public Result insertOrder(String orderItem, TbOrderShipping tbOrderShipping, TbOrder tbOrder) {
        try {
            OrderVo orderVo = new OrderVo(orderItem, tbOrderShipping, tbOrder);
            String orderid = orderFeign.insertOrder(orderVo);
            return Result.ok(orderid);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("1022");
        }
    }
}
