package com.usian.feign;

import com.usian.pojo.CartItem;
import com.usian.vo.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("usian-order-service")
public interface OrderFeign {

    @RequestMapping("/goSettlement")
    public List<CartItem> goSettlement(@RequestParam("ids")String [] ids, @RequestParam("userId") String userId , @RequestParam("token") String token);

    @RequestMapping("/insertOrder")
    String insertOrder(@RequestBody  OrderVo orderVo);

}
