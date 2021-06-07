package com.usian.mapper;

import com.usian.pojo.TbOrder;
import com.usian.pojo.TbOrderItem;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbOrderMapper extends Mapper<TbOrder> {

    @Select("select * from tb_order where status = 1  and TIMESTAMPDIFF(MINUTE,create_time,NOW())>=30")
    List<TbOrder> findOverTimeOrder();
}