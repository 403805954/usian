package com.usian.controller;

import com.usian.feign.ItemFeign;

import com.usian.pojo.TbItemParam;
import com.usian.util.PageResult;
import com.usian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backend/itemParam")
public class ItemParamController {


    @Autowired
    private ItemFeign itemFeign;
//查询规格模板
    @RequestMapping("/selectItemParamAll")
    public Result selectItemParamAll(@RequestParam(name = "page",defaultValue = "1") Integer page,@RequestParam(name = "rows",defaultValue = "20") Integer rows){
        try {
           PageResult list= itemFeign.selectItemParamAll(page,rows);
           return Result.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }

    //添加规格模板
    @RequestMapping("/insertItemParam")
    public Result insertItemParam(@RequestParam("itemCatId") Long itemCatId,@RequestParam("paramData") String paramData){
        try {
            itemFeign.insertItemParam(itemCatId,paramData);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }

    //删除规格模板
    @RequestMapping("/deleteItemParamById")
    public Result deleteItemParamById(@RequestParam("id") Long id){
        try {
            itemFeign.deleteItemParamById(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("失败");
        }
    }
}
