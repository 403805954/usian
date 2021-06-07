package com.usian.mapper;

import com.usian.pojo.ItemVo;
import com.usian.pojo.TbItem;
import com.usian.pojo.TbOrderItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbItemMapper extends Mapper<TbItem> {

    @Select("select a.*,b.item_desc `desc`,c.param_data `itemParams` from tb_item a,tb_item_desc b,tb_item_param_item where a.id=b.item_id and a.id=c.item_id and a.id=#{itemId}")
    ItemVo preUpdateItem(Long itemId);

    @Update("update tb_item set status = 3 where id=#{itemId}")
    void updateStatus(Long itemId);

    @Select("select num from tb_item where id=#{num}")
    Integer findNumByitemid(Integer num);

    @Update("update tb_item set num = num - #{num} where id = #{itemId}")
    void updateNum4id(@Param("itemId") Long itemId, @Param("num") Integer num);



}
