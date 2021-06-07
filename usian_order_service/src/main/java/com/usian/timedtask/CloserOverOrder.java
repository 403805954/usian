package com.usian.timedtask;


import com.usian.mapper.TbItemMapper;
import com.usian.mapper.TbOrderItemMapper;
import com.usian.mapper.TbOrderMapper;
import com.usian.pojo.TbOrder;
import com.usian.pojo.TbOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CloserOverOrder {
    @Autowired
    private TbOrderMapper orderMapper;

    /**
     * 定时任务方法
     *
     * @Scheduled:设置定时任务 cron属性：cron表达式。定时任务触发是时间的一个字符串表达形式
     */
    @Scheduled(cron = "0 0/1 * * * * ?")
    public void task() {

        //关闭超时订单
        //查询 状态为未支付的商品 条件 30分钟未支付则关闭 create_time + 30 分钟 > new Date 则 已超时 需要关闭
        List<TbOrder> orderItems = orderMapper.findOverTimeOrder();
        //修改状态为6(关闭)
        for (TbOrder orderItem : orderItems) {
            TbOrder order = new TbOrder();
            order.setStatus(6);
            order.setOrderId(orderItem.getOrderId());
            orderMapper.updateByPrimaryKeySelective(order);
        }
    }
}
