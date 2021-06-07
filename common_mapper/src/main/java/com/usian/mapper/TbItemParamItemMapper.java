package com.usian.mapper;

import com.usian.pojo.TbItemParamItem;
import org.apache.ibatis.annotations.Delete;
import tk.mybatis.mapper.common.Mapper;

public interface TbItemParamItemMapper extends Mapper<TbItemParamItem> {
    @Delete("delete from tb_item_param_item where item_id =#{itemId}")
    void deleteByItemId(Long itemId);
}