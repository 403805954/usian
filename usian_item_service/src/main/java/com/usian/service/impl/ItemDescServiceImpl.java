package com.usian.service.impl;

import com.usian.mapper.TbItemDescMapper;
import com.usian.pojo.TbItemDesc;
import com.usian.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceImpl implements ItemDescService {

    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Override
    public TbItemDesc selectItemDescByItemId(Long itemId) {
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        if (tbItemDesc!=null){
            return tbItemDesc;
        }
        return null;
    }
}
