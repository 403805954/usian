package com.usian.service;

import com.usian.pojo.CartItem;
import com.usian.vo.OrderVo;

import java.util.List;

public interface OrderService {
    List<CartItem> goSettlement(String[] ids, String userId, String token);

    String insertOrder(OrderVo orderVo);
}
