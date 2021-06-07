package com.usian.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.usian.mapper.TbItemMapper;
import com.usian.pojo.TbItem;
import com.usian.service.StockLiSiTenErService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StockLiSiTenErServiceServiceImpl implements StockLiSiTenErService {

    @Autowired
    private TbItemMapper itemMapper;
    @Override
    public void updateNumById(Map<String, Integer> itemIdNumMap) {
        itemIdNumMap.forEach((id,num) ->{
            itemMapper.updateNum4id(Long.parseLong(id),num);
        });

    }
}
