package com.usian.vo;

import com.usian.pojo.TbItem;
import com.usian.pojo.TbOrder;
import com.usian.pojo.TbOrderShipping;

import java.io.Serializable;

public class OrderVo implements Serializable {
    String  orderItem ;

    TbOrderShipping tbOrderShipping;

    TbOrder tbOrder;

    public OrderVo(String orderItem, TbOrderShipping tbOrderShipping, TbOrder tbOrder) {
        this.orderItem = orderItem;
        this.tbOrderShipping = tbOrderShipping;
        this.tbOrder = tbOrder;
    }

    public OrderVo() {
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public TbOrderShipping getTbOrderShipping() {
        return tbOrderShipping;
    }

    public void setTbOrderShipping(TbOrderShipping tbOrderShipping) {
        this.tbOrderShipping = tbOrderShipping;
    }

    public TbOrder getTbOrder() {
        return tbOrder;
    }

    public void setTbOrder(TbOrder tbOrder) {
        this.tbOrder = tbOrder;
    }
}
