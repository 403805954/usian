package com.usian.controller;


import com.usian.feign.ItemFeign;
import com.usian.pojo.*;
import com.usian.util.PageResult;
import com.usian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backend")
public class ItemController {
    @Autowired
    private ItemFeign itemFeign;

//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private LoadBalancerClient loadBalancerClient;
    /*
    * 查询商品列表接口
    * */
    @RequestMapping("/item/selectTbItemAllByPage")
    public Result selectTbItemAllByPage(@RequestParam(name = "page",defaultValue = "1") Integer page,@RequestParam(name = "rows",defaultValue = "2") Integer rows){
        try {
           PageResult pageResult= itemFeign.selectTbItemAllByPage(page,rows);
           return  Result.ok(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }


    }


    /*
    * 查询商品类目接口
    * */
    @RequestMapping("/itemCategory/selectItemCategoryByParentId")
    public Result selectItemCategoryByParentId(@RequestParam(name = "id",defaultValue = "0") Long id){

        try {
           List<TbItemCat>   byId = itemFeign.selectItemCategoryByParentId(id);
            return Result.ok(byId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }
    /*
     * 查询商品规格参数模板接口
     * */
        @RequestMapping("/itemParam/selectItemParamByItemCatId/{itemCatId}")
        public Result selectItemParamByItemCatId(@PathVariable("itemCatId") Long itemCatId) {

            try {
                TbItemParam byId = itemFeign.selectItemParamByItemCatId(itemCatId);
                return Result.ok(byId);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("失败");
            }
        }
    /*
     * 商品添加接口
     * */
    @RequestMapping("/item/insertTbItem")
    public Result insertTbItem(ItemVo itemVo){
        try {
            itemFeign.insertTbItem(itemVo);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("失败");
    }


    /*
     * 商品删除接口
     * */
    @RequestMapping("/item/deleteItemById")
    public Result deleteItemById(@RequestParam("itemId") Long itemId){
        try {
            itemFeign.deleteItemById(itemId);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("失败");
    }

    /*
     * 预更新商品接口
     * */
    @RequestMapping("/item/preUpdateItem")
    public Result preUpdateItem(@RequestParam("itemId") Long itemId){
        try {
            Map<String, Object> map = itemFeign.preUpdateItem(itemId);
            return Result.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("失败");
    }

    /*
     * 商品修改接口
     * */
    @RequestMapping("/item/updateTbItem")
    public Result updateTbItem(ItemVo itemVo){
        try {
            itemFeign.updateTbItem(itemVo);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("失败");
    }
}
