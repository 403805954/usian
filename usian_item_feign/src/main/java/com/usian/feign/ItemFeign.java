package com.usian.feign;

import com.usian.pojo.*;
import com.usian.util.PageResult;
import com.usian.util.Result;
import com.usian.vo.ADItemCatVo;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@FeignClient(value = "usian-item-service")
public interface ItemFeign {
//首页侧面广告
    @RequestMapping("/itemCategory/selectItemCategoryAll")
    public ADItemCatVo selectItemCategoryAll();

    //调用service查询商品列表
    @RequestMapping("/item/selectTbItemAllByPage")
    public PageResult selectTbItemAllByPage(@RequestParam(name = "page") Integer page, @RequestParam(name = "rows") Integer rows);

    //调用service商品类目接口
    @RequestMapping("/item/selectItemCategoryByParentId")
    public List<TbItemCat> selectItemCategoryByParentId(@RequestParam("id") Long id);

    //调用service商品规格参数
    @RequestMapping("/item/selectItemParamByItemCatId/{itemCatId}")
    public TbItemParam selectItemParamByItemCatId(@PathVariable("itemCatId") Long itemCatId);

    //调用service商品添加接口
    @RequestMapping("/item/insertTbItem")
    public void insertTbItem(@RequestBody ItemVo itemVo);

    //调用service商品删除接口
    @RequestMapping("/item/deleteItemById")
    public void deleteItemById(@RequestParam("itemId") Long itemId);

    //调用service预更新接口
    @RequestMapping("/item/preUpdateItem")
    public Map<String,Object> preUpdateItem(@RequestParam("itemId") Long itemId);

    @RequestMapping("/item/updateTbItem")
    public void updateTbItem(@RequestBody ItemVo itemVo);

    @RequestMapping("/itemParam/selectItemParamAll")
    public PageResult selectItemParamAll(@RequestParam(name = "page") Integer page, @RequestParam(name = "rows") Integer rows);

    @RequestMapping("/itemParam/insertItemParam")
    public void insertItemParam(@RequestParam("itemCatId") Long itemCatId,@RequestParam("paramData") String paramData);

    @RequestMapping("/itemParam/deleteItemParamById")
    public void deleteItemParamById(@RequestParam("id") Long id);


    //查询商品详情接口
    @RequestMapping("/item/selectItemInfo")
    public TbItem selectItemInfo(@RequestParam(name = "itemId") Long itemId);
    //查询商品规格参数接口
    @RequestMapping("/tbItemParamItem/selectTbItemParamItemByItemId")
    public TbItemParamItem selectTbItemParamItemByItemId(@RequestParam("itemId") Long itemId);
    //查询商品介绍接口
    @RequestMapping("/itemDesc/selectItemDescByItemId")
    public TbItemDesc selectItemDescByItemId(@RequestParam("itemId") Long itemId);
}
