package com.usian.controller;

import com.usian.pojo.ItemVo;
import com.usian.pojo.TbItemCat;
import com.usian.pojo.TbItemParam;
import com.usian.service.ItemParamService;
import com.usian.service.ItemService;
import com.usian.util.PageResult;
import com.usian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/itemParam")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;


    @RequestMapping("/selectItemParamAll")
    public PageResult selectItemParamAll(@RequestParam(name = "page") Integer page, @RequestParam(name = "rows") Integer rows){
        return itemParamService.selectItemParamAll(page,rows);
    }

    @RequestMapping("/insertItemParam")
    public void insertItemParam(@RequestParam("itemCatId") Long itemCatId,@RequestParam("paramData") String paramData){
        itemParamService.insertItemParam(itemCatId,paramData);
    }


    @RequestMapping("/deleteItemParamById")
    public void deleteItemParamById(@RequestParam("id") Long id){
        itemParamService.deleteItemParamById(id);
    }
   /* *//*
     * 查询商品列表接口
     * *//*
    @RequestMapping("/selectTbItemAllByPage")
    public PageResult selectTbItemAllByPage(@RequestParam(name = "page") Integer page, @RequestParam(name = "rows") Integer rows){

        return itemService.selectTbItemAllByPage(page,rows);

    }

    *//*
     * 查询商品类目接口
     * *//*
    @RequestMapping("/selectItemCategoryByParentId")
    public List<TbItemCat> selectItemCategoryByParentId(@RequestParam("id") Long id){

        return itemService.selectItemCategoryByParentId(id);

    }

    *//*
     * 查询商品规格参数模板接口
     * *//*
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
    }*/
}
