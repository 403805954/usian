package com.usian.service;

import com.usian.pojo.*;
import com.usian.util.PageResult;
import com.usian.util.Result;

import java.util.List;
import java.util.Map;


public interface ItemService {

    List<TbItemCat> selectItemCategoryByParentId(Long id);

    PageResult selectTbItemAllByPage(Integer page, Integer rows);

    TbItemParam selectItemParamByItemCatId(Long itemCatId);

    void insertTbItem(ItemVo itemVo);

    void deleteItemById(Long itemId);

    Map<String,Object> preUpdateItem(Long itemId);

    void updateTbItem(ItemVo itemVo);

    TbItem selectItemInfo(Long itemId);
}
