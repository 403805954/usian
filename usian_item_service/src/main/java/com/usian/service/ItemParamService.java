package com.usian.service;

import com.usian.pojo.TbItemParam;
import com.usian.util.PageResult;

import java.util.List;

public interface ItemParamService {
   PageResult selectItemParamAll(Integer page, Integer rows);

    void insertItemParam(Long itemCatId, String paramData);

    void deleteItemParamById(Long id);
}
