package com.usian.lisitener;


import com.rabbitmq.client.Channel;
import com.usian.service.StockLiSiTenErService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StockLiSiTenEr {

    @Autowired
    private StockLiSiTenErService stockLiSiTenErService;


    /**
     * 监听者接收消息三要素：
     *  1、queue
     *  2、exchange
     *  3、routing key
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value="stock_queue",durable = "true"),
            exchange = @Exchange(value="deduction_stock_exchange",type= ExchangeTypes.TOPIC),
            key= {"deduction_stock"}
    ))
    public void listen(Map<String, Integer> itemIdNumMap, Channel channel, Message message){
        String correlationId = message.getMessageProperties().getHeaders().get("spring_returned_message_correlation")+"";
        stockLiSiTenErService.updateNumById(itemIdNumMap);


    }
}
