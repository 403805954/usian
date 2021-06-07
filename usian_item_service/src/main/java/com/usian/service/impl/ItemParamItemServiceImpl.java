package com.usian.service.impl;

import com.usian.mapper.TbItemParamItemMapper;
import com.usian.pojo.TbItemParamItem;
import com.usian.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {


    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    /**
     * 根据商品ID查询 iteamparamiteam表
     *
     * @param itemId
     * @return
     */
    @Override
    public TbItemParamItem selectTbItemParamItemByItemId(Long itemId) {
        Example example = Example.builder(TbItemParamItem.class)
                .where(Sqls.custom().andEqualTo("itemId", itemId))
                .build();
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExample(example);
        if (tbItemParamItems != null || tbItemParamItems.size() > 0) {
            return tbItemParamItems.get(0);
        }
        return null;
    }
}
