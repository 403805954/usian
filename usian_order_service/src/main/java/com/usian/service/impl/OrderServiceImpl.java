package com.usian.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.usian.constant.Constant;
import com.usian.mapper.*;
import com.usian.mq.OrderSendMessage;
import com.usian.pojo.*;
import com.usian.service.OrderService;
import com.usian.util.JsonUtils;
import com.usian.util.RedisClient;
import com.usian.vo.OrderVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.code.ORDER;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private SendMessageLogMapper sendMessageLogMapper;

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private OrderSendMessage orderSendMessage;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 获取Redis中当前用户选中的商品 返回给前台
     *
     * @param ids
     * @param userId
     * @param token
     * @return
     */
    @Override
    public List<CartItem> goSettlement(String[] ids, String userId, String token) {
        List<CartItem> cartItems = null;
        for (String id : ids) {
            CartItem hget = (CartItem) redisClient.hget(Constant.COOKIENAME + userId, id);
            cartItems.add(hget);
        }
        return cartItems;
    }


    /**
     * 添加商品 物流 订单信息等
     *
     * @param orderVo
     * @return
     */
    @Override
    public String insertOrder(OrderVo orderVo) {
        List<TbOrderItem> tbOrderItems1 = JsonUtils.jsonToList2(orderVo.getOrderItem(), TbOrderItem.class);
        for (TbOrderItem tbOrderItems : tbOrderItems1) {
            //判断当前商品库存是否足够
            Integer itemId = Integer.parseInt(tbOrderItems.getItemId());
            Integer itemNum = itemMapper.findNumByitemid(itemId);
            if (itemNum >= tbOrderItems.getNum()) {
                //库存足够 可以购买
                TbOrder Order = orderVo.getTbOrder();
                //添加订单表
                String orderID = UUID.randomUUID().toString();
                Date now = new Date();
                Order.setOrderId(orderID);
                Order.setStatus(1);
                Order.setCreateTime(now);
                tbOrderMapper.insertSelective(Order);

                //添加OrderItem表
                List<TbOrderItem> tbOrderItems2 = JsonUtils.jsonToList2(orderVo.getOrderItem(), TbOrderItem.class);
                tbOrderItems2.forEach(tbOrderItem -> {
                    tbOrderItem.setId(UUID.randomUUID().toString());
                    tbOrderItem.setOrderId(orderID);
                    orderItemMapper.insertSelective(tbOrderItem);
                });
                //添加快递信息表
                TbOrderShipping tbOrderShipping = orderVo.getTbOrderShipping();
                tbOrderShipping.setOrderId(orderID);
                tbOrderShipping.setCreated(now);
                orderShippingMapper.insertSelective(tbOrderShipping);

                //添加成功后减库存
//                itemMapper.updatenum4id(tbOrderItems.getNum(), itemId);

                //使用mq进行异步修改库存
                Map<String, Integer> itemNumMap = tbOrderItems2.stream().collect(Collectors.toMap(TbOrderItem::getItemId,TbOrderItem::getNum));

                //发送消息记录表 0为待发送 1 为已发送
                SendMessageLog sendMessageLog = new SendMessageLog();
                sendMessageLog.setBody(JsonUtils.objectToJson(itemNumMap));
                int sendMessagLogId = sendMessageLogMapper.insertSelective(sendMessageLog);//影响的行数


//                amqpTemplate.convertAndSend("deduction_stock_exchange","deduction_stock",itemNumMap);
                //保存订单信息


                orderSendMessage.sendMeassge(sendMessageLog.getId()+"",itemNumMap);
                return orderID;
            }
        }

        return null;
        //如果订单确定 删除 Redis中购物车中的数据
//        for (TbOrderItem tbOrderItem : tbOrderItems) {
//            List<CartItem> cartItems = (List<CartItem>) redisClient.hget(Constant.COOKIENAME + tbOrder.getUserId(), tbOrderItem.getItemId());
//            if (cartItems!=null){
//                for (CartItem cartItem : cartItems) {
//                    redisClient.hdel(Constant.COOKIENAME+tbOrder.getUserId(),cartItem.getId());
//                }
//            }
//        }
    }
}
