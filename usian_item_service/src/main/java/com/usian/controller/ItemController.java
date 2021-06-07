package com.usian.controller;

import com.usian.pojo.*;
import com.usian.service.ItemService;
import com.usian.util.IDUtils;
import com.usian.util.PageResult;
import com.usian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;


    /*
     * 查询商品列表接口
     * */
    @RequestMapping("/selectTbItemAllByPage")
    public PageResult selectTbItemAllByPage(@RequestParam(name = "page") Integer page, @RequestParam(name = "rows") Integer rows){

        return itemService.selectTbItemAllByPage(page,rows);
    }

    /*
     * 查询商品类目接口
     * */
    @RequestMapping("/selectItemCategoryByParentId")
    public List<TbItemCat> selectItemCategoryByParentId(@RequestParam("id") Long id){

        return itemService.selectItemCategoryByParentId(id);

    }

    /*
     * 查询商品规格参数模板接口
     * */
    @RequestMapping("/selectItemParamByItemCatId/{itemCatId}")
    public TbItemParam selectItemParamByItemCatId(@PathVariable("itemCatId") Long itemCatId){
        return itemService.selectItemParamByItemCatId(itemCatId);
    }


    @RequestMapping("/insertTbItem")
    public void insertTbItem(@RequestBody ItemVo itemVo){
        itemService.insertTbItem(itemVo);
    }

    @RequestMapping("/deleteItemById")
    public void deleteItemById(@RequestParam("itemId") Long itemId){
        itemService.deleteItemById(itemId);
    }

    @RequestMapping("/preUpdateItem")
    public Map<String,Object> preUpdateItem(@RequestParam("itemId") Long itemId){
        return itemService.preUpdateItem(itemId);
    }

    @RequestMapping("/updateTbItem")
    public void updateTbItem(@RequestBody ItemVo itemVo){
         itemService.updateTbItem(itemVo);
    }

    @RequestMapping("/selectItemInfo")
    public TbItem selectItemInfo(@RequestParam(name = "itemId") Long itemId){
        return itemService.selectItemInfo(itemId);
    }
}
